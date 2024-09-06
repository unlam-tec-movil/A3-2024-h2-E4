package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.*
import ar.edu.unlam.mobile.scaffolding.ui.screens.homeScreen.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectComScreen.SelectCom
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectMapScreen.SelectMap
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectPlayerScreen.SelectPlayer
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroCombatResultScreen.SuperHeroCombatResult
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroCombatScreen.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroDetailScreen.SuperHeroDetail
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroRankedScreen.SuperHeroRanked
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = HomeScreenRoute) {
                    composable<HomeScreenRoute> { HomeScreen(navController = navController)  }
                    composable<SelectComRoute> { SelectCom(navController = navController) }
                    composable<SelectMapRoute> { SelectMap(navController = navController) }
                    composable<SelectPlayerRoute> { SelectPlayer(navController = navController) }
                    composable<CombatResultRoute> { SuperHeroCombatResult(navController = navController) }
                    composable<CombatScreenRoute> { SuperHeroCombat(navController = navController) }
                    composable<DetailRoute> { SuperHeroDetail(navController = navController) }
                    composable<RankedRoute> { SuperHeroRanked(navController = navController) }


                }
            }
        }
    }
}


