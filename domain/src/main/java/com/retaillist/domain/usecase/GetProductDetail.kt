package com.retaillist.domain.usecase

import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import javax.inject.Inject

interface GetProductDetail {
    suspend fun invoke(productId: ProductId): ResultRequired<Product>
}

class GetProductDetailImpl @Inject
constructor(
    private val repository: ProductsRepository
): GetProductDetail {
    override suspend fun invoke(productId: ProductId): ResultRequired<Product> =
        repository.getProductDetail(productId)
}