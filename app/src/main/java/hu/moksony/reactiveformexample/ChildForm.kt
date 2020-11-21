package hu.moksony.reactiveformexample

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class ChildForm: BaseObservable() {
    @Bindable
    var firstName: String? = null
    set(value) {
        if(field != value){
            field = value
            notifyPropertyChanged(BR.firstName)
        }
    }
    
    @Bindable
    var lastName: String? = null
    set(value) {
        if(field != value){
            field = value
            notifyPropertyChanged(BR.lastName)
        }
    }
    
    @Bindable
    var age: String? = null
    set(value) {
        if(field != value){
            field = value
            notifyPropertyChanged(BR.age)
        }
    }
}