package com.example.googlesignin.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.googlesignin.R
import com.example.googlesignin.auth.viewModel.LoginViewModel
import com.example.googlesignin.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    companion object {
        const val REQUEST_CODE_SIGN_IN = 0
    }

    private lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )
        binding.loginviewModel = loginViewModel
        binding.lifecycleOwner = this
        auth = FirebaseAuth.getInstance()



        onActionPerform()
        return binding.root
    }

    private fun onActionPerform() {
        loginViewModel.signIn.observe(requireActivity(), {
            login()
        })


    }

    private fun login() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()
        val signInClient = GoogleSignIn.getClient(requireContext(), options)
        signInClient.signInIntent.also {
            startActivityForResult(it, REQUEST_CODE_SIGN_IN)
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully logged in", Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            val account1 = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = account1.exception
            if (account1.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = account1.getResult(ApiException::class.java)!!
                    Toast.makeText(
                        requireContext(),
                        "firebaseAuthWithGoogle:" + account.email,
                        Toast.LENGTH_LONG
                    ).show()

                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    //  firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }



            account?.let {
                googleAuthForFirebase(it)
            }
        }
    }


}
