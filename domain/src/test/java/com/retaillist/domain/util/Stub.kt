package com.retaillist

import com.retaillist.domain.model.*


val productIdStub: ProductId =
    ProductId("HC123")

val productIdStub2: ProductId =
    ProductId("HC12223")
val ratingStub = Rating(1)
val reviewDescriptionStub = ReviewDescription("review")

val reviewsStub = listOf<ProductReview>(
    ProductReview(
        id = productIdStub,
        rating = ratingStub,
        reviewDescription = reviewDescriptionStub
    )
)

val productStub = Product(
    id = productIdStub,
    currency = "$",
    price = 50L,
    name = "Shoes",
    description = "Perfect SHoes",
    imgUrl = "url"
)

val productStub2 = Product(
    id = productIdStub2,
    currency = "$",
    price = 50L,
    name = "Shoes",
    description = "Perfect SHoes",
    imgUrl = "url"
)

val productsStub = listOf(productStub, productStub2)
