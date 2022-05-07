package lostankit7.droid.stockmarket.presentation.company_info

import lostankit7.droid.stockmarket.domain.model.CompanyInfo
import lostankit7.droid.stockmarket.domain.model.IntradayInfo
import lostankit7.droid.stockmarket.util.StringHandler

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: StringHandler? = null,
)
