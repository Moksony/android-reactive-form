package hu.moksony.reactiveform

abstract class FieldComparator {
    companion object {
        var default = DefaultFieldComparator()
    }

    abstract fun isSame(oldItem: Any?, newItem: Any?): Boolean
}