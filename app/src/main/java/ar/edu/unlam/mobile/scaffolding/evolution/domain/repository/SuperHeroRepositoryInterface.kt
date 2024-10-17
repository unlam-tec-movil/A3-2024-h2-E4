package ar.edu.unlam.mobile.scaffolding.evolution.domain.repository

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem

interface SuperHeroRepositoryInterface {
    suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem>

    fun getHeroDetail(): SuperHeroItem?

    fun setHeroDetail(hero: SuperHeroItem)

    fun getCombatBackGround(): List<Background>
}
