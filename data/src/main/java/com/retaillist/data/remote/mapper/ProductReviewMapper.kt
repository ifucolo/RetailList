package com.retaillist.data.remote.mapper

import com.retaillist.data.remote.model.ProductPayload
import com.retaillist.data.remote.model.ProductReviewPayload
import com.retaillist.domain.model.*

object ProductReviewMapper {
    fun map(payload: List<ProductReviewPayload>) = payload.map { map(it) }

    fun map(payload: ProductReviewPayload) = ProductReview(
        id = ProductId(value = payload.productId),
        rating = Rating(payload.rating),
        reviewDescription = ReviewDescription(payload.text)
    )

    fun mapToPayload(review: ProductReview) = ProductReviewPayload(
        productId = review.id.value,
        rating = review.rating.value,
        text = review.reviewDescription.value
    )
}