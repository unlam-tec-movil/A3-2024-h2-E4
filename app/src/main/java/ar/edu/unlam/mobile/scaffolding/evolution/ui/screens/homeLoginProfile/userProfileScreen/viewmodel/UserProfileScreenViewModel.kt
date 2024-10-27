package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.ImageRepository
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.UserDataRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

lateinit var userData: UserData

@HiltViewModel
class UserProfileScreenViewModel
    @Inject
    constructor(
        private val repository: ImageRepository,
        private val userDataRepository: UserDataRepository,
        private val auth: FirebaseAuth,
        private val db: FirebaseFirestore,
        private val storage: FirebaseStorage,
    ) : ViewModel() {
        private val userId = auth.currentUser?.uid ?: ""

        private var _userData = MutableStateFlow<UserData?>(null)
        val userData = _userData.asStateFlow()

        private val _name = MutableStateFlow("")
        val name: StateFlow<String> get() = _name

        private val _nickName = MutableStateFlow("")
        val nickName: StateFlow<String> get() = _nickName

        private val _avatarUrl = MutableStateFlow("")
        val avatarUrl: StateFlow<String> get() = _avatarUrl

        private val _email = MutableStateFlow("")
        val email: StateFlow<String> get() = _email

        private val _infoUser = MutableStateFlow("")
        val infoUser: StateFlow<String> get() = _infoUser

        init {
            if (userId.isNotEmpty()) {
                getUserAvatarUrl(userId)
            }
            // fetchUserProfile()
            getNameFromDatabase(userId)
            getNicknameFromDatabase(userId)
        }

        private fun fetchUserProfile() {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                val userData = userDataRepository.getUserDataFromFirestore(userId)
                userData?.let {
                    _name.value = it.name ?: ""
                    _nickName.value = it.nickname ?: ""
                    _infoUser.value = it.infoUser ?: ""
                    _email.value = it.email ?: ""
                }
            }
        }

        private fun getUserAvatarUrl(userId: String) {
            viewModelScope.launch {
                // val userId = auth.currentUser?.uid ?: return@launch
                _avatarUrl.value = userDataRepository.getUserDataAvatarUrl(userId)
            }
        }

        private fun getNameFromDatabase(userId: String) {
            viewModelScope.launch {
                _name.value = userDataRepository.getNameFromFirestore(userId)
            }
        }

        private fun getNicknameFromDatabase(userId: String) {
            viewModelScope.launch {
                _nickName.value = userDataRepository.getNicknameFromFirestore(userId)
            }
        }

        suspend fun setNuevoUsuario(nuevoUsuario: UserData) {
            userDataRepository.addUserFireStore(nuevoUsuario)
        }

        fun updateNickName(newNickName: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("UsersFutureFight").document(userId).update("nickName", newNickName)
                _nickName.value = newNickName
            }
        }

        fun updateInfoUser(newInfoUser: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("UsersFutureFight").document(userId).update("infoUser", newInfoUser)
                _infoUser.value = newInfoUser
            }
        }

        fun updateAvatarUrl(newAvatarUrl: String) {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                db.collection("UsersFutureFight").document(userId).update("avatarUrl", newAvatarUrl)
                _avatarUrl.value = newAvatarUrl
            }
        }

// TODO Funciones que implementan el UserDataResponse - traen datos Success {Data = Ejemplo}

        fun getNameResponse() {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                _name.value = userDataRepository.getNameFromFirestoreResponse().toString()
            }
        }

        fun getNicknameResponse() {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                _nickName.value = userDataRepository.getNicknameFromFirestoreResponse().toString()
            }
        }

        fun getEmailResponse() {
            viewModelScope.launch {
                val userId = auth.currentUser?.uid ?: return@launch
                _email.value = userDataRepository.getEmailFromFirestoreResponse().toString()
            }
        }
    }

//        private fun getUserAvatarUrl(userId: String) {
//            viewModelScope.launch {
//                val db = FirebaseFirestore.getInstance()
//                val documentRef = db.collection("userAvatarImages").document(userId)
//                try {
//                    val documentSnapshot = documentRef.get().await()
//                    if (documentSnapshot.exists()) {
//                        _avatarUrl.value = documentSnapshot.getString("url") ?: ""
//                    } else {
//                        _avatarUrl.value = "default_url"
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    _avatarUrl.value = "default_url"
//                }
//            }
//        }
//    }

//        private fun fetchUserProfile() {
//            viewModelScope.launch {
//                val userId = auth.currentUser?.uid ?: return@launch
//                db.collection(USERDATA).document(userId).get().addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        _name.value = document.getString(NAME) ?: ""
//                        _nickName.value = document.getString(NICKNAME) ?: ""
//                        _avatarUrl.value = document.getString(AVATAR) ?: ""
//                        _infoUser.value = document.getString(USERINFO) ?: ""
//                        _email.value = document.getString(EMAIL) ?: ""
//                    }
//                }
//            }
//        }

//        fun getNameFromDatabase() =
//            viewModelScope.launch {
//                getNameFromDatabaseResponse = UserDataResponse.Loading
//                getNameFromDatabaseResponse = userDataRepository.getNameFromFirestoreResponse()
//            }
//
//        fun getNicknameFromDatabase() =
//            viewModelScope.launch {
//                getNicknameFromDatabaseResponse = UserDataResponse.Loading
//                getNicknameFromDatabaseResponse = userDataRepository.getNicknameFromFirestoreResponse()
//            }
//
//        fun getEmailFromDatabase() =
//            viewModelScope.launch {
//                getEmailFromDatabaseResponse = UserDataResponse.Loading
//                getEmailFromDatabaseResponse = userDataRepository.getEmailFromFirestoreResponse()
//            }
