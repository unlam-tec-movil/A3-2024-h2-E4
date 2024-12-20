package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.selectCharacterMap.superHeroDetailScreen.viewmodel

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetSuperHeroDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SuperHeroDetailScreenViewModel
    @Inject
    constructor(
        getSuperHeroDetail: GetSuperHeroDetailUseCase,
    ) : ViewModel() {
        private val _playerDetailScreen = MutableStateFlow<SuperHeroItem?>(null)
        val playerDetailScreen = _playerDetailScreen.asStateFlow()

        init {
            val superHero = getSuperHeroDetail()
            if (superHero != null) {
                _playerDetailScreen.value = superHero
            }
        }
    }
