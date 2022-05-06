package lostankit7.droid.stockmarket.presentation.company_listings

import lostankit7.droid.stockmarket.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
)
