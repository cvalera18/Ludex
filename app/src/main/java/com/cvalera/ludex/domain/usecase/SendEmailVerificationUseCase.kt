package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.AuthenticationService
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke() = authenticationService.sendVerificationEmail()
}