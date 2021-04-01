package hu.moksony.reactiveformexample.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import hu.moksony.reactiveformexample.BR

class LoginModel : BaseObservable() {
    @Bindable
    var username: String? = null
    set(value) {
        if(field != value){
            field = value
            notifyPropertyChanged(BR.username)
        }
    }

    @Bindable
    var password: String? = null
    set(value) {
        if(field != value){
            field = value
            notifyPropertyChanged(BR.password)
        }
    }
}