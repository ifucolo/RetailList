package com.retaillist.data.remote.mapper

import com.retaillist.data.remote.model.RemoteResponse
import com.retaillist.domain.model.ResultRequired
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

fun <T> Response<T>.mapRawResponse(): RemoteResponse<T> =
    try {
        if (isSuccessful) {
            RemoteResponse.Success(code = code(), body = body()) as RemoteResponse<T>
        } else {
            mapHttpException(HttpException(this))
        }
    } catch (throwable: Throwable) {
        map(throwable)
    }

 fun <T> map(ex: Throwable): RemoteResponse<T> {
    return when (ex) {
        is UnknownHostException,
        is ConnectException,
        is SocketTimeoutException,
        is SocketException,
        is SSLException -> {
            RemoteResponse.Error.ConnectivityError(ex)
        }
        is HttpException -> mapHttpException(ex)
        else -> {
            RemoteResponse.Error.Other(message = ex.message, cause = ex)
        }
    }
}


fun Throwable.mapThrowable(): ResultRequired<Nothing> {
    return when (this) {
        is UnknownHostException,
        is ConnectException,
        is SocketTimeoutException,
        is SocketException,
        is SSLException -> {
            RemoteResponse.Error.ConnectivityError(this).mapErrorToResultRequired()
        }
        is HttpException -> {
            mapHttpExceptionToRequired(this)
        }
        else -> {
            RemoteResponse.Error.Other(message = this.message, cause = this).mapErrorToResultRequired()
        }
    }
}

private fun mapHttpExceptionToRequired(ex: HttpException): ResultRequired<Nothing> {
    val code = ex.code()
    val body = ex.response()?.errorBody()?.string()

    return when (code) {
        400 -> RemoteResponse.Error.ClientError.BadRequest(body).mapErrorToResultRequired()
        401 -> RemoteResponse.Error.ClientError.Unauthorized.mapErrorToResultRequired()
        403 -> RemoteResponse.Error.ClientError.Forbidden(body).mapErrorToResultRequired()
        404 -> RemoteResponse.Error.ClientError.NotFound(body).mapErrorToResultRequired()
        409 -> RemoteResponse.Error.ClientError.Conflict(body).mapErrorToResultRequired()
        410 -> RemoteResponse.Error.ClientError.Gone(body).mapErrorToResultRequired()
        in 400..499 -> RemoteResponse.Error.ClientError.Other(code, body).mapErrorToResultRequired()
        in 500..599 -> RemoteResponse.Error.ServerError(code).mapErrorToResultRequired()
        else -> throw UnsupportedOperationException("HttpException with code=$code, body=$body")
    }
}

private fun <T> mapHttpException(ex: HttpException): RemoteResponse<T> {
    val code = ex.code()
    val body = ex.response()?.errorBody()?.string()

    return when (code) {
        400 -> RemoteResponse.Error.ClientError.BadRequest(body)
        401 -> RemoteResponse.Error.ClientError.Unauthorized
        403 -> RemoteResponse.Error.ClientError.Forbidden(body)
        404 -> RemoteResponse.Error.ClientError.NotFound(body)
        409 -> RemoteResponse.Error.ClientError.Conflict(body)
        410 -> RemoteResponse.Error.ClientError.Gone(body)
        in 400..499 -> RemoteResponse.Error.ClientError.Other(code, body)
        in 500..599 -> RemoteResponse.Error.ServerError(code)
        else -> throw UnsupportedOperationException("HttpException with code=$code, body=$body")
    }
}

fun RemoteResponse.Error.mapErrorToResultRequired(): ResultRequired<Nothing> {
    return when(this) {
        is RemoteResponse.Error.ClientError -> {
            ResultRequired.Error.ClientError
        }
        is RemoteResponse.Error.ConnectivityError -> {
            ResultRequired.Error.ConnectivityError
        }
        is RemoteResponse.Error.Other ->  {
            ResultRequired.Error.Other
        }
        is RemoteResponse.Error.ServerError ->  {
            ResultRequired.Error.ServerError
        }
    }
}