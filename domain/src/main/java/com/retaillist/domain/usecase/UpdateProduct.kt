package com.retaillist.domain.usecase

import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import javax.inject.Inject

interface UpdateProduct {
    suspend fun invoke(product: Product): ResultRequired<Product>
}

class UpdateProductImpl @Inject
constructor(
    private val repository: ProductsRepository
): UpdateProduct {
    override suspend fun invoke(product: Product): ResultRequired<Product> =
        repository.updateProduct(product = product)

}