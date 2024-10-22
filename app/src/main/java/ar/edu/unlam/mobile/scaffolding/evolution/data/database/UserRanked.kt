package ar.edu.unlam.mobile.scaffolding.evolution.data.database

data class UserRanked(
    val userID: Int? = null,
    val userName: String? = null,
    val userLocation: LocationUser? = null,
    val userVictories: Int? = null,
)

data class LocationUser(
    val latitude: Double? = null,
    val longitude: Double? = null,
)
