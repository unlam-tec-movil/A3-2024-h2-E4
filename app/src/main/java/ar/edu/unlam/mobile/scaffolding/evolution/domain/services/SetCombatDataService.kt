package ar.edu.unlam.mobile.scaffolding.evolution.domain.services

import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem

interface SetCombatDataService {
    fun setSuperHeroPlayer(superHeroItem: SuperHeroItem)

    fun setSuperHeroCom(superHeroItem: SuperHeroItem)

    fun setCombatScreen(background: Background)
}
