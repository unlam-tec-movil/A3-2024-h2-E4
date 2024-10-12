package ar.edu.unlam.mobile.scaffolding.ui.screens.selectComScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.domain.services.GetSuperHeroListByNameService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SetSuperHeroDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var comListDefault: List<SuperHeroItem>

@HiltViewModel
class SelectComScreenViewModel @Inject constructor(
    private val getSuperHeroListByName: GetSuperHeroListByNameService,
    private val setSuperHeroDetailUseCase: SetSuperHeroDetailUseCase) : ViewModel()
{
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _superHeroListCom = MutableStateFlow<List<SuperHeroItem>>(emptyList())
    val superHeroListCom = _superHeroListCom.asStateFlow()

    private val _comSelected = MutableStateFlow<SuperHeroItem?>(null)
    val comSelected = _comSelected.asStateFlow()

    private val _audioPosition = MutableStateFlow(0)
    val audioPosition = _audioPosition.asStateFlow()

    init {
        viewModelScope.launch {
            delay(5000)
            initListHero()
            _isLoading.value = false
        }
    }

    fun initListHero() {
        viewModelScope.launch {
            comListDefault = getSuperHeroListByName(getRandomList())
            _superHeroListCom.value = comListDefault
        }
    }

    fun setAudioPosition(currentPosition: Int) {
        _audioPosition.value = currentPosition + 1
    }

    private fun getRandomList(): String {
        val randomList =
            listOf("war", "iro","sup","dar","de","su", "ca", "ba", "sp", "go", "f", "hu", "ap", "man", "th", "ir", "dr", "do")
        return randomList.random()
    }

    fun searchHeroByNameToCom(query: String) {
        viewModelScope.launch {
            val list = getSuperHeroListByName(query)
            if (list.isNotEmpty()) {
                _superHeroListCom.value = list
            }
        }
    }

    fun setComHero(comHero: SuperHeroItem) {
        if (comHero == _comSelected.value) {
            _comSelected.value = null
        } else {
            _comSelected.value = comHero
        }
    }

    fun setSuperHeroDetail(hero: SuperHeroItem) {
        setSuperHeroDetailUseCase(hero)
    }
}

