package ar.edu.unlam.mobile.scaffolding.data.repository


import ar.edu.unlam.mobile.scaffolding.data.local.HeroDetail
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.network.service.SuperHeroService
import ar.edu.unlam.mobile.scaffolding.domain.repository.SuperHeroRepositoryInterface
import javax.inject.Inject

class SuperHeroRepository @Inject constructor(
    private val superHeroService: SuperHeroService,
    private val heroDetail: HeroDetail
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
}