package ar.edu.unlam.mobile.scaffolding.evolution.data.network.service

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.firestore_collection_userFutureFight
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.AVATAR
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.EMAIL
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.NAME
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.NICKNAME
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.USERDATA
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.USERID
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.utils.Constants.USERINFO
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.AddUserFireStoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.GetEmailFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.GetNameFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.GetNicknameFromFirestoreResponse
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.UserDataRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataService
    @Inject
    constructor(
        private val storage: FirebaseStorage,
        private val db: FirebaseFirestore,
        private val auth: FirebaseAuth,
    ) : UserDataRepository {
        override suspend fun getUserDataFromFirestore(userId: String): Flow<UserData?> =
            callbackFlow {
                val listener =
                    db
                        .collection(firestore_collection_userFutureFight)
                        .document(userId)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                Log.e("FirestoreError", "Error al obtener el usuario.", error)
                                close(error)
                                return@addSnapshotListener
                            }
                            if (snapshot != null && snapshot.exists()) {
                                val user = snapshot.toObject(UserData::class.java)
                                trySend(user).isSuccess
                            } else {
                                trySend(null).isSuccess
                            }
                        }
                awaitClose { listener.remove() }
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
