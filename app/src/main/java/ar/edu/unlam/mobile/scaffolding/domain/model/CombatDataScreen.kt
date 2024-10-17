package ar.edu.unlam.mobile.scaffolding.domain.model

import ar.edu.unlam.mobile.scaffolding.data.local.Background
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
