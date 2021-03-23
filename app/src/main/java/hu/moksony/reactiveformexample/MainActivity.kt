package hu.moksony.reactiveformexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
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
            add(
                BR.username,
                Required(getString(R.string.required)),
                MinLength(3, getString(R.string.username_min_length, 3))
            )
            add(
                BR.email,
                Email(getString(R.string.not_valid_email))
            )
            add(
                BR.password,
                Required(getString(R.string.required)),
                MinLength(6, getString(R.string.min_length, 6))
            ).apply {
                triggerFields = arrayOf(BR.confirmPassword)
            }

            add(
                BR.confirmPassword,
                Required(getString(R.string.required)),
                MatchWith(this.fields[BR.password]!!, getString(R.string.password_not_match))
            )
            add(
                BR.policyAccepted,
                MatchExactly(true, getString(R.string.not_match))

            )
            add(BR.hasChild)
        }
            .build()

        val field = formControl.createField(
            BR.child,
            Required("At least one child required"),
            MinLength(1, "At least one child required")
        )

        val onPropChangeListener = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.hasChild -> {
                        formControl.apply {
                            if (form.hasChild == true) {
                                   addField(field)
                            } else {
                                   removeField(field)
                            }
                            formControl.checkForm()
                        }
                    }
                }
            }
        }

        form.addOnPropertyChangedCallback(onPropChangeListener)

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
            add(
                BR.firstName,
                Required(getString(R.string.required)),
                MinLength(3, getString(R.string.min_length, 3))
            )
            add(
                BR.lastName,
                Required(getString(R.string.required)),
                MinLength(3, getString(R.string.min_length, 3))
            )
            add(
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