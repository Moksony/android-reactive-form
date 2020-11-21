package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator

class MinLength(val min: Int, message: String) : FieldValidator(message) {

    override fun validate(value: Any?, field: FieldControl): Boolean {
        return when (value) {
            is String -> {
                if (value.isEmpty()) {
                    this.validIfNotRequired(field)
                } else {
                    value.length >= min
                }
            }
            is List<*> -> value.size >= min
            is Array<*> -> value.size >= min
            null -> this.validIfNotRequired(field)
            else -> TODO()
        }
    }
}