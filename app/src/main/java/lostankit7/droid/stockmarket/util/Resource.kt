package lostankit7.droid.stockmarket.util

sealed class Resource<T>(val data: T? = null, val message: StringHandler? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: StringHandler, data: T? = null): Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}