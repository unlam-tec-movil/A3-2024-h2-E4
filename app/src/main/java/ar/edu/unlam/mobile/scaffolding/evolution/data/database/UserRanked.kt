package ar.edu.unlam.mobile.scaffolding.evolution.data.database

import com.google.android.gms.maps.model.LatLng

data class UserRanked(
    val userID: Int? = null,
    val userName: String? = null,
    val userLocation: LatLng? = null,
    val userVictories: Int = 0,
)
