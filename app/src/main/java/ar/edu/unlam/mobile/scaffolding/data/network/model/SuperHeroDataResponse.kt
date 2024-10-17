package ar.edu.unlam.mobile.scaffolding.data.network.model

import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem
import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val isSuccess: String,
    @SerializedName("results") val superheroes: List<SuperHeroItem>,
)
