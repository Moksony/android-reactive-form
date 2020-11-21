package hu.moksony.reactiveform.validator

import hu.moksony.reactiveform.FieldControl
import hu.moksony.reactiveform.FieldValidator

class Email(message: String) : FieldValidator(message) {
    companion object {
        val EmailRegex: Regex by lazy {
            Regex("^[\\w-\\\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        }
    }

    override fun validate(value: Any?, field: FieldControl): Boolean {
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