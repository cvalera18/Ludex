package com.cvalera.ludex.presentation.auth.intro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cvalera.ludex.R
import com.cvalera.ludex.core.ex.toast
import com.cvalera.ludex.databinding.ActivityIntroductionBinding
import com.cvalera.ludex.presentation.MainActivity
import com.cvalera.ludex.presentation.auth.login.LoginActivity
import com.cvalera.ludex.presentation.auth.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding
    private val introductionViewModel: IntroductionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GameLista)
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            btnLogin.setOnClickListener { introductionViewModel.onLoginSelected() }
            btnSingIn.setOnClickListener { introductionViewModel.onSignInSelected() }
            btnAnonymous.setOnClickListener {
                introductionViewModel.signInAnonymously()
                // TODO Quitar este toast, es solo para probar.
                toast("Se accedió como anónimo")
            }
        }
    }


    private fun initObservers() {
        introductionViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        })
        introductionViewModel.navigateToSignIn.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToSingIn()
            }
        })
        introductionViewModel.navigateToList.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToList()
            }
        }
    }

    private fun goToList() {
        startActivity(MainActivity.create(this))
        finish()
    }

    private fun goToSingIn() {
        startActivity(SignInActivity.create(this))
    }

    private fun goToLogin() {
        startActivity(LoginActivity.create(this))
    }
}