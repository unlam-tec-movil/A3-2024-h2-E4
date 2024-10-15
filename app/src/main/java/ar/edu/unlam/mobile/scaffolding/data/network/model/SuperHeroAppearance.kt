package ar.edu.unlam.mobile.scaffolding.data.network.model

import com.google.gson.annotations.SerializedName

data class SuperHeroAppearance(
    @SerializedName("gender") val gender: String,
    @SerializedName("race") val race: String
)
