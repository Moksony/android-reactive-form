package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.Field
import hu.moksony.reactiveform.Validator

class NotMatch(message: String, val withValue: Any) : Validator(message) {
    override fun validate(value: Any?, field: Field): Boolean {
        return value != withValue
    }
}