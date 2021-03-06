package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator
import java.lang.Exception

class RegexMatch(message: String, withValue: String) : FieldValidator(message) {
    val regex = Regex(withValue)
    override fun validate(value: Any?, field: FieldControl): Boolean {
        return when (value) {
            is String -> value.matches(regex)
            null -> validIfNotRequired(field)
            else -> throw Exception("${this::class.java} can not validate ${value::class.java}")
        }
    }
}