package com.lycn.modashop.ui.auth.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.lycn.modashop.ui.home.MainActivity
import com.lycn.modashop.databinding.ActivityLoginBinding
import com.lycn.modashop.ui.auth.register.RegisterActivity
import com.lycn.modashop.ui.auth.register.addOnTextChange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (loginViewModel.isUserExisted()) {
            navigateToMain()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputLayoutEmail = binding.inputLayoutEmail
        val editEmail = binding.editEmail
        val inputLayoutPassword = binding.inputLayoutPassword
        val editPassword = binding.editPassword
        val btnLogIn = binding.btnLogin
        val btnCreateAccount = binding.btnCreateAccount
        val pbProductLoading = binding.pbLoading

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnLogIn.isEnabled = loginState.isDataValid

            inputLayoutEmail.error =
                if (loginState.usernameError == null) null else getString(loginState.usernameError)

            inputLayoutPassword.error =
                if (loginState.passwordError == null) null else getString(loginState.passwordError)
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            pbProductLoading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                navigateToMain()
            }
        })

        val onTextChange = { _: String ->
            loginViewModel.loginDataChanged(editEmail.text.toString(), editPassword.text.toString())
        }
        editEmail.addOnTextChange(onTextChange)
        editPassword.addOnTextChange(onTextChange)

        btnCreateAccount.setOnClickListener {
            navigateToRegister()
        }

        btnLogIn.setOnClickListener {
            loginViewModel.login(editEmail.text.toString(), editPassword.text.toString())
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun showLoginFailed(@StringRes error: Int) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}