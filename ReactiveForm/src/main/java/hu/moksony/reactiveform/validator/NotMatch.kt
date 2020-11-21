package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator

class NotMatch(message: String, val withValue: Any) : FieldValidator(message) {
    override fun validate(value: Any?, field: FieldControl): Boolean {
        return value != withValue
    }
}