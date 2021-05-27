package com.retaillist.data.remote.api

import com.retaillist.data.remote.model.ProductPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductsApi {

    @GET("/product")
    suspend fun getProducts(): Response<List<ProductPayload>>

    @GET("/product/{productId}")
    suspend fun getProductById(@Path("productId") productId: String): Response<ProductPayload>
}