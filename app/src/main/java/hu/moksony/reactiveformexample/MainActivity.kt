package hu.moksony.reactiveformexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import hu.moksony.reactiveform.FormControl
import hu.moksony.reactiveform.validator.*
import hu.moksony.reactiveformexample.databinding.ActivityMainBinding
import hu.moksony.reactiveformexample.databinding.FormChildBinding

class MainActivity : AppCompatActivity() {

    val form = RegisterForm()
    lateinit var formControl: FormControl
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)


        formControl = FormControl(form, BR::class.java).apply {
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
            addField(
                BR.child,
//                Required("At least one child required"),
//                MinLength(1, "At least one child required")
            )
        }
            .build()

        binding.form = form
        binding.formControl = formControl

        this.binding.btnUpdate.setOnClickListener {
            this.formControl.snapshotValues()
        }

        binding.btnAdd.setOnClickListener {
            this.addChild()
        }
    }

    fun addChild() {
        val childForm = ChildForm()
        val childControl = FormControl(childForm, BR::class.java).apply {
            addField(
                BR.firstName,
                Required(getString(R.string.required)),
                MinLength(3, getString(R.string.min_length, 3))
            )
            addField(
                BR.lastName,
                Required(getString(R.string.required)),
                MinLength(3, getString(R.string.min_length, 3))
            )
            addField(
                BR.age,
                Required(getString(R.string.required)),
                Min(0, getString(R.string.age_is_invalid))
            )
        }
            .build()
        this.form.child.add(childForm)

        val viewContainer = binding.childrenHolder
        val binding = FormChildBinding.inflate(layoutInflater, viewContainer, false)
        viewContainer.addView(binding.root)

        binding.btnRemove.setOnClickListener {
            formControl.removeControl(childControl)
            form.child.remove(childForm)
            viewContainer.removeView(binding.root)
        }

        binding.child = childForm
        binding.control = childControl

        formControl.addControl(childControl)
    }
}