package ar.edu.unlam.mobile.scaffolding.ui.screens.selectCharacterMap.selectPlayerScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.domain.services.GetSuperHeroListByNameService
import ar.edu.unlam.mobile.scaffolding.domain.services.SetCombatDataService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCombatBackgroundDataUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SetSuperHeroDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var playerListDefault: List<SuperHeroItem>

@HiltViewModel
class SelectCharacterViewModel
    @Inject
    constructor(
        private val getSuperHeroListByName: GetSuperHeroListByNameService,
        private val setSuperHeroDetailUseCase: SetSuperHeroDetailUseCase,
        private val setCombatDataService: SetCombatDataService,
        private val getCombatBackgroundDataUseCase: GetCombatBackgroundDataUseCase,
    ) : ViewModel() {
        private val _isLoading = MutableStateFlow(true)
        val isLoading = _isLoading.asStateFlow()

        private val _superHeroList = MutableStateFlow<List<SuperHeroItem>>(emptyList())
        val superHeroList = _superHeroList.asStateFlow()

        private val _playerSelected = MutableStateFlow<SuperHeroItem?>(null)
        val playerSelected = _playerSelected.asStateFlow()

        private val _comSelected = MutableStateFlow<SuperHeroItem?>(null)
        val comSelected = _comSelected.asStateFlow()

        private val _audioPosition = MutableStateFlow(0)
        val audioPosition = _audioPosition.asStateFlow()

        private val _background = MutableStateFlow<Background?>(null)
        val background = _background.asStateFlow()
        private val _backgroundData = MutableStateFlow<List<Background>>(emptyList())
        val backgroundData = _backgroundData.asStateFlow()

        init {
            viewModelScope.launch {
                delay(5000)
                initListHero()
                _isLoading.value = false
            }
        }

        fun initListHero() {
            viewModelScope.launch {
                playerListDefault = getSuperHeroListByName(getRandomList())
                _superHeroList.value = playerListDefault
                _backgroundData.value = getCombatBackgroundDataUseCase()
            }
        }

        fun setAudioPosition(currentPosition: Int) {
            _audioPosition.value = currentPosition + 1
        }

        private fun getRandomList(): String {
            val randomList =
                listOf(
                    "war",
                    "iro",
                    "sup",
                    "dar",
                    "de",
                    "su",
                    "ca",
                    "ba",
                    "sp",
                    "go",
                    "f",
                    "hu",
                    "ap",
                    "man",
                    "th",
                    "ir",
                    "dr",
                    "do",
                )
            return randomList.random()
        }

        fun searchHeroByNameToPlayer(query: String) {
            viewModelScope.launch {
                val list = getSuperHeroListByName(query)
                if (list.isNotEmpty()) {
                    _superHeroList.value = list
                }
            }
        }

        fun setPlayer(player: SuperHeroItem) {
            if (player == _playerSelected.value) {
                _playerSelected.value = null
            } else {
                _playerSelected.value = player
            }
        }

        fun setSuperHeroDetail(hero: SuperHeroItem) {
            setSuperHeroDetailUseCase(hero)
        }

        fun setComHero(playerCom: SuperHeroItem) {
            if (playerCom == _comSelected.value) {
                _comSelected.value = null
            } else {
                _comSelected.value = playerCom
            }
        }

        fun setBackground(background: Background) {
            if (background == _background.value) {
                _background.value = null
            } else {
                _background.value = background
            }
        }

        fun setCombatDataScreen() {
            setCombatDataService.setSuperHeroPlayer(_playerSelected.value!!)
            setCombatDataService.setSuperHeroCom(_comSelected.value!!)
            setCombatDataService.setCombatScreen(_background.value!!)
        }
    }
