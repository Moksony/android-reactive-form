package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator
import java.lang.Exception


class Max(val max: Int, message: String) : FieldValidator(message) {
    override fun validate(value: Any?, field: FieldControl): Boolean {
        return when (value) {
            is Int -> value <= max
            "" -> validIfNotRequired(field)
            is String -> value.toInt() <= max
            null -> validIfNotRequired(field)
            else -> throw Exception("${field::class.java} not supported.")
        }
    }
}