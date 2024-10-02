package ar.edu.unlam.mobile.scaffolding.data.repository


import ar.edu.unlam.mobile.scaffolding.data.local.HeroDetail
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.network.service.SuperHeroService
import javax.inject.Inject

class SuperHeroRepository @Inject constructor(
    private val superHeroService: SuperHeroService, private val heroDetail: HeroDetail

) {

    suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem> {
        return superHeroService.getSuperHeroList(query)
    }

    fun getHeroDetail(): SuperHeroItem? {
        return heroDetail.superHeroDetail
    }

    fun setHeroDetail(hero: SuperHeroItem) {
        heroDetail.superHeroDetail = hero
    }

}