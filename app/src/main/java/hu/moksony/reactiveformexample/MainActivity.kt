package hu.moksony.reactiveformexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import hu.moksony.reactiveform.Form
import hu.moksony.reactiveform.validator.*
import hu.moksony.reactiveformexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val form = RegisterForm()

        val validator = Form(form, BR::class.java).apply {
            addField(
                BR.username,
                Required(getString(R.string.required)),
                MinLength(3, getString(R.string.username_min_length, 3))
            )
            addField(
                BR.email,
                Email(getString(R.string.not_valid_email))
            )
            addField(
                BR.password,
                Required(getString(R.string.required)),
                MinLength(6, getString(R.string.min_length, 6))
            ).apply {
                triggerFields = arrayOf(BR.confirmPassword)
            }

            addField(
                BR.confirmPassword,
                Required(getString(R.string.required)),
                MatchWith(this.fields[BR.password]!!, getString(R.string.password_not_match))
            )
            addField(
                BR.policyAccepted,
                MatchExactly(true, getString(R.string.not_match))

            )
            checkForm()
        }

        binding.form = form
        binding.validator = validator

    }
}