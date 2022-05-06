package lostankit7.droid.stockmarket.data.mapper

import lostankit7.droid.stockmarket.data.local.enitity.CompanyListingEntity
import lostankit7.droid.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name, symbol, exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name, symbol, exchange)
}