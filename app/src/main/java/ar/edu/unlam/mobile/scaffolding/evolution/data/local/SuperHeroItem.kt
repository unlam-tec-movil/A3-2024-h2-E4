package ar.edu.unlam.mobile.scaffolding.evolution.data.local

import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.SuperHeroAppearance
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.SuperHeroBiography
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.SuperHeroImage
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.SuperHeroPowerStats

data class SuperHeroItem(
    val id: String,
    val name: String,
    var powerstats: SuperHeroPowerStats,
    val biography: SuperHeroBiography,
    val appearance: SuperHeroAppearance,
    val image: SuperHeroImage,
    var imagePath: String? = null,
)
