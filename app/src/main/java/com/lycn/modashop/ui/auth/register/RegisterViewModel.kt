package com.lycn.modashop.ui.auth.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lycn.modashop.R
import com.lycn.modashop.data.model.Result
import com.lycn.modashop.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(name: String, email: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch(context = Dispatchers.IO) {
            when (val result = registerRepository.register(name, email, password)) {
                is Result.Success -> {
                    _registerResult.postValue(RegisterResult(success = RegisteredInUserView(result.data)))
                }

                else -> {
                    _registerResult.postValue(RegisterResult(error = R.string.register_failed))
                }
            }
        }
    }

    fun registerDataChanged(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val isNameValid = isNameValid(name)
        val isEmailValid = isEmailValid(email)
        val isPasswordValid = isPasswordValid(password)
        val isConfirmPasswordValid = isConfirmPasswordValid(password, confirmPassword)
        _registerForm.value = (_registerForm.value ?: RegisterFormState()).copy(
            nameError = if (isNameValid) null else R.string.invalid_name,
            emailError = if (isEmailValid) null else R.string.invalid_email,
            passwordError = if (isPasswordValid) null else R.string.invalid_password,
            confirmPasswordError = if (isConfirmPasswordValid) null else R.string.invalid_confirm_password,
            isDataValid = isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
        )
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    // A Placeholder confirm password validation check
    private fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
        return isPasswordValid(password) && password == confirmPassword
    }
}