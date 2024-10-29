package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetUserAvatarUrlUseCase
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetUserDataFromFireStoreUseCase
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.SetUserDataFireStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel
    @Inject
    constructor(
        private val getUserDataFromFireStoreUseCase: GetUserDataFromFireStoreUseCase,
        private val setUserDataFireStoreUseCase: SetUserDataFireStoreUseCase,
        private val getUserAvatarUrlUseCase: GetUserAvatarUrlUseCase,
    ) : ViewModel() {
        private var _userData = MutableStateFlow<UserData?>(null)
        val userData = _userData.asStateFlow()

        private var _avatarUrl = MutableStateFlow("")
        val avatarUrl = _avatarUrl.asStateFlow()

        init {
            viewModelScope.launch {
                _avatarUrl.value = getUserAvatarUrlUseCase()
                val userData = getUserDataFromFireStoreUseCase()
                userData.collect { user ->
                    _userData.value = user
                }
            }
        }

        fun updateNickName(newNickName: String) {
            val userUpdate = _userData.value!!.copy(nickname = newNickName)
            viewModelScope.launch {
                setUserDataFireStoreUseCase(userUpdate)
            }
        }
    }
