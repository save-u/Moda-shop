package com.lycn.modashop.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lycn.modashop.data.model.LoggedInUser
import com.lycn.modashop.data.model.Result
import com.lycn.modashop.services.firebase.AuthService
import com.lycn.modashop.services.firebase.DefaultUserStoreService
import com.lycn.modashop.services.firebase.FirebaseAuthServiceCallBack
import com.lycn.modashop.services.firebase.UserStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val userStoreService: UserStoreService
) : ViewModel() {

    private val _loggedInUserResult = MutableLiveData<LoggedInUser>()
    val loggedInUserResult: LiveData<LoggedInUser> = _loggedInUserResult

    fun signOut() {
        authService.signOut()
    }

    fun fetchLoggedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userStoreService.getLoggedInUser(authService.currentUser()!!.email!!)

            when (result) {
                is Result.Success -> {
                    _loggedInUserResult.postValue(result.data)
                }

                is Result.Error -> {
                    // TODO:
                }
            }
        }
    }
}