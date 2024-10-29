package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.UserDataRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetNickNameUseCase
    @Inject
    constructor(
        private val repository: UserDataRepository,
        private val auth: FirebaseAuth,
    ) {
        suspend operator fun invoke(): String =
            try {
                repository.getNicknameFromFirestore(auth.currentUser!!.uid)
            } catch (e: Exception) {
                "Player"
            }
    }
