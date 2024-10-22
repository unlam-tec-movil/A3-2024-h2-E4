package ar.edu.unlam.mobile.scaffolding.evolution.domain.repository

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.ResultDataScreen
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.SuperHeroCombat

interface SuperHeroRepositoryInterface {
    suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem>

    fun getHeroDetail(): SuperHeroItem?

    fun setHeroDetail(hero: SuperHeroItem)

    fun getCombatBackGround(): List<Background>

    fun getResultDataScreen(): ResultDataScreen

    fun setResultDataScreen(
        superHeroPlayer: SuperHeroCombat,
        superHeroCom: SuperHeroCombat,
        lifePlayer: Int,
        lifeCom: Int,
    )
}
