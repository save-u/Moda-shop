package com.lycn.modashop.ui.auth.register

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.lycn.modashop.R
import com.lycn.modashop.databinding.ActivityRegisterBinding
import com.lycn.modashop.ui.base.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputLayoutName = binding.inputLayoutName
        val editName = binding.editName
        val inputLayoutEmail = binding.inputLayoutEmail
        val editEmail = binding.editEmail
        val inputLayoutPassword = binding.inputLayoutPassword
        val editPassword = binding.editPassword
        val inputLayoutConfirmPassword = binding.inputLayoutConfirmPassword
        val editConfirmPassword = binding.editConfirmPassword
        val btnSignUp = binding.btnSignUp
        val loading = binding.pbLoading
        val btnSignIn = binding.btnSignIn

        registerViewModel =
            ViewModelProvider(this, ViewModelFactory()).get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this) {
            val registerState = it ?: return@observe

            // disable login button unless both username / password is valid
            btnSignIn.isEnabled = registerState.isDataValid

            if (registerState.nameError == null) {
                inputLayoutName.error = null
            } else {
                inputLayoutName.error = getString(registerState.nameError)
            }
            if (registerState.emailError == null) {
                inputLayoutEmail.error = null
            } else {
                inputLayoutEmail.error = getString(registerState.emailError)
            }
            if (registerState.passwordError == null) {
                inputLayoutPassword.error = null
            } else {
                inputLayoutPassword.error = getString(registerState.passwordError)
            }
            if (registerState.confirmPasswordError == null) {
                inputLayoutConfirmPassword.error = null
            } else {
                inputLayoutConfirmPassword.error = getString(registerState.confirmPasswordError)
            }
        }

        registerViewModel.registerResult.observe(this) {
            val registerResult = it ?: return@observe

            loading.visibility = View.GONE

            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                updateUiWithUser(registerResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        }

        btnSignIn.setOnClickListener {
            registerViewModel.register(
                name = editName.text.toString(),
                email = editEmail.text.toString(),
                password = editPassword.text.toString(),
            )
        }

        val onTextChange = { _: String ->
            registerViewModel.registerDataChanged(
                name = editName.text.toString(),
                email = editEmail.text.toString(),
                password = editPassword.text.toString(),
                confirmPassword = editConfirmPassword.text.toString()
            )
        }
        editName.addOnTextChange(onTextChange)
        editEmail.addOnTextChange(onTextChange)
        editPassword.addOnTextChange(onTextChange)
        editConfirmPassword.addOnTextChange(onTextChange)

        btnSignIn.setOnClickListener {
            finish()
        }
    }

    private fun updateUiWithUser(userView: RegisteredInUserView) {
        // TODO:
    }

    private fun showRegisterFailed(error: Int) {
        // TODO:
    }
}

fun EditText.addOnTextChange(onTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {
        }
    })
}
