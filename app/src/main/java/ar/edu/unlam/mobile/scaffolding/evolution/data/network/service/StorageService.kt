package ar.edu.unlam.mobile.scaffolding.evolution.data.network.service

import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class StorageService
    @Inject
    constructor(
        private val storage: FirebaseStorage,
    ) {
    }
