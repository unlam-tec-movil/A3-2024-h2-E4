package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.SuperHeroRepository
import javax.inject.Inject

class UpdateUserRankingFireStore
    @Inject
    constructor(
        private val repository: SuperHeroRepository,
    ) {
        suspend operator fun invoke(user: UserRanked) {
            repository.updateUserRankingFireStore(user)
        }
    }
