package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem

interface SuperHeroRepositoryInterface {

    suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem>

    fun getHeroDetail(): SuperHeroItem?

    fun setHeroDetail(hero: SuperHeroItem)
}