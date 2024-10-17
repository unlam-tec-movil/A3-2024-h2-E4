package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class GetSuperHeroDetailUseCase
    @Inject
    constructor(
        private val superHeroRepository: SuperHeroRepositoryInterface,
    ) {
        operator fun invoke(): SuperHeroItem? = superHeroRepository.getHeroDetail()
    }
