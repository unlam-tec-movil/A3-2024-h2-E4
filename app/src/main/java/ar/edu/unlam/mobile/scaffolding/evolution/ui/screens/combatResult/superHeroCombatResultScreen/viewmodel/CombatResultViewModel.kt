package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroCombatResultScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.LocationUser
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.ResultDataScreen
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetResultDataScreenUseCase
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.UpdateUserRankingFireStore
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CombatResultViewModel
    @Inject
    constructor(
        private val getResultDataScreen: GetResultDataScreenUseCase,
        private val updateUserRankingFireStore: UpdateUserRankingFireStore,
        private val firebaseAuth: FirebaseAuth,
    ) : ViewModel() {
        private val _result = MutableStateFlow<ResultDataScreen?>(null)
        val result = _result.asStateFlow()

        private val _isLoading = MutableStateFlow(true)
        val isLoading = _isLoading.asStateFlow()

        private val _playerWin = MutableStateFlow(false)
        val playerWin = _playerWin.asStateFlow()

        init {
            viewModelScope.launch {
                _result.value = getResultDataScreen()
                _playerWin.value = checkIfPlayerWin(_result.value!!)
                if (_playerWin.value) {
                    updateUserRanking()
                }
                _isLoading.value = false
            }
        }

        private fun checkIfPlayerWin(resultDataScreen: ResultDataScreen): Boolean {
            val playerLife = resultDataScreen.resultDataScreen!!.superHeroPlayer.life
            val comLife = resultDataScreen.resultDataScreen!!.superHeroCom.life
            return playerLife > comLife
        }

        fun resetLife() {
            val resultData = _result.value ?: return
            resultData.resultDataScreen!!.superHeroPlayer.life = resultData.resultDataScreen!!.lifePlay
            resultData.resultDataScreen!!.superHeroCom.life = resultData.resultDataScreen!!.lifeCom
        }

        private fun updateUserRanking() {
            viewModelScope.launch {
                val userRanked2 =
                    UserRanked(
                        userID = firebaseAuth.currentUser?.uid,
                        userName = firebaseAuth.currentUser!!.email,
                        userLocation = LocationUser(latitude = 28.270833, longitude = -16.63916),
                        userVictories = 1,
                    )
                updateUserRankingFireStore(userRanked2)
            }
        }
    }
