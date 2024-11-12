package ar.edu.unlam.mobile.scaffolding.evolution.data.database

import com.google.android.gms.maps.model.LatLng

data class LocationUser(
    val latitude: Double? = null,
    val longitude: Double? = null,
)

fun LocationUser.toLatLng() = LatLng(this.latitude!!, this.longitude!!)
