package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.AuthenticationScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.CameraScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.CombatResultRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.CombatScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.DetailRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.HomeScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.QRGenerateScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.RankedRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.SelectComRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.SelectMapRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.SelectPlayerRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.SignUpScreenRoute
import ar.edu.unlam.mobile.scaffolding.ui.screens.authenticationScreen.AuthenticationScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.cameraScreen.CameraScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.homeScreen.ui.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.qrGenerateScreen.QRGenerateScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectComScreen.SelectCom
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectMapScreen.SelectMap
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectPlayerScreen.SelectPlayer
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectPlayerScreen.viewmodel.SelectCharacterViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.signUpScreen.SignUpScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroCombatResultScreen.SuperHeroCombatResult
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroCombatScreen.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroDetailScreen.SuperHeroDetail
import ar.edu.unlam.mobile.scaffolding.ui.screens.superHeroRankedScreen.SuperHeroRanked
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme {
                val navController = rememberNavController()
                val selectCharacterViewModel: SelectCharacterViewModel = hiltViewModel()
                NavHost(navController = navController, startDestination = HomeScreenRoute) {
                    composable<HomeScreenRoute> { HomeScreen(navController = navController) }
                    composable<SelectComRoute> { SelectCom(navController = navController) }
                    composable<SelectMapRoute> { SelectMap(navController = navController) }
                    composable<SelectPlayerRoute> {
                        SelectPlayer(
                            navController = navController,
                            selectCharacterViewModel = selectCharacterViewModel
                        )
                    }
                    composable<CombatResultRoute> { SuperHeroCombatResult(navController = navController) }
                    composable<CombatScreenRoute> { SuperHeroCombat(navController = navController) }
                    composable<DetailRoute> {
                        SuperHeroDetail(
                            navController = navController,
                            selectCharacterViewModel = selectCharacterViewModel
                        )
                    }
                    composable<RankedRoute> { SuperHeroRanked(navController = navController) }
                    composable<CameraScreenRoute> {
                        CameraScreen(
                            navController = navController
                        )
                    }
                    composable<QRGenerateScreenRoute> { QRGenerateScreen() }
                    composable<SignUpScreenRoute> { SignUpScreen(navController = navController, auth) }
                    composable<AuthenticationScreenRoute> { AuthenticationScreen(navController = navController, auth) }
                }
            }
        }
    }
}


