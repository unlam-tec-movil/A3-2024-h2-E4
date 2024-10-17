package ar.edu.unlam.mobile.scaffolding.evolution.data.network.model

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val isSuccess: String,
    @SerializedName("results") val superheroes: List<SuperHeroItem>,
)
