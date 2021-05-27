package com.retaillist.data.remote.mapper

import com.retaillist.data.remote.model.ProductPayload
import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ProductId

object ProductMapper {
    fun map(payload: List<ProductPayload>) = payload.map { map(it) }

    fun map(payload: ProductPayload) = Product(
        id = ProductId(value = payload.id),
        currency = payload.currency,
        price = payload.price,
        name = payload.name,
        description = payload.description,
        imgUrl = payload.imgUrl
    )

    fun mapToPayload(product: Product) = ProductPayload(
        id = product.id.value,
        currency = product.currency,
        price = product.price,
        name = product.name,
        description = product.description,
        imgUrl = product.imgUrl
    )
}