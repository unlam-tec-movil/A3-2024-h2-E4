package ar.edu.unlam.mobile.scaffolding.evolution.data.repository

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.firestore_collection_userRanking
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.Background
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.CombatBackgroundsData
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.HeroDetail
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.ResultData
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.ResultDataScreen
import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.service.SuperHeroService
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SuperHeroRepository
    @Inject
    constructor(
        private val superHeroService: SuperHeroService,
        private val heroDetail: HeroDetail,
        private val combatBackgroundsData: CombatBackgroundsData,
        private val resultDataScreen: ResultDataScreen,
        private val firestore: FirebaseFirestore,
    ) : SuperHeroRepositoryInterface {
        override suspend fun getSuperHeroListByName(query: String): List<SuperHeroItem> = superHeroService.getSuperHeroList(query)

        override fun getHeroDetail(): SuperHeroItem? = heroDetail.superHeroDetail

        override fun setHeroDetail(hero: SuperHeroItem) {
            heroDetail.superHeroDetail = hero
        }

        override fun getCombatBackGround(): List<Background> = combatBackgroundsData.combatBackgroundsData

        override fun getResultDataScreen(): ResultDataScreen = resultDataScreen

        override fun setResultDataScreen(
            superHeroPlayer: SuperHeroCombat,
            superHeroCom: SuperHeroCombat,
            lifePlayer: Int,
            lifeCom: Int,
        ) {
            resultDataScreen.resultDataScreen =
                ResultData(superHeroPlayer, superHeroCom, lifePlayer, lifeCom)
        }

        override suspend fun getUserByIdFromFirestore(userId: String): Flow<UserRanked?> =
            callbackFlow {
                val listener =
                    firestore
                        .collection(firestore_collection_userRanking)
                        .document(userId)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                Log.e("FirestoreError", "Error al obtener el usuario.", error)
                                close(error)
                                return@addSnapshotListener
                            }
                            if (snapshot != null && snapshot.exists()) {
                                val user = snapshot.toObject(UserRanked::class.java)
                                trySend(user).isSuccess
                            } else {
                                trySend(null).isSuccess
                            }
                        }
                awaitClose { listener.remove() }
            }

        override suspend fun getAllUsersFromFireStore(): Flow<List<UserRanked>> =
            callbackFlow {
                val listener =
                    firestore
                        .collection(firestore_collection_userRanking)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                close(error)
                                return@addSnapshotListener
                            }
                            if (snapshot != null) {
                                val userList =
                                    snapshot.documents.mapNotNull {
                                        it.toObject(UserRanked::class.java)
                                    }
                                trySend(userList).isSuccess
                            }
                        }
                awaitClose { listener.remove() }
            }

        override suspend fun addUserFireStore(user: UserRanked) {
            firestore.collection(firestore_collection_userRanking).add(user)
        }

        override suspend fun updateUserRankingFireStore(user: UserRanked) {
            try {
                // Verificar si el usuario ya existe en Firestore
                val querySnapshot =
                    firestore
                        .collection(firestore_collection_userRanking)
                        .whereEqualTo("userID", user.userID)
                        .get()
                        .await()

                if (querySnapshot.documents.isNotEmpty()) {
                    // Si el usuario existe
                    for (document in querySnapshot.documents) {
                        val existingUser = document.toObject(UserRanked::class.java)
                        val newVictories = existingUser!!.userVictories!!.plus(1)
                        firestore
                            .collection(firestore_collection_userRanking)
                            .document(document.id)
                            .update("userVictories", newVictories) // update
                    }
                } else {
                    // Usuario no encontrado, agregarlo con 1 victoria
                    val newUser = user.copy(userVictories = 1)
                    addUserFireStore(newUser)
                }
            } catch (e: Exception) {
                Log.e("KlyxFirestore", "Error al buscar/actualizar el usuario", e)
            }
        }
    }
