package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroRankedScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.Routes
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroRankedScreen.viewmodel.SuperHeroRankedViewModel

@Composable
fun SuperHeroRanked(
    navController: NavController,
    viewModel: SuperHeroRankedViewModel = hiltViewModel(),
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val usersRanked by viewModel.usersRanked.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn {
                items(usersRanked) {
                    CardView(it)
                }
            }

            Button(onClick = { navController.navigate(Routes.RankedMapsUsersRoute) }) {
                Text(text = "Maps")
            }
        }
    }
}

@Composable
fun CardView(userRanked: UserRanked) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = userRanked.userID.toString())
        Text(text = userRanked.userVictories.toString())
    }
}
