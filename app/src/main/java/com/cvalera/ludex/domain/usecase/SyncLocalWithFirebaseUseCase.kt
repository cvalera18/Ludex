package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncLocalWithFirebaseUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        gameRepository.syncLocalWithFirebase()
    }
}