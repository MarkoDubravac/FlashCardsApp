package com.example.flashcardsapp.ui.signIn.di


import com.example.flashcardsapp.ui.signIn.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInModule = module {
    viewModel {
        SignInViewModel(
            repository = get()
        )
    }

}
