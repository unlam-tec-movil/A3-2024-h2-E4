package ar.edu.unlam.mobile.scaffolding.evolution.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNickProvider
    @Inject
    constructor() {
        var nickNameUser: String = "Player"
    }
