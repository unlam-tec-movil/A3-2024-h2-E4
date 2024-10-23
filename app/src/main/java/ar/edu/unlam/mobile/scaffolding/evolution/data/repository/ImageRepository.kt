package ar.edu.unlam.mobile.scaffolding.evolution.data.repository

import android.net.Uri
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse

typealias AddImageToStorageResponse = UserDataResponse<Uri>
typealias AddImageUrlToFirestoreResponse = UserDataResponse<Boolean>
typealias GetImageFromFirestoreResponse = UserDataResponse<String>

interface ImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse

    suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse

    suspend fun getImageUrlFromFirestore(): GetImageFromFirestoreResponse
}
