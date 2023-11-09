package no.vipps.summerinternship.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v2/name/{name}")
    fun getCountry(@Path("name") name: String): Country

    @GET("v3.1/all")
    suspend fun getAllCountries(): List<Country>

}



