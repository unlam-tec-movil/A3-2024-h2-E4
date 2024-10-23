package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel
    @Inject
    constructor(
        private val repository: ImageRepository,
    ) : ViewModel() {
//        var addImageToStorageResponse by mutableStateOf<UserDataResponse<Uri>>(Success(null))
//            private set
//        var addImageToDatabaseResponse by mutableStateOf<UserDataResponse<Boolean>>(Success(null))
//            private set
//        var getImageFromDatabaseResponse by mutableStateOf<UserDataResponse<String>>(Success(null))
//            private set
//
//        fun addImageToStorage(imageUri: Uri) =
//            viewModelScope.launch {
//                addImageToStorageResponse = UserDataResponse.Loading
//                addImageToStorageResponse = repository.addImageToFirebaseStorage(imageUri)
//            }
//
//        fun addImageToDatabase(downloadUrl: Uri) =
//            viewModelScope.launch {
//                addImageToDatabaseResponse = UserDataResponse.Loading
//                addImageToDatabaseResponse = repository.addImageUrlToFirestore(downloadUrl)
//            }
//
//        fun getImageFromDatabase() =
//            viewModelScope.launch {
//                getImageFromDatabaseResponse = UserDataResponse.Loading
//                getImageFromDatabaseResponse = repository.getImageUrlFromFirestore()
//        }
    }
