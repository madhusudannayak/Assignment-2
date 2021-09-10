package com.example.googlesignin.splashscreen.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlesignin.common.view.HomeActivity

class SplashscreenViewModel : ViewModel() {

    val openLoginActivity = MutableLiveData<Boolean>()

    fun openLoginScreen() {
        Handler().postDelayed({

            openLoginActivity.postValue(true)

        }, 3000L)
    }

    fun doNavigateToHome(context: Context) {
        context.startActivity(Intent(context, HomeActivity::class.java))
    }
}