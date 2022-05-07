package lostankit7.droid.stockmarket.data.mapper

import lostankit7.droid.stockmarket.data.local.enitity.CompanyListingEntity
import lostankit7.droid.stockmarket.data.remote.dto.CompanyInfoDto
import lostankit7.droid.stockmarket.domain.model.CompanyInfo
import lostankit7.droid.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name, symbol, exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name, symbol, exchange)
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol ?: "",
        description ?: "",
        name ?: "",
        country ?: "",
        industry ?: ""
    )
}