package ar.edu.unlam.mobile.scaffolding.evolution.domain.model

import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.SuperHeroImage

data class SuperHeroCombat(
    val id: String,
    val name: String,
    val image: SuperHeroImage,
    val damageAbsMax: Int,
    var attack: Int,
    var defense: Int,
    var life: Int,
    var damageAbs: Int,
    var damagePenance: Int,
    var healingPotion: Int,
)
