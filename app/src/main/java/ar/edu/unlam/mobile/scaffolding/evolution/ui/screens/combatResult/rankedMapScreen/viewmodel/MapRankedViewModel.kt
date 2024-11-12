package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.rankedMapScreen.viewmodel

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetUsersRankingFireStore
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapRankedViewModel
    @Inject
    constructor(
        private val getUsersRankingFireStore: GetUsersRankingFireStore,
    ) : ViewModel() {
        private val _usersRanked = MutableStateFlow<List<UserRanked>>(emptyList())
        val usersRanked = _usersRanked.asStateFlow()

        private val _isLoading = MutableStateFlow(true)
        val isLoading = _isLoading.asStateFlow()

        init {
            viewModelScope.launch {
                val usersFlow: Flow<List<UserRanked>> = getUsersRankingFireStore()
                Log.i("FlowFirestore", "$usersFlow")
                usersFlow.collect { users ->
                    _usersRanked.value = users
                    Log.i("FlowFirestore", "${_usersRanked.value}")
                    _isLoading.value = false
                }
            }
        }

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
