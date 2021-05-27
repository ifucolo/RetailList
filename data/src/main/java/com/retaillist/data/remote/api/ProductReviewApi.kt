package com.retaillist.data.remote.api

import com.retaillist.data.remote.model.ProductReviewPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductReviewApi {

    @GET("/reviews/{productId}")
    suspend fun getProductReview(@Path("productId") productId: String): Response<List<ProductReviewPayload>>

    @POST("/reviews/{productId}")
    suspend fun postProductReview(@Path("productId") productId: String, @Body body: ProductReviewPayload): Response<ProductReviewPayload>
}