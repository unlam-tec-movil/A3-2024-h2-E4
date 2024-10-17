package ar.edu.unlam.mobile.scaffolding.data.local

import ar.edu.unlam.mobile.scaffolding.data.network.model.SuperHeroAppearance
import ar.edu.unlam.mobile.scaffolding.data.network.model.SuperHeroBiography
import ar.edu.unlam.mobile.scaffolding.data.network.model.SuperHeroImage
import ar.edu.unlam.mobile.scaffolding.data.network.model.SuperHeroPowerStats

data class SuperHeroItem(
    val id: String,
    val name: String,
    var powerstats: SuperHeroPowerStats,
    val biography: SuperHeroBiography,
    val appearance: SuperHeroAppearance,
    val image: SuperHeroImage,
    var imagePath: String? = null,
)
