package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.Field
import hu.moksony.reactiveform.Validator
import java.lang.Exception

class RegexMatch(message: String, withValue: String) : Validator(message) {
    val regex = Regex(withValue)
    override fun validate(value: Any?, field: Field): Boolean {
        return when (value) {
            is String -> value.matches(regex)
            null -> validIfNotRequired(field)
            else -> throw Exception("${this::class.java} can not validate ${value::class.java}")
        }
    }
}