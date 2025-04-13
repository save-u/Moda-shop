package com.lycn.modashop.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.lycn.modashop.R
import com.lycn.modashop.data.repository.LoginRepository
import com.lycn.modashop.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginResult.postValue(LoginResult(success = LoggedInUserView(displayName = result.data)))
            } else {
                _loginResult.postValue(LoginResult(error = R.string.login_failed))
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        val isEmailValid = isEmailValid(email);
        val isPasswordValid = isPasswordValid(password)
        val isDataValid = isEmailValid && isPasswordValid
        _loginForm.value = (_loginForm.value ?: LoginFormState()).copy(
            usernameError = if (isEmailValid) null else R.string.invalid_username,
            passwordError = if (isPasswordValid) null else R.string.invalid_password,
            isDataValid = isDataValid
        )
    }

    // A placeholder username validation check
    private fun isEmailValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun isUserExisted(): Boolean {
        return loginRepository.isUserLogged()
    }
}