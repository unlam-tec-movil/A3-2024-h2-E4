package ar.edu.unlam.mobile.scaffolding.evolution.data.repository

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse
import kotlinx.coroutines.flow.Flow

typealias GetNameFromFirestoreResponse = UserDataResponse<String>
typealias GetNicknameFromFirestoreResponse = UserDataResponse<String>
typealias GetEmailFromFirestoreResponse = UserDataResponse<String>
typealias AddUserFireStoreResponse = UserDataResponse<Boolean>

interface UserDataRepository {
    suspend fun getUserDataFromFirestore(userId: String): Flow<UserData?>

    suspend fun addUserFireStore(user: UserData): AddUserFireStoreResponse

    suspend fun getNameFromFirestoreResponse(): GetNameFromFirestoreResponse

    suspend fun getNicknameFromFirestoreResponse(): GetNicknameFromFirestoreResponse

    suspend fun getEmailFromFirestoreResponse(): GetEmailFromFirestoreResponse

    // suspend fun getAllUsersFromFireStore(): Flow<List<UserData>>
    // suspend fun updateUserRankingFireStore(user: UserData)
}
