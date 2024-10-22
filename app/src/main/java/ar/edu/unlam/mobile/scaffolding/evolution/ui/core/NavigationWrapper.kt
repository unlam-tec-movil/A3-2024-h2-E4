package ar.edu.unlam.mobile.scaffolding.evolution.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.AuthenticationScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.CameraScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.CombatResultRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.CombatScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.CreateAccountScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.DetailRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.HomeScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.QRGenerateScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.RankedRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.SelectComRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.SelectMapRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.SelectPlayerRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.SignUpScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.UserProfileScreenRoute
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.viewmodel.NavigationWrapperViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.qrGenerateScreen.QRGenerateScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroCombatResultScreen.SuperHeroCombatResult
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroCombatScreen.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroRankedScreen.SuperHeroRanked
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.authenticationScreen.AuthenticationScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.cameraScreen.CameraScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.createAccountScreen.CreateAccountScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.homeScreen.ui.HomeScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.signUpScreen.SignUpScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.UserProfileScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.selectCharacterMap.selectComScreen.SelectCom
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.selectCharacterMap.selectMapScreen.SelectMap
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.selectCharacterMap.selectPlayerScreen.SelectPlayer
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.selectCharacterMap.selectPlayerScreen.viewmodel.SelectCharacterViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.selectCharacterMap.superHeroDetailScreen.SuperHeroDetail

@Composable
fun NavigationWrapper(viewModel: NavigationWrapperViewModel = hiltViewModel()) {
    val auth by viewModel.auth.collectAsState()
    val navController = rememberNavController()
    val selectCharacterViewModel: SelectCharacterViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = HomeScreenRoute) {
        composable<HomeScreenRoute> { HomeScreen(navController = navController) }
        composable<SelectMapRoute> {
            SelectMap(
                navController = navController,
                selectCharacterViewModel = selectCharacterViewModel,
            )
        }
        composable<SelectPlayerRoute> {
            SelectPlayer(
                navController = navController,
                selectCharacterViewModel = selectCharacterViewModel,
            )
        }
        composable<SelectComRoute> {
            SelectCom(
                navController = navController,
                selectCharacterViewModel = selectCharacterViewModel,
            )
        }
        composable<CombatResultRoute> { SuperHeroCombatResult(navController = navController) }
        composable<CombatScreenRoute> { SuperHeroCombat(navController = navController) }
        composable<DetailRoute> {
            SuperHeroDetail(
                navController = navController,
                selectCharacterViewModel = selectCharacterViewModel,
            )
        }
        composable<RankedRoute> { SuperHeroRanked(navController = navController) }
        composable<CameraScreenRoute> {
            CameraScreen(
                navController = navController,
            )
        }
        composable<QRGenerateScreenRoute> { QRGenerateScreen() }
        composable<SignUpScreenRoute> { SignUpScreen(navController = navController, auth) }
        composable<CreateAccountScreenRoute> {
            CreateAccountScreen(
                navController = navController,
                auth,
            )
        }
        composable<AuthenticationScreenRoute> {
            AuthenticationScreen(
                navController = navController,
                auth,
            )
        }

        composable<UserProfileScreenRoute> {
            UserProfileScreen(
                auth = auth,
                navController = navController,
            )
        }
    }
}
