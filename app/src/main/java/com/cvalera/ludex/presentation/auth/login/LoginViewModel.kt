package com.cvalera.ludex.presentation.auth.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvalera.ludex.core.Event
import com.cvalera.ludex.data.network.FirebaseClient
import com.cvalera.ludex.data.response.LoginResult
import com.cvalera.ludex.domain.usecase.CreateGoogleUserUseCase
import com.cvalera.ludex.domain.usecase.LoginUseCase
import com.cvalera.ludex.domain.usecase.RecoverPasswordUseCase
import com.cvalera.ludex.presentation.auth.login.model.UserLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val recoverPasswordUseCase: RecoverPasswordUseCase,
    private val firebaseClient: FirebaseClient,
    private val createGoogleUserUseCase: CreateGoogleUserUseCase
) : ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToList = MutableLiveData<Event<Boolean>>()
    val navigateToList: LiveData<Event<Boolean>>
        get() = _navigateToList

    private val _navigateToForgotPassword = MutableLiveData<Event<Boolean>>()
    val navigateToForgotPassword: LiveData<Event<Boolean>>
        get() = _navigateToForgotPassword

    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>>
        get() = _navigateToSignIn

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(UserLogin())
    val showErrorDialog: LiveData<UserLogin>
        get() = _showErrorDialog

    private val _passwordResetEmailSent = MutableLiveData<Event<Boolean>>()
    val passwordResetEmailSent: LiveData<Event<Boolean>> get() = _passwordResetEmailSent

    fun onLoginSelected(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        } else {
            onFieldsChanged(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)
            val result = loginUseCase(email, password)
            handleLoginResult(result)
        }
    }

    fun signInAnonymously() {
        viewModelScope.launch {
            val result = firebaseClient.signInAnonymously()
            _navigateToList.value = Event(result != null)
        }
    }

    fun onFieldsChanged(email: String, password: String) {
        _viewState.value = LoginViewState(
            isValidEmail = isValidEmail(email),
            isValidPassword = isValidPassword(password)
        )
    }

    fun authenticateWithGoogle(idToken: String?) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)
            idToken?.let {
                val success = createGoogleUserUseCase(it)
                if (success) {
                    _viewState.value = _viewState.value.copy(isUserAuthenticated = true)
                    _navigateToList.value = Event(true)
                } else {
                    _viewState.value =
                        _viewState.value.copy(errorMessage = "Failed to authenticate with Google")
                }
            }
        }
    }

    private fun handleLoginResult(result: LoginResult) {
        _viewState.value = _viewState.value.copy(isLoading = false)
        when (result) {
            is LoginResult.Success -> {
                _viewState.value = _viewState.value.copy(
                    isUserAuthenticated = result.verified,
                    authenticatedUser = UserLogin(email = result.email)
                )
                if (result.verified) {
                    _navigateToList.value = Event(true)
                } else {
                    _navigateToVerifyAccount.value = Event(true)
                }
            }

            is LoginResult.Error -> {
                _viewState.value = _viewState.value.copy(errorMessage = result.message)
                _showErrorDialog.value = UserLogin(showErrorDialog = true)
            }
        }
    }


    //    fun onForgotPasswordSelected() {
//        _navigateToForgotPassword.value = Event(true)
//    }

    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            val result = recoverPasswordUseCase(email)
            _passwordResetEmailSent.value = Event(result)
            if (!result) {
                _showErrorDialog.value = UserLogin(showErrorDialog = true)
            }
        }
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()

}