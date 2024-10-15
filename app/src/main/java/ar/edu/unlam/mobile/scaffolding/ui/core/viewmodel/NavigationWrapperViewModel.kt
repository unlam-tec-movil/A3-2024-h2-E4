package ar.edu.unlam.mobile.scaffolding.ui.core.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationWrapperViewModel@Inject constructor(firebaseAuth: FirebaseAuth):ViewModel() {
    private val _auth = MutableStateFlow(firebaseAuth)
    val auth = _auth.asStateFlow()

}