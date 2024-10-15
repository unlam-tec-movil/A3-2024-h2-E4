package ar.edu.unlam.mobile.scaffolding.data.repository


import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.data.local.CombatBackgroundsData
import ar.edu.unlam.mobile.scaffolding.data.local.HeroDetail
import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.network.service.SuperHeroService
import ar.edu.unlam.mobile.scaffolding.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class SuperHeroRepository @Inject constructor(
    private val superHeroService: SuperHeroService,
    private val heroDetail: HeroDetail,
    private val combatBackgroundsData: CombatBackgroundsData
) : SuperHeroRepositoryInterface {
    override suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem> {
        return superHeroService.getSuperHeroList(query)
    }

    override fun getHeroDetail(): SuperHeroItem? {
        return heroDetail.superHeroDetail
    }

    override fun setHeroDetail(hero: SuperHeroItem) {
        heroDetail.superHeroDetail = hero
    }

    override fun getCombatBackGround(): List<Background> {
        return combatBackgroundsData.combatBackgroundsData
    }
}