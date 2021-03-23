package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator
import java.lang.Exception
import javax.xml.validation.Validator

class Min(val min: Int, message: String) : FieldValidator(message) {
    override fun validate(value: Any?, field: FieldControl): Boolean {
        return when (value) {
            is Int -> value >= min
            is Long -> value >= min
            is Float -> value >= min
            is Double -> value >= min
            "" -> validIfNotRequired(field)
            is String -> value.toInt() >= min
            null -> validIfNotRequired(field)
            else -> throw Exception("${field::class.java} not supported.")
        }
    }
}