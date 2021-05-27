package com.retaillist.domain.usecase

import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import javax.inject.Inject

interface GetProducts {
    suspend fun invoke() : ResultRequired<List<Product>>
}

class GetProductsImpl @Inject constructor(
    private val repository: ProductsRepository
): GetProducts {
    override suspend fun invoke(): ResultRequired<List<Product>> =
        repository.getProducts()
}