package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.mapRanked.viewmodel

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.MarkerInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MapRankedViewModel
    @Inject
    constructor() : ViewModel() {
        private val _markerList = MutableStateFlow<List<MarkerInfo>>(emptyList())
        val markerList = _markerList.asStateFlow()

        init {

            // ACA SE DEBERIA CARGAR LOS RANKED  EN EL TITULO IRIA EL NOMBRE DEL JUGADOR Y CANTIDAD DE VICTORIAS
            val markers =
                mutableListOf<MarkerInfo>().apply {
                    add(MarkerInfo(position = LatLng(-34.666666666666664, -58.44805799945578), "PLAYER 1", "5 victories"))
                    add(MarkerInfo(position = LatLng(40.270880, -16.64000), "PLAYER 2", "8 victories"))
                    add(MarkerInfo(position = LatLng(35.000000, -15.00000), "PLAYER 3", "10 victories"))
                }
            _markerList.value = markers
        }

      /*  fun addMarker(): Boolean {
            _markerList

            return true
        }*/

        fun calculateDistance(
            start: LatLng,
            end: LatLng,
        ): Double {
            val startLocation =
                Location("start").apply {
                    latitude = start.latitude
                    longitude = start.longitude
                }
            val endLocation =
                Location("end").apply {
                    latitude = end.latitude
                    longitude = end.longitude
                }
            return (startLocation.distanceTo(endLocation) / 1000).toDouble() // Devuelve la distancia en kilómetros
        }

        fun openMaps(
            latLng: LatLng,
            context: Context,
        ) {
            val uri = Uri.parse("google.navigation:q=${latLng.latitude},${latLng.longitude}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            context.startActivity(intent)
        }
    }
