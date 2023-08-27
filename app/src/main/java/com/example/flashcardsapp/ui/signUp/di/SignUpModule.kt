package com.example.flashcardsapp.ui.signUp.di

import com.example.flashcardsapp.ui.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {
    viewModel {
        SignUpViewModel(
            repository = get()
        )
    }

}
