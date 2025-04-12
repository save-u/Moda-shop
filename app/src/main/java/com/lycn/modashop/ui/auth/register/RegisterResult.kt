package com.lycn.modashop.ui.auth.register

import com.lycn.modashop.ui.auth.login.LoggedInUserView

data class RegisterResult(
    val success: RegisteredInUserView? = null,
    val error: Int? = null
)