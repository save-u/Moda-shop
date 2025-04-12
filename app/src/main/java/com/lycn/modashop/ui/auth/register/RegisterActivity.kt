package com.lycn.modashop.ui.auth.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lycn.modashop.ui.home.MainActivity
import com.lycn.modashop.R
import com.lycn.modashop.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

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
        val btnBack = binding.imgBtnBack

        registerViewModel.registerFormState.observe(this) {
            val registerState = it ?: return@observe

            // disable login button unless both username / password is valid
            btnSignUp.isEnabled = registerState.isDataValid

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
                navigateToMain(registerResult.success)
            }
        }

        btnSignUp.setOnClickListener {
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

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun navigateToMain(userView: RegisteredInUserView) {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        //Complete and destroy register activity
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun showRegisterFailed(error: Int) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
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
