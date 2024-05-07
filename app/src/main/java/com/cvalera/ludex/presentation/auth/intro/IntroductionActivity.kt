package com.cvalera.ludex.presentation.auth.intro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cvalera.ludex.R
import com.cvalera.ludex.databinding.ActivityIntroductionBinding
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
    }


    private fun goToSingIn() {
        startActivity(SignInActivity.create(this))
    }

    private fun goToLogin() {
        startActivity(LoginActivity.create(this))
    }
}