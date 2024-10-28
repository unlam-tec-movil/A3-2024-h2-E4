package ar.edu.unlam.mobile.scaffolding.evolution.domain.services

import android.net.Uri
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.CREATED_AT
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.IMAGES
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.IMAGE_NAME
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.URL
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.UserDataResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.AddImageToStorageResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.AddImageUrlToFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.GetImageFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.ImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageService
    @Inject
    constructor(
        private val storage: FirebaseStorage,
        private val db: FirebaseFirestore,
        private val auth: FirebaseAuth,
    ) : ImageRepository {
        override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse =
            try {
                val downloadUrl =
                    storage.reference
                        .child(IMAGES)
                        .child(auth.currentUser?.uid + IMAGE_NAME)
                        .putFile(imageUri)
                        .await()
                        .storage.downloadUrl
                        .await()
                UserDataResponse.Success(downloadUrl)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }

        override suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse =
            try {
                db
                    .collection(IMAGES)
                    .document(auth.currentUser?.uid.toString())
                    .set(
                        mapOf(
                            URL to download,
                            CREATED_AT to FieldValue.serverTimestamp(),
                        ),
                    ).await()
                UserDataResponse.Success(true)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }

        override suspend fun getImageUrlFromFirestore(): GetImageFromFirestoreResponse =
            try {
                val imageUrl =
                    db
                        .collection(IMAGES)
                        .document(auth.currentUser?.uid.toString())
                        .get()
                        .await()
                        .getString(URL)
                UserDataResponse.Success(imageUrl)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }
    }
