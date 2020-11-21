package hu.moksony.reactiveform

class FieldControl(
    val propId: Int,
    val propName: String,
    var initValue: Any?,
    val validators: Array<out FieldValidator>
) {
    var value: Any? = initValue
        set(value) {
            if (field != value) {
                field = value
                isTouched = true
            }
        }
    var triggerFields: Array<Int>? = null
    var comperator = FieldComparator.default

    var isValid: Boolean = false

    var isTouched: Boolean = false

    val isDirty: Boolean
        get() {
            return !comperator.isSame(initValue, value)
        }

    fun getErrors(): List<String>? {
        val errs: MutableList<String> by lazy {
            mutableListOf()
        }
        var hasError: Boolean = false
        validators.forEachIndexed { i, validator ->
            val isValid = validator.validate(value, this)
            if (!isValid) {
                hasError = true
                errs.add(validator.message)
            }
        }
        if(hasError){
            return errs
        }
        return null
    }
}