package com.example.flashcardsapp.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.AuthRepository
import com.example.flashcardsapp.ui.signIn.SignInState
import com.example.flashcardsapp.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    val _signUpState = Channel<SignInState>()
    val signUpState = _signUpState.receiveAsFlow()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signUpState.send(SignInState(isSuccess = "Sign Up Success"))
                }
                is Resource.Loading -> {
                    _signUpState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(SignInState(isError = result.message))
                }
            }

        }
    }

}
