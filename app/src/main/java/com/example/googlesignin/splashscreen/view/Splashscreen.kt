package com.example.googlesignin.splashscreen.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.googlesignin.R
import com.example.googlesignin.splashscreen.viewmodel.SplashscreenViewModel
import com.example.googlesignin.databinding.ActivitySplashscreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class Splashscreen : AppCompatActivity() {
    private val splashscreenViewModel: SplashscreenViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySplashscreenBinding>(
            this,
            R.layout.activity_splashscreen
        )
        binding.splashviewModel = splashscreenViewModel
        binding.lifecycleOwner = this
        splashscreenViewModel.openLoginScreen()
        onActionPerform()
    }

    private fun onActionPerform() {

        splashscreenViewModel.openLoginActivity.observe(this, {
            splashscreenViewModel.doNavigateToHome(this)
            finish()
        })
    }
}
