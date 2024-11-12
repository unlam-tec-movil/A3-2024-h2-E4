package ar.edu.unlam.mobile.scaffolding.evolution.data.local

import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.SuperHeroCombat

data class ResultData(
    var superHeroPlayer: SuperHeroCombat,
    var superHeroCom: SuperHeroCombat,
    var lifePlay: Int,
    var lifeCom: Int,
)
