package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.ImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel
    @Inject
    constructor(
        private val repository: ImageRepository,
        private val auth: FirebaseAuth,
        private val db: FirebaseFirestore,
        private val storage: FirebaseStorage,
    ) : ViewModel() {
        private val userId = auth.currentUser?.uid ?: ""

        private val _name = MutableStateFlow("")
        val name: StateFlow<String> get() = _name

        private val _nickName = MutableStateFlow("")
        val nickName: StateFlow<String> get() = _nickName

        private val _avatarUrl = MutableStateFlow("")
        val avatarUrl: StateFlow<String> get() = _avatarUrl

        private val _infoUser = MutableStateFlow("")
        val infoUser: StateFlow<String> get() = _infoUser

        init {
            if (userId.isNotEmpty()) {
                getUserAvatarUrl(userId)
            }
        }

        private fun fetchUserProfile() {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch

                db.collection("users").document(userId).get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        _name.value = document.getString("name") ?: ""
                        _nickName.value = document.getString("nickName") ?: ""
                        _avatarUrl.value = document.getString("avatarUrl") ?: ""
                        _infoUser.value = document.getString("infoUser") ?: ""
                    }
                }
            }
        }

        fun updateName(newName: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("users").document(userId).update("name", newName)
                _name.value = newName
            }
        }

        fun updateNickName(newNickName: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("users").document(userId).update("nickName", newNickName)
                _nickName.value = newNickName
            }
        }

        fun updateAvatarUrl(newAvatarUrl: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("users").document(userId).update("avatarUrl", newAvatarUrl)
                _avatarUrl.value = newAvatarUrl
            }
        }

        fun updateInfoUser(newInfoUser: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("users").document(userId).update("infoUser", newInfoUser)
                _infoUser.value = newInfoUser
            }
        }

        private fun getUserAvatarUrl(userId: String) {
            viewModelScope.launch {
                val db = FirebaseFirestore.getInstance()
                val documentRef = db.collection("userAvatarImages").document(userId)
                try {
                    val documentSnapshot = documentRef.get().await()
                    if (documentSnapshot.exists()) {
                        _avatarUrl.value = documentSnapshot.getString("url") ?: ""
                    } else {
                        _avatarUrl.value = "default_url"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _avatarUrl.value = "default_url"
                }
            }
        }
    }
