package ar.edu.unlam.mobile.scaffolding.evolution.data.network

import ar.edu.unlam.mobile.scaffolding.data.network.model.SuperHeroDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroApiClient {
    @GET("3080941948705073/search/{name}")
    suspend fun getHeroByName(
        @Path("name") superHeroName: String,
    ): Response<SuperHeroDataResponse>
}
