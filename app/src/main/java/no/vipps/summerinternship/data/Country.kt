package no.vipps.summerinternship.data
import com.squareup.moshi.Json


data class Country(
    @Json(name = "name")
    val name: Name,
    @Json(name = "capital")
    val capital: List<String>?=null,
    @Json(name = "altSpellings")
    val altSpellings: List<String>,
    @Json(name = "flag")
    val flag: String,
)

data class Name(
    @Json(name = "common")
    val common: String
)

