package com.lycn.modashop.ui.auth.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.lycn.modashop.databinding.ActivityLoginBinding
import com.lycn.modashop.ui.auth.register.RegisterActivity
import com.lycn.modashop.ui.base.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputLayoutEmail = binding.inputLayoutEmail
        val editEmail = binding.editEmail
        val inputLayoutPassword = binding.inputLayoutPassword
        val editPassword = binding.editPassword
        val btnLogIn = binding.btnLogin
        val btnCreateAccount = binding.btnCreateAccount

        val loading = binding.pbLoading

        loginViewModel = ViewModelProvider(this, ViewModelFactory()).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnLogIn.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                inputLayoutEmail.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                inputLayoutPassword.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        btnCreateAccount.setOnClickListener {
            navigateToRegister()
        }

    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        // TODO: Handle login success 
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}