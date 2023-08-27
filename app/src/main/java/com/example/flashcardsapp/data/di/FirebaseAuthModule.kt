package com.example.flashcardsapp.data.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val firebaseAuthModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
}
