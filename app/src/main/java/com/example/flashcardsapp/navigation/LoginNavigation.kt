package com.example.flashcardsapp.navigation

sealed class LoginNavigation(val route: String) {
    object SignInScreen : LoginNavigation(route = "SignIn_Screen")
    object SignUpScreen : LoginNavigation(route = "SignUp_Screen")
}
