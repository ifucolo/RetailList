package com.retaillist.data.util

import com.retaillist.data.remote.model.ProductPayload
import com.retaillist.data.remote.model.ProductReviewPayload
import com.retaillist.domain.model.*
import okhttp3.MediaType
import okhttp3.ResponseBody

val productIdPayloadStub: String = "HC123"
val productIdPayloadStub2: String = "HC12223"

val ratingPayloadStub = 1
val reviewDescriptionPayloadStub = "review"

val ratingStubRep = Rating(1)
val reviewDescriptionStubRep = ReviewDescription("review")

val reviewPayloadStub = ProductReviewPayload(
    productId = productIdPayloadStub,
    rating = ratingPayloadStub,
    text = reviewDescriptionPayloadStub
)
val reviewPayloadStub2 = ProductReviewPayload(
    productId = productIdPayloadStub,
    rating = ratingPayloadStub,
    text = reviewDescriptionPayloadStub
)

val reviewsPayloadStub = listOf<ProductReviewPayload>(
    reviewPayloadStub
)

val productPayloadStub = ProductPayload(
    id = productIdPayloadStub,
    currency = "$",
    price = 50L,
    name = "Shoes",
    description = "Perfect SHoes",
    imgUrl = "url"
)

val productPayloadStub2 = ProductPayload(
    id = productIdPayloadStub2,
    currency = "$",
    price = 50L,
    name = "Shoes",
    description = "Perfect SHoes",
    imgUrl = "url"
)



val productIdStubRep: ProductId =
    ProductId(productIdPayloadStub)

val productIdStub2Rep: ProductId =
    ProductId(productIdPayloadStub2)

val productStubRep = Product(
    id = productIdStubRep,
    currency = "$",
    price = 50L,
    name = "Shoes",
    description = "Perfect SHoes",
    imgUrl = "url"
)

val productStub2Rep = Product(
    id = productIdStub2Rep,
    currency = "$",
    price = 50L,
    name = "Shoes",
    description = "Perfect SHoes",
    imgUrl = "url"
)
val productsPayloadStub = listOf(productPayloadStub, productPayloadStub2)
val productsStubRep = listOf(productStubRep, productStub2Rep)

val errorBodyPayload =  ResponseBody.create(MediaType.parse("application/json"),"")

val reviewstubRep = ProductReview(
    id = productIdStubRep,
    rating = ratingStubRep,
    reviewDescription = reviewDescriptionStubRep
)

val reviewsStubRep = listOf<ProductReview>(
    ProductReview(
        id = productIdStubRep,
        rating = ratingStubRep,
        reviewDescription = reviewDescriptionStubRep
    )
)

