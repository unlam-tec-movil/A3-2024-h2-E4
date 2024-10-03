package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem

interface GetSuperHeroListByNameService {

    suspend operator fun invoke(query: String): List<SuperHeroItem>


}