package lostankit7.droid.stockmarket.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lostankit7.droid.stockmarket.domain.repository.StockRepository
import lostankit7.droid.stockmarket.util.Resource
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository,
) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
    private var searchQueryJob: Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.query)
                searchQueryJob?.cancel()
                searchQueryJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> TODO()
                        is Resource.Loading -> state = state.copy(isLoading = result.isLoading)
                        is Resource.Success -> result.data?.let {
                            state = state.copy(companies = result.data)
                        }
                    }
                }
        }
    }

}