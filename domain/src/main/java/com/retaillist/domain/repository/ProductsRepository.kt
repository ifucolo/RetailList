package com.retaillist.domain.repository

import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ResultRequired

interface ProductsRepository {
    suspend fun getProducts(): ResultRequired<List<Product>>
    suspend fun getProductDetail(productId: ProductId): ResultRequired<Product>
    suspend fun updateProduct(product: Product): ResultRequired<Product>
}