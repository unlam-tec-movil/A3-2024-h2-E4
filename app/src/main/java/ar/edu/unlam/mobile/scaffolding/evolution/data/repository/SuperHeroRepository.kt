package ar.edu.unlam.mobile.scaffolding.evolution.data.repository

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.ResultData
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.ResultDataScreen
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.CombatBackgroundsData
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.HeroDetail
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.service.SuperHeroService
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class SuperHeroRepository
    @Inject
    constructor(
        private val superHeroService: SuperHeroService,
        private val heroDetail: HeroDetail,
        private val combatBackgroundsData: CombatBackgroundsData,
        private val resultDataScreen: ResultDataScreen,
    ) : SuperHeroRepositoryInterface {
        override suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem> = superHeroService.getSuperHeroList(query)

        override fun getHeroDetail(): SuperHeroItem? = heroDetail.superHeroDetail

        override fun setHeroDetail(hero: SuperHeroItem) {
            heroDetail.superHeroDetail = hero
        }

        override fun getCombatBackGround(): List<Background> = combatBackgroundsData.combatBackgroundsData

        override fun getResultDataScreen(): ResultDataScreen = resultDataScreen

        override fun setResultDataScreen(
            superHeroPlayer: SuperHeroCombat,
            superHeroCom: SuperHeroCombat,
            lifePlayer: Int,
            lifeCom: Int,
        ) {
            resultDataScreen.resultDataScreen =
                ResultData(superHeroPlayer, superHeroCom, lifePlayer, lifeCom)
        }
    }
