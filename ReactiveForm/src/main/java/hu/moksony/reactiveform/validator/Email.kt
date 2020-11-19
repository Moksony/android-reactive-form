package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.Field
import hu.moksony.reactiveform.Validator

class Email(message: String) : Validator(message) {
    companion object {
        val EmailRegex: Regex by lazy {
            Regex("^[\\w-\\\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        }
    }

    override fun validate(value: Any?, field: Field): Boolean {
        return when (value) {
            is String -> {
                if (value.isEmpty()) {
                    this.validIfNotRequired(field)
                } else {
                    value.matches(EmailRegex)
                }
            }
            else -> this.validIfNotRequired(field)
        }
    }
}