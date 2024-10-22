package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.viewmodel

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel
    @Inject
    constructor(
        storageService: StorageService,
    ) : ViewModel()
