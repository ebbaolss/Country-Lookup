package no.vipps.summerinternship

import no.vipps.summerinternship.data.Country

sealed interface UiState {
    data class Success(
        val countries: List<Country>,
    ) : UiState
    object Error : UiState
    object Loading : UiState
}