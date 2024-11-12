package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.SuperHeroRepository
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.SuperHeroCombat
import javax.inject.Inject

class SetResultDataScreenUseCase
    @Inject
    constructor(
        private val repository: SuperHeroRepository,
    ) {
        operator fun invoke(
            superHeroPlayer: SuperHeroCombat,
            superHeroCom: SuperHeroCombat,
            lifePlayer: Int,
            lifeCom: Int,
        ) {
            repository.setResultDataScreen(superHeroPlayer, superHeroCom, lifePlayer, lifeCom)
        }
    }
