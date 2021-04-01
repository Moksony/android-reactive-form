Android Reactive forms
===============

This library based on android DataBinding.

# Examples
## Simple login
### Create a login model
```kotlin
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
```
### Create Reactive form
```kotlin
 val loginModel = LoginModel()
 val formControl = FormControl(null, BR::class.java).apply{
	add(
	    BR.username,
	    Required(Required("Required field"),
	    MinLength(3, "Username min length is 3 character")
	)
	add(
	    BR.password,
	    Required("Required field"),
	    MinLength(6, "Passworn min length is 6 character")
	)
}
```
### Bind model & formcontrol
In your activity/fragment
```kotlin
binding.model = loginModel
binding.formControl = formControl
```
XML
```xml
<com.google.android.material.textfield.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:error="@{formControl.errors.username}">

            <com.google.android.material.textfield.TextInputEditText
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@={model.username}" />
</com.google.android.material.textfield.TextInputLayout>

<com.google.android.material.button.MaterialButton
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:enabled="@{formControl.isValid}"
            android:text="Login" />
```
### DONE




