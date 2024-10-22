package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.mapRanked

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
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.MarkerInfo
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.mapRanked.viewmodel.MapRankedViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline

@Composable
fun RankedMaps(
    navController: NavController,
    mapRankedViewModel: MapRankedViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val markers by mapRankedViewModel.markerList.collectAsState()

    var selectedMarker by remember { mutableStateOf<MarkerInfo?>(null) }
    var routePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var distance by remember { mutableStateOf<Double?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(modifier = Modifier.weight(1f)) {
            markers.forEach { markerInfo ->
                Marker(
                    position = markerInfo.position,
                    title = markerInfo.name,
                    snippet = markerInfo.victories,
                    onClick = {
                        selectedMarker = markerInfo
                        routePoints = listOf(markerInfo.position, LatLng(28.270833, -16.63916))
                        distance =
                            mapRankedViewModel.calculateDistance(
                                markerInfo.position,
                                LatLng(28.270833, -16.63916),
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

        selectedMarker?.let { marker ->
            AlertDialog(
                onDismissRequest = { selectedMarker = null },
                title = { Text(marker.name) },
                text = {
                    Column {
                        Text(marker.victories)
                        distance?.let { dist ->
                            Text("Distancia: %.2f km".format(dist))
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        mapRankedViewModel.openMaps(marker.position, context)
                        selectedMarker = null // Cerrar el diálogo
                    }) {
                        Text("Cómo llegar")
                    }
                },
                dismissButton = {
                    Button(onClick = { selectedMarker = null }) {
                        Text("Cerrar")
                    }
                },
            )
        }
    }
}
