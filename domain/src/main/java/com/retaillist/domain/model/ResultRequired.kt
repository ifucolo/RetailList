package com.retaillist.domain.model

sealed class ResultRequired<out T> {
    data class Success<out T>(val result: T): ResultRequired<T>()
    sealed class Error: ResultRequired<Nothing>() {
        object ClientError: Error()
        object ConnectivityError: Error()
        object Other: Error()
        object ServerError: Error()
    }
}

