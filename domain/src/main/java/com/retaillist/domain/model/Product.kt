package com.retaillist.domain.model

import java.io.Serializable

data class Product(
    val id: ProductId,
    val currency: String,
    val price: Long,
    val name: String,
    val description: String,
    val imgUrl: String
) {
    fun getPriceWithCurrency(): String = "$currency$price"
}

data class ProductId(val value: String): Serializable
data class Rating(val value: Int)
data class ReviewDescription(val value: String)

data class ProductReview(
    val id: ProductId,
    val rating: Rating,
    val reviewDescription: ReviewDescription
)