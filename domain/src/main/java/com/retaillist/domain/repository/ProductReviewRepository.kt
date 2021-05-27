package com.retaillist.domain.repository

import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ProductReview
import com.retaillist.domain.model.ResultRequired

interface ProductReviewRepository {
    suspend fun getProductReviews(id: ProductId): ResultRequired<List<ProductReview>>
    suspend fun sendProductReview(review: ProductReview): ResultRequired<Any>
}