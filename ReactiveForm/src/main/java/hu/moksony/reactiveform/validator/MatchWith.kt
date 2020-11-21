package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator

class MatchWith(private val anotherField: FieldControl, message: String) : FieldValidator(message) {
    override fun validate(value: Any?, field: FieldControl): Boolean {
        return value == anotherField.value
    }
}