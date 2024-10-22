package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.ResultDataScreen
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.SuperHeroRepository
import javax.inject.Inject

class GetResultDataScreenUseCase
    @Inject
    constructor(
        private val repository: SuperHeroRepository,
    ) {
        operator fun invoke(): ResultDataScreen = repository.getResultDataScreen()
    }
