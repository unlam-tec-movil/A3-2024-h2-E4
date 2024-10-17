package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class GetCombatBackgroundDataUseCase
    @Inject
    constructor(
        private val repositoryInterface: SuperHeroRepositoryInterface,
    ) {
        operator fun invoke(): List<Background> = repositoryInterface.getCombatBackGround()
    }
