package hu.moksony.reactiveform

import hu.moksony.reactiveform.validator.Required

abstract class Validator(val message: String) {
    abstract fun validate(value: Any?, field: Field): Boolean

    fun validIfNotRequired(field: Field): Boolean {
        return field.validators.find { it is Required } == null
    }
}