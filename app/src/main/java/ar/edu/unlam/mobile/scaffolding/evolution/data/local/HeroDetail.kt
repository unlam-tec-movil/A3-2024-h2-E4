package ar.edu.unlam.mobile.scaffolding.evolution.data.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroDetail
    @Inject
    constructor() {
        var superHeroDetail: SuperHeroItem? = null
    }