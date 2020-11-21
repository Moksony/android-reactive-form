package hu.moksony.reactiveform

import hu.moksony.reactiveform.validator.Required

abstract class FieldValidator(val message: String) {
    abstract fun validate(value: Any?, field: FieldControl): Boolean

    fun validIfNotRequired(field: FieldControl): Boolean {
        return field.validators.find { it is Required } == null
    }
}