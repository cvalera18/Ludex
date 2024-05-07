package com.cvalera.ludex.presentation.auth.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvalera.ludex.core.Event
import com.cvalera.ludex.domain.usecase.SendEmailVerificationUseCase
import com.cvalera.ludex.domain.usecase.VerifyEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    val verifyEmailUseCase: VerifyEmailUseCase
) : ViewModel() {

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _showContinueButton = MutableLiveData<Event<Boolean>>()
    val showContinueButton: LiveData<Event<Boolean>>
        get() = _showContinueButton

    init {
        viewModelScope.launch { sendEmailVerificationUseCase() }
        viewModelScope.launch {

            verifyEmailUseCase()
                .catch {

                }
                .collect { verification ->
                    if(verification){
                        _showContinueButton.value = Event(verification)
                    }
                }

        }
    }

    fun onGoToDetailSelected() {
        _navigateToVerifyAccount.value = Event(true)
    }
}