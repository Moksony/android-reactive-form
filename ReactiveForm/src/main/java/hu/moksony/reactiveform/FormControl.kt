package hu.moksony.reactiveform

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import java.lang.Exception

class FormControl(val observable: Observable, bindingResource: Class<*>) {

    var firstErrorOnly: Boolean = true
    var triggerErrorOnNotTouchedField = false

    val fields = mutableMapOf<Int, FieldControl>()

    var _isValid: Boolean = true
        set(value) {
            field = value
            isValid.set(value)
        }
    var _isDirty: Boolean = false
        set(value) {
            field = value
            isDirty.set(value)
        }
    var isValid = ObservableField(true)

    val isDirty = ObservableField(false)

    var brFields = mutableMapOf<Int, String>()

    val errors = ObservableArrayMap<String, String>()

    var subControls = mutableListOf<FormControl>()

    var parent: FormControl? = null

    init {
        bindingResource.declaredFields.forEach { field ->
            field.isAccessible = true
            val genBr: Int = field.getInt(null)
            brFields[genBr] = field.name
        }

        observable
            .addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    val propName = brFields[propertyId]
                    Log.d("FROM", "[$propertyId] $propName changed")
                    validate(propertyId)
                }
            })
    }

    private fun getProperty(fieldId: Int): String {
        return brFields[fieldId] ?: throw Exception("Field id ($fieldId) not in BR::class")
    }

    //Get the actual value of the property using the field getter: get+FieldName
    private fun getPropertyValue(property: String): Any? {
        return observable::class.java.getMethod("get" + property.capitalize()).invoke(observable)
    }


    fun checkFromValid(): Boolean {
        val valid = this.fields.values.all { it.isValid }
        val subValid = this.subControls.all { it._isValid }
        return valid && subValid
    }

    fun checkDirty(): Boolean {
        val dirtyItem = this.fields.values.find { it.isDirty }
        if (subControls.size > 0) {
            val subDirtyItem = this.subControls.find { it._isDirty }
            return subDirtyItem != null || dirtyItem != null
        } else {
            return dirtyItem != null
        }
    }

    fun validateAll() {
        fields.forEach {
            validate(it.key, false, false)
        }
        checkForm()
    }

    fun build(): FormControl {
        checkForm()
        return this
    }

    fun checkForm() {
        val valid = checkFromValid()
        val dirty = checkDirty()

        _isValid = valid
        _isDirty = dirty

        parent?.let { p ->
            if ((p._isValid != _isValid) || (p._isDirty != _isDirty)) {
                //notify parent to check
                p.onSubFormControlChanged(this)
            }
        }
    }

    //update field isValid & collect errors
    fun validate(fieldId: Int, allowTrigger: Boolean = true, validateForm: Boolean = true) {
        fields[fieldId]?.let { field ->
            val prop = field.propName
            val propValue = getPropertyValue(prop)
            field.value = propValue
            if (!triggerErrorOnNotTouchedField && !field.isTouched) {
                return
            }

            val errs = field.getErrors()
            field.isValid = errs == null

            if (firstErrorOnly) {
                this.errors[prop] = errs?.getOrNull(0)
            } else {
                this.errors[prop] = errs?.joinToString("\n")
            }


            val triggers = field.triggerFields
            if (allowTrigger && triggers?.isNotEmpty() == true) {
                triggers.forEach {
                    validate(
                        it,
                        allowTrigger = false,
                        validateForm = false
                    )
                }
            }

            if (validateForm) {
                checkForm()
            }
        }
    }

    fun addField(propId: Int, vararg args: FieldValidator): FieldControl {
        if (fields.containsKey(propId)) {
            throw Exception("$propId already added")
        }

        val propName = getProperty(propId)
        val value = getPropertyValue(propName)
        val field = FieldControl(propId, propName, value, args)

        fields[propId] = field
        errors[propName] = null

        field.isValid = field.getErrors() == null
        return field
    }

    fun addControl(control: FormControl) {
        control.parent = this
        subControls.add(control)
        checkForm()
    }

    fun removeControl(control: FormControl) {
        val index = this.subControls.indexOf(control)
        if (index == -1) {
            throw Exception("FormControl not found.")
        }
        this.subControls.removeAt(index)
        control.parent = null
        this.checkForm()
    }

    fun snapshotValues() {
        this.subControls.forEach { formControl ->
            formControl.snapshotValues()
        }

        this.fields.forEach {
            it.value.initValue = getPropertyValue(it.value.propName)
        }
        checkForm()
    }

    private fun onSubFormControlChanged(formControl: FormControl) {
        this.checkForm()
    }
}