package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.UserNickProvider
import javax.inject.Inject

class GetNickNameUseCase
    @Inject
    constructor(
        private val userNickProvider: UserNickProvider,
    ) {
        operator fun invoke(): String = userNickProvider.nickNameUser
    }
