package hu.moksony.reactiveform

class DefaultFieldComparator : FieldComparator() {
    override fun isSame(oldItem: Any?, newItem: Any?): Boolean {
        if (oldItem is String || newItem is String) {
            return (oldItem ?: "") == (newItem ?: "")
        }
        return oldItem == newItem
    }
}