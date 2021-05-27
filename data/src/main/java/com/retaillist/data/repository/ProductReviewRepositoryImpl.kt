package com.retaillist.data.repository

import com.retaillist.data.remote.api.ProductReviewApi
import com.retaillist.data.remote.mapper.ProductReviewMapper
import com.retaillist.data.remote.mapper.mapErrorToResultRequired
import com.retaillist.data.remote.mapper.mapRawResponse
import com.retaillist.data.remote.mapper.mapThrowable
import com.retaillist.data.remote.model.RemoteResponse
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ProductReview
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductReviewRepository
import javax.inject.Inject

class ProductReviewRepositoryImpl @Inject constructor(
    private val reviewApi: ProductReviewApi
): ProductReviewRepository {
    override suspend fun getProductReviews(id: ProductId): ResultRequired<List<ProductReview>> {
        return try {
            val response = reviewApi.getProductReview(id.value).mapRawResponse()

            return when(response) {
                is RemoteResponse.Success -> {
                    val reviews = ProductReviewMapper.map(response.body)

                    ResultRequired.Success(
                        result = reviews
                    )
                }
                is RemoteResponse.Error -> {
                    response.mapErrorToResultRequired()
                }
            }

        } catch (e: Throwable) {
            e.mapThrowable()
        }
    }

    override suspend fun sendProductReview(review: ProductReview): ResultRequired<Any> {
        return try {
            val payload = ProductReviewMapper.mapToPayload(review = review)

            val response = reviewApi.postProductReview(
                productId = review.id.value,
                body = payload
            ).mapRawResponse()

            return when(response) {
                is RemoteResponse.Success -> {
                    ResultRequired.Success(
                        result = Any()
                    )
                }
                is RemoteResponse.Error -> {
                    response.mapErrorToResultRequired()
                }
            }
        } catch (e: Throwable) {
            e.mapThrowable()
        }
    }
}