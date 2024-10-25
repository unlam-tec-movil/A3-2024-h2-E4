package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.rankedMapScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.toLatLng
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.rankedMapScreen.viewmodel.MapRankedViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun RankedMaps(
    navController: NavController,
    mapRankedViewModel: MapRankedViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val usersRanked by mapRankedViewModel.usersRanked.collectAsState()

    var selectedUser by remember { mutableStateOf<UserRanked?>(null) }
    var routePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var distance by remember { mutableStateOf<Double?>(null) }

    val cameraPositionState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(-34.6037, -58.3816), 10f)
        }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(modifier = Modifier.weight(1f), cameraPositionState = cameraPositionState) {
            usersRanked.forEach { userInfo ->
                Marker(
                    position = userInfo.userLocation!!.toLatLng(),
                    title = userInfo.userName,
                    snippet = userInfo.userVictories.toString(),
                    onClick = {
                        selectedUser = userInfo
                        routePoints =
                            listOf(
                                userInfo.userLocation.toLatLng(),
                                LatLng(-34.67055556, -58.56277778),
                            )
                        distance =
                            mapRankedViewModel.calculateDistance(
                                userInfo.userLocation.toLatLng(),
                                LatLng(-34.67055556, -58.56277778),
                            )
                        true
                    },
                )
            }

            // Dibuja la polilínea si hay puntos en routePoints
            if (routePoints.size > 1) {
                Polyline(
                    points = routePoints,
                    color = androidx.compose.ui.graphics.Color.Blue,
                    width = 5f,
                )
            }
        }

        if (distance != null) {
            Text(
                text = "Distancia: ${"%.2f".format(distance)} km",
                modifier = Modifier.padding(16.dp),
            )
        }

        selectedUser?.let { userInfo ->
            AlertDialog(
                onDismissRequest = { selectedUser = null },
                title = { Text(userInfo.userName!!) },
                text = {
                    Column {
                        Text(userInfo.userVictories.toString())
                        distance?.let { dist ->
                            Text("Distancia: %.2f km".format(dist))
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        mapRankedViewModel.openMaps(
                            userInfo.userLocation!!.toLatLng(),
                            context,
                        )
                        selectedUser = null // Cerrar el diálogo
                    }) {
                        Text("Cómo llegar")
                    }
                },
                dismissButton = {
                    Button(onClick = { selectedUser = null }) {
                        Text("Cerrar")
                    }
                },
            )
        }
    }

    BackHandler {
        navController.popBackStack()
    }
}
