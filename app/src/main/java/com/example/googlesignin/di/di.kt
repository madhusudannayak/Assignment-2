package com.example.googlesignin.di

import android.content.Context
import com.example.googlesignin.R
import com.example.googlesignin.splashscreen.viewmodel.SplashscreenViewModel
import com.example.googlesignin.auth.viewModel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashscreenViewModel() }
    viewModel { LoginViewModel() }



    fun provideGso(context: Context): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.webclient_id))
            .requestEmail()
            .build()

    fun provideSigninClient(context: Context, gso: GoogleSignInOptions) =
        GoogleSignIn.getClient(context, gso)

    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    single { provideGso(androidApplication()) }
    single { provideSigninClient(androidApplication(),get()) }

}