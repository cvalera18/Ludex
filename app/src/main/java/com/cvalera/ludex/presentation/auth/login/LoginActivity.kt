package com.cvalera.ludex.presentation.auth.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.cvalera.ludex.R
import com.cvalera.ludex.core.dialog.DialogFragmentLauncher
import com.cvalera.ludex.core.dialog.ErrorDialog
import com.cvalera.ludex.core.ex.dismissKeyboard
import com.cvalera.ludex.core.ex.loseFocusAfterAction
import com.cvalera.ludex.core.ex.onTextChanged
import com.cvalera.ludex.core.ex.show
import com.cvalera.ludex.core.ex.span
import com.cvalera.ludex.core.ex.toast
import com.cvalera.ludex.databinding.ActivityLoginBinding
import com.cvalera.ludex.presentation.MainActivity
import com.cvalera.ludex.presentation.auth.login.model.UserLogin
import com.cvalera.ludex.presentation.auth.signin.SignInActivity
import com.cvalera.ludex.presentation.auth.verification.VerificationActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, LoginActivity::class.java)

        private const val RC_SIGN_IN = 100
    }

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let {
                    loginViewModel.authenticateWithGoogle(it)
                    toast("Google sign in success")
                } ?: run {
                    Log.w(TAG, "ID Token is null")
                    toast("Google sign in failed: ID Token is null")
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                toast("Google sign in failed")
            }
        }
    }


    private fun initUI() {
        initListeners()
        initObservers()
        binding.viewBottom.tvFooter.text = span(
            getString(R.string.login_footer_unselected),
            getString(R.string.login_footer_selected)
        )
    }

    private fun initListeners() {
        binding.etEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.etEmail.onTextChanged { onFieldChanged() }

        binding.etPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
        binding.etPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.etPassword.onTextChanged { onFieldChanged() }

//        binding.tvForgotPassword.setOnClickListener { loginViewModel.onForgotPasswordSelected() }

        binding.viewBottom.tvFooter.setOnClickListener { loginViewModel.onSignInSelected() }

        binding.btnLogin.setOnClickListener {
            it.dismissKeyboard()
            loginViewModel.onLoginSelected(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (email.isNotBlank()) {
                loginViewModel.recoverPassword(email)
            } else {
                toast(getString(R.string.enter_email_message))
            }
        }

        binding.btnAnonymous.setOnClickListener {
            loginViewModel.signInAnonymously()
            // TODO Quitar este toast, es solo para probar.
            toast("Se accedió como anónimo")
        }

        binding.viewBottom.cardGoogle.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            googleSignInClient.signOut()

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    private fun initObservers() {
        // Observadores para la navegación y eventos específicos
        loginViewModel.navigateToList.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToList()
            }
        }

        loginViewModel.navigateToSignIn.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToSignIn()
            }
        }

        loginViewModel.navigateToForgotPassword.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToForgotPassword()
            }
        }

        loginViewModel.navigateToVerifyAccount.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToVerify()
            }
        }

        loginViewModel.showErrorDialog.observe(this) { userLogin ->
            if (userLogin.showErrorDialog) showErrorDialog(userLogin)
        }

        loginViewModel.passwordResetEmailSent.observe(this) { event ->
            event.getContentIfNotHandled()?.let { success ->
                if (success) {
                    toast(getString(R.string.password_reset_email_sent))
                } else {
                    toast(getString(R.string.password_reset_email_failed))
                }
            }
        }

        // Observador para los estados del LoginViewState
        lifecycleScope.launchWhenStarted {
            loginViewModel.viewState.collect { viewState ->
                updateUI(viewState)
                if (viewState.isUserAuthenticated) {
                    goToList()
                }
                viewState.errorMessage?.let { errorMessage ->
                    showErrorDialog(errorMessage)
                }
            }
        }
    }


    private fun updateUI(viewState: LoginViewState) {
        binding.pbLoading.isVisible = viewState.isLoading
        binding.tilEmail.error =
            if (viewState.isValidEmail) null else getString(R.string.login_error_mail)
        binding.tilPassword.error =
            if (viewState.isValidPassword) null else getString(R.string.login_error_password)
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            loginViewModel.onFieldsChanged(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
    }


    private fun showErrorDialog(userLogin: UserLogin) {
        ErrorDialog.create(
            title = getString(R.string.login_error_dialog_title),
            description = getString(R.string.login_error_dialog_body),
            negativeAction = ErrorDialog.Action(getString(R.string.login_error_dialog_negative_action)) {
                it.dismiss()
            },
            positiveAction = ErrorDialog.Action(getString(R.string.login_error_dialog_positive_action)) {
                loginViewModel.onLoginSelected(
                    userLogin.email,
                    userLogin.password
                )
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }

    private fun showErrorDialog(errorMessage: String) {
        ErrorDialog.create(
            title = getString(R.string.error),
            description = errorMessage,
            negativeAction = ErrorDialog.Action(getString(R.string.ok)) { dialog ->
                dialog.dismiss()
            }
        ).show(dialogLauncher, this)
    }

    private fun goToForgotPassword() {
        toast(getString(R.string.feature_not_allowed))
    }

    private fun goToSignIn() {
        startActivity(SignInActivity.create(this))
    }

    private fun goToList() {
        startActivity(MainActivity.create(this))
        finish()
    }

    private fun goToVerify() {
        startActivity(VerificationActivity.create(this))
    }
}