package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class GetCombatBackgroundDataUseCase
    @Inject
    constructor(
        private val repositoryInterface: SuperHeroRepositoryInterface,
    ) {
        operator fun invoke(): List<Background> = repositoryInterface.getCombatBackGround()
    }
