package ar.edu.unlam.mobile.scaffolding.evolution.domain.model

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CombatDataScreen
    @Inject
    constructor() {
        var playerCharacter: SuperHeroCombat? = null
        var comCharacter: SuperHeroCombat? = null
        var background: Background? = null
    }
