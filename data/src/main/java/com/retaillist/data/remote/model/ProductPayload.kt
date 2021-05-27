package com.retaillist.data.remote.model

import com.google.gson.annotations.SerializedName

class ProductPayload(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("price")
    val price: Long,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imgUrl")
    val imgUrl: String
)