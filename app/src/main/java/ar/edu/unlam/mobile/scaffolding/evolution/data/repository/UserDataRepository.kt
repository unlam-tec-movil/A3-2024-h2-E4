package ar.edu.unlam.mobile.scaffolding.evolution.data.repository

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse

typealias GetNameFromFirestoreResponse = UserDataResponse<String>
typealias GetNicknameFromFirestoreResponse = UserDataResponse<String>
typealias GetEmailFromFirestoreResponse = UserDataResponse<String>
typealias AddUserFireStoreResponse = UserDataResponse<Boolean>

interface UserDataRepository {
    suspend fun getUserDataFromFirestore(userId: String): UserData?

    suspend fun getUserDataAvatarUrl(userId: String): String

    suspend fun getNameFromFirestore(userId: String): String

    suspend fun getNicknameFromFirestore(userId: String): String

    suspend fun addUserFireStore(user: UserData): AddUserFireStoreResponse

    suspend fun getNameFromFirestoreResponse(): GetNameFromFirestoreResponse

    suspend fun getNicknameFromFirestoreResponse(): GetNicknameFromFirestoreResponse

    suspend fun getEmailFromFirestoreResponse(): GetEmailFromFirestoreResponse
}
