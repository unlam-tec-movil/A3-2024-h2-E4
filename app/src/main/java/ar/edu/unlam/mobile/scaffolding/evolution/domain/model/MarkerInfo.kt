package ar.edu.unlam.mobile.scaffolding.evolution.domain.model

import com.google.android.gms.maps.model.LatLng

data class MarkerInfo(
    val position: LatLng,
    val name: String,
    val victories: String,
)
