package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.domain.core.toSuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.CombatDataScreen
import ar.edu.unlam.mobile.scaffolding.evolution.domain.services.SetCombatDataService
import javax.inject.Inject

class SetCombatDataUseCase
    @Inject
    constructor(
        private val combatDataScreen: CombatDataScreen,
    ) : SetCombatDataService {
        override fun setSuperHeroPlayer(superHeroItem: SuperHeroItem) {
            combatDataScreen.playerCharacter = superHeroItem.toSuperHeroCombat()
        }

        override fun setSuperHeroCom(superHeroItem: SuperHeroItem) {
            combatDataScreen.comCharacter = superHeroItem.toSuperHeroCombat()
        }

        override fun setCombatScreen(background: Background) {
            combatDataScreen.background = background
        }
    }
