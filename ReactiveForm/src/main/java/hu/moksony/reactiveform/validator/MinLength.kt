package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.Field
import hu.moksony.reactiveform.Validator

class MinLength(val min: Int, message: String) : Validator(message) {

    override fun validate(value: Any?, field: Field): Boolean {
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