package com.cvalera.ludex.presentation.auth.verification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.cvalera.ludex.R
import com.cvalera.ludex.core.dialog.DialogFragmentLauncher
import com.cvalera.ludex.core.dialog.LoginSuccessDialog
import com.cvalera.ludex.core.ex.show
import com.cvalera.ludex.databinding.ActivityVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VerificationActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, VerificationActivity::class.java)
    }

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    private lateinit var binding: ActivityVerificationBinding
    private val verificationViewModel: VerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.btnGoToDetail.setOnClickListener { verificationViewModel.onGoToDetailSelected() }
    }

    private fun initObservers() {
        verificationViewModel.navigateToVerifyAccount.observe(this) {
            it.getContentIfNotHandled()?.let {
                LoginSuccessDialog.create().show(dialogLauncher, this)
            }
        }

        verificationViewModel.showContinueButton.observe(this) {
            it.getContentIfNotHandled()?.let {
                binding.btnGoToDetail.isVisible = true
            }
        }
    }
}