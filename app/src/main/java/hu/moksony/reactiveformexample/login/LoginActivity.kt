package hu.moksony.reactiveformexample.login

import android.app.Activity
import android.database.DatabaseUtils
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import hu.moksony.reactiveform.FormControl
import hu.moksony.reactiveform.validator.MinLength
import hu.moksony.reactiveform.validator.Required
import hu.moksony.reactiveformexample.BR
import hu.moksony.reactiveformexample.R
import hu.moksony.reactiveformexample.databinding.ActivityLoginBinding

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        val loginModel = LoginModel()
        val formControl = FormControl(loginModel, BR::class.java).apply {
            add(
                BR.username,
                Required("Username required"),
                MinLength(3, "Username min length is 3 character")
            )

            add(
                BR.password,
                Required("Password required"),
                MinLength(6, "Password min length is 6 character")
            )
        }.build()

        binding.model = loginModel
        binding.formControl = formControl
    }
}