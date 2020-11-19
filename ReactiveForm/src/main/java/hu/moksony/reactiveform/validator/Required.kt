package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.Field
import hu.moksony.reactiveform.Validator

class Required(message: String) : Validator(message) {

    override fun validate(value: Any?, field: Field): Boolean {
        return value != null
    }
}