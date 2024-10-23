package ar.edu.unlam.mobile.scaffolding.evolution.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleStorage {
//    @Singleton
//    @Provides
//    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage
}
