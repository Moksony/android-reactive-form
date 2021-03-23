package hu.moksony.reactiveform

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import javax.xml.validation.Validator

class FieldControl(
    val propId: Int,
    val propName: String,
    var initValue: Any?,
    val validators: MutableList<FieldValidator>
) : BaseObservable() {
    var value: Any? = initValue
        set(value) {
            if (field != value) {
                field = value
                isTouched = true
            }
        }
    var triggerFields: Array<Int>? = null
    var comperator = FieldComparator.default

    var isValid: Boolean = false

    var isTouched: Boolean = false

    @Bindable
    var enabled: Boolean? = null

    val isDirty: Boolean
        get() {
            return !comperator.isSame(initValue, value)
        }

    fun removeValidator(validators: Iterable<FieldValidator>) {
        validators.forEach {
            removeValidator(it, false)
        }
        checkValid()
    }

    fun checkValid() {
        this.isValid = this.getErrors() == null
    }

    fun removeValidator(validator: FieldValidator, checkValid: Boolean = true) {
        if (this.validators.contains(validator)) {
            this.validators.remove(validator)
        }
        if (checkValid) {
            checkValid()
        }
    }

    fun addValidator(validators: Iterable<FieldValidator>, checkValid: Boolean = true) {
        validators.forEach {
            addValidator(it)
        }
        if (checkValid) {
            checkValid()
        }
    }

    fun addValidator(validator: FieldValidator) {
        if (!this.validators.contains(validator)) {
            this.validators.add(validator)
        }
        checkValid()
    }


    fun getErrors(): List<String>? {
        val errs: MutableList<String> by lazy {
            mutableListOf()
        }
        var hasError: Boolean = false
        validators.forEachIndexed { i, validator ->
            val isValid = validator.validate(value, this)
            if (!isValid) {
                hasError = true
                errs.add(validator.message)
            }
        }
        if (hasError) {
            return errs
        }
        return null
    }
}