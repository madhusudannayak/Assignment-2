package com.example.googlesignin

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlesignin.activity.HomeActivity

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