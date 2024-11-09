package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.qrGenerateScreen.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ScanResultViewModel
    @Inject
    constructor() : ViewModel() {
        private val _scanResult = MutableStateFlow<String?>(null)
        val scanResult: StateFlow<String?> = _scanResult

        fun setScanResult(result: String?) {
            _scanResult.value = result
        }
    }
