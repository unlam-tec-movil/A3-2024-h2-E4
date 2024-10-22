package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class SetSuperHeroDetailUseCase
    @Inject
    constructor(
        private val superHeroRepository: SuperHeroRepositoryInterface,
    ) {
        operator fun invoke(hero: SuperHeroItem) {
            superHeroRepository.setHeroDetail(hero)
        }
    }
