package hu.moksony.reactiveformexample

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class RegisterForm : BaseObservable() {
    @Bindable
    var username: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.username)
            }
        }

    @Bindable
    var email: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.email)
            }
        }

    @Bindable
    var password: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.password)
            }
        }

    @Bindable
    var confirmPassword: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.confirmPassword)
            }
        }

    @Bindable
    var policyAccepted: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.policyAccepted)
            }
        }

    @Bindable
    var child = mutableListOf<ChildForm>()
}