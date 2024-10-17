package ar.edu.unlam.mobile.scaffolding.evolution.domain.services

import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem

interface GetSuperHeroListByNameService {
    suspend operator fun invoke(query: String): List<SuperHeroItem>
}
