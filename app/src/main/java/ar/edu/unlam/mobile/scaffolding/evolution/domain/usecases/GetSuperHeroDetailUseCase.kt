package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class GetSuperHeroDetailUseCase
    @Inject
    constructor(
        private val superHeroRepository: SuperHeroRepositoryInterface,
    ) {
        operator fun invoke(): SuperHeroItem? = superHeroRepository.getHeroDetail()
    }
