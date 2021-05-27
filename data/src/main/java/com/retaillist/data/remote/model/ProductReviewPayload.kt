package com.retaillist.data.remote.model

import com.google.gson.annotations.SerializedName

class ProductReviewPayload(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("text")
    val text: String
)