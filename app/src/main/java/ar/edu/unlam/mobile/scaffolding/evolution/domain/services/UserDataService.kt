package ar.edu.unlam.mobile.scaffolding.evolution.domain.services

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.firestore_collection_userFutureFight
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.AVATAR
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.EMAIL
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.IMAGES
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.NAME
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.NICKNAME
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.USERDATA
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.USERID
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.USERINFO
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.UserDataResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.AddUserFireStoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.GetEmailFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.GetNameFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.GetNicknameFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.UserDataRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataService
    @Inject
    constructor(
        private val storage: FirebaseStorage,
        private val db: FirebaseFirestore,
        private val auth: FirebaseAuth,
    ) : UserDataRepository {
        override suspend fun getUserDataFromFirestore(userId: String): UserData? =
            try {
                val documentSnapshot =
                    db
                        .collection(USERDATA)
                        .document(userId)
                        .get()
                        .await()
                if (documentSnapshot.exists()) {
                    documentSnapshot.toObject(UserData::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        override suspend fun addUserFireStore(user: UserData): AddUserFireStoreResponse =
            try {
                db
                    .collection(firestore_collection_userFutureFight)
                    .document(auth.currentUser?.uid.toString())
                    .set(
                        mapOf(
                            AVATAR to user.avatarUrl,
                            EMAIL to user.email,
                            USERINFO to user.infoUser,
                            NAME to user.name,
                            NICKNAME to user.nickname,
                            USERID to user.userID,
                        ),
                    ).await()
                UserDataResponse.Success(true)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }

        override suspend fun getNameFromFirestore(userId: String): String {
            val documentRef = db.collection(USERDATA).document(userId)
            var errorRef: String
            try {
                val documentSnapshot = documentRef.get().await()
                if (documentSnapshot.exists()) {
                    return documentSnapshot.getString(NAME) ?: ""
                } else {
                    errorRef = "default_url" // TODO tengo manejar los errores como gente que sabe
                    return errorRef
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorRef = "default_url" // TODO tengo manejar los errores como gente que sabe
                return errorRef
            }
        }

        override suspend fun getNicknameFromFirestore(userId: String): String {
            val documentRef = db.collection(USERDATA).document(userId)
            var errorRef: String
            try {
                val documentSnapshot = documentRef.get().await()
                if (documentSnapshot.exists()) {
                    return documentSnapshot.getString(NICKNAME) ?: ""
                } else {
                    errorRef = "default_url" // TODO tengo manejar los errores como gente que sabe
                    return errorRef
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorRef = "default_url" // TODO tengo manejar los errores como gente que sabe
                return errorRef
            }
        }

        override suspend fun getUserDataAvatarUrl(userId: String): String {
            val db =
                FirebaseFirestore.getInstance() // manifiesta la instancia actual de la Firestore en uso
            val documentRef = db.collection(IMAGES).document(userId)
            var errorRef: String
            try {
                val documentSnapshot =
                    documentRef
                        .get()
                        .await() // aca buscamos en la colección IMAGES, el documento del usuario
                if (documentSnapshot.exists()) {
                    return documentSnapshot.getString("url")
                        ?: "" // si existe el documento y el campo correcto, lo devuelvo
                } else {
                    errorRef = "default_url" // TODO tengo manejar los errores como gente que sabe
                    return errorRef
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorRef = "default_url" // TODO tengo manejar los errores como gente que sabe
                return errorRef
            }
        }

        // TODO Funciones que implementan el UserDataResponse - traen datos Success {Data = Ejemplo}

        override suspend fun getNameFromFirestoreResponse(): GetNameFromFirestoreResponse =
            try {
                val name =
                    db
                        .collection(USERDATA)
                        .document(auth.currentUser?.uid.toString())
                        .get()
                        .await()
                        .getString(NAME)
                UserDataResponse.Success(name)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }

        override suspend fun getNicknameFromFirestoreResponse(): GetNicknameFromFirestoreResponse =
            try {
                val nickname =
                    db
                        .collection(USERDATA)
                        .document(auth.currentUser?.uid.toString())
                        .get()
                        .await()
                        .getString(NICKNAME)
                UserDataResponse.Success(nickname)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }

        override suspend fun getEmailFromFirestoreResponse(): GetEmailFromFirestoreResponse =
            try {
                val email =
                    db
                        .collection(USERDATA)
                        .document(auth.currentUser?.uid.toString())
                        .get()
                        .await()
                        .getString(EMAIL)
                UserDataResponse.Success(email)
            } catch (e: Exception) {
                UserDataResponse.Failure(e)
            }
    }
