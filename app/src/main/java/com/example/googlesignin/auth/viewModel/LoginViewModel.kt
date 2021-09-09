package com.example.googlesignin.auth.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val signIn = MutableLiveData<Boolean>()

    fun login() {
        signIn.value = true
    }

}