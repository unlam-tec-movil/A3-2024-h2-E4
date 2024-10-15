package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.network.model.SuperHeroImage
import ar.edu.unlam.mobile.scaffolding.domain.core.toSuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.domain.model.CombatDataScreen
import ar.edu.unlam.mobile.scaffolding.domain.services.SetCombatDataService
import javax.inject.Inject

class SetCombatDataUseCase @Inject constructor(private val combatDataScreen: CombatDataScreen) :
    SetCombatDataService {
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