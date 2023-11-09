package no.vipps.summerinternship.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import no.vipps.summerinternship.UiState
import no.vipps.summerinternship.data.ApiService
import no.vipps.summerinternship.data.Country
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainViewModel : ViewModel() {

    private val baseURL = "https://restcountries.com/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)

    val uiState = MutableStateFlow<UiState>(UiState.Loading)
    private var allCountries: List<Country> = mutableListOf()

    init {
        viewModelScope.launch {
            try {
                val countriesList = retrofit.getAllCountries()
                allCountries=countriesList

                uiState.value = UiState.Success(countriesList.sortedBy { it.name.common })
                Log.d("countries", countriesList.toString())
            } catch (e: Exception) {
                Log.e("Error", "Error fetching countries: ${e.message}", e)
                uiState.value = UiState.Error
            }
        }
    }

}
