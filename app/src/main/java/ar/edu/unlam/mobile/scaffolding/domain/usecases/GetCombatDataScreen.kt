package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.model.CombatDataScreen
import javax.inject.Inject

class GetCombatDataScreen @Inject constructor(private val combatDataScreen: CombatDataScreen) {
    operator fun invoke():CombatDataScreen{
        return combatDataScreen
    }
}