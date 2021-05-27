package com.retaillist.data.remote.model

sealed class RemoteResponse<out T> {
    data class Success<out T>(val code: Int, val body: T) : RemoteResponse<T>()

    sealed class Error: RemoteResponse<Nothing>() {
        sealed class ClientError : Error() {
            data class BadRequest(val body: String?) : ClientError() {
                override fun toString(): String {
                    return "BadRequest(body=$body)"
                }
            }

            object Unauthorized : ClientError() {
                override fun toString(): String = "Unauthorized"
            }

            data class Forbidden(val body: String?) : ClientError() {
                override fun toString(): String {
                    return "Forbidden(body=$body)"
                }
            }

            data class NotFound(val body: String?) : ClientError() {
                override fun toString(): String {
                    return "NotFound(body=$body)"
                }
            }

            data class Conflict(val body: String?) : ClientError() {
                override fun toString(): String {
                    return "Conflict(body=$body)"
                }
            }

            data class Gone(val body: String?) : ClientError() {
                override fun toString(): String {
                    return "Gone(body=$body)"
                }
            }

            data class Other(val code: Int, val body: String?) : ClientError() {
                override fun toString(): String {
                    return "Other(code=$code, body=$body)"
                }
            }
        }

        data class ServerError(val code: Int) : Error()

        data class ConnectivityError(val cause: Throwable) : Error()

        data class Other(
            val message: String?,
            val cause: Throwable?
        ) : Error()
    }
}