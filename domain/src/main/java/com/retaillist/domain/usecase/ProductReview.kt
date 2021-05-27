package com.retaillist.domain.usecase

import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ProductReview
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductReviewRepository
import javax.inject.Inject

interface ProductReviews {
    suspend fun sendReview(review: ProductReview): ResultRequired<Any>
    suspend fun getReviews(id: ProductId): ResultRequired<List<ProductReview>>
}

class ProductReviewImpl @Inject constructor(
    private val productReviewRepository: ProductReviewRepository
): ProductReviews {

    override suspend fun sendReview(review: ProductReview): ResultRequired<Any> {
        return productReviewRepository.sendProductReview(review)
    }

    override suspend fun getReviews(id: ProductId): ResultRequired<List<ProductReview>> =
        productReviewRepository.getProductReviews(id)
}