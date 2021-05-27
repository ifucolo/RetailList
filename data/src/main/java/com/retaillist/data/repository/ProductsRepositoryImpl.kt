package com.retaillist.data.repository

import com.retaillist.data.remote.api.ProductsApi
import com.retaillist.data.remote.mapper.*
import com.retaillist.data.remote.model.RemoteResponse
import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi
): ProductsRepository {

    override suspend fun getProducts(): ResultRequired<List<Product>> {
        return try {
            val response = productsApi.getProducts().mapRawResponse()

            return when(response) {
                is RemoteResponse.Success -> {
                    val products = ProductMapper.map(response.body)

                    ResultRequired.Success(
                        result = products
                    )
                }
                is RemoteResponse.Error -> {
                    response.mapErrorToResultRequired()
                }
            }
        } catch (e: Throwable) {
            e.mapThrowable()
        }
    }

    override suspend fun getProductDetail(productId: ProductId): ResultRequired<Product> {
        return try {
            val response = productsApi.getProductById(productId.value).mapRawResponse()

            return when (response) {
                is RemoteResponse.Success -> {
                    val products = ProductMapper.map(response.body)

                    ResultRequired.Success(
                        result = products
                    )
                }
                is RemoteResponse.Error -> {
                    response.mapErrorToResultRequired()
                }
            }
        } catch (e: Throwable) {
            e.mapThrowable()
        }
    }

    override suspend fun updateProduct(product: Product): ResultRequired<Product> {
        return try {
            val payload = ProductMapper.mapToPayload(product = product)

            val response = productsApi.updateProduct(
                productId = product.id.value,
                body = payload
            ).mapRawResponse()

            return when(response) {
                is RemoteResponse.Success -> {
                    val products = ProductMapper.map(response.body)

                    ResultRequired.Success(
                        result = products
                    )
                }
                is RemoteResponse.Error -> {
                    response.mapErrorToResultRequired()
                }
            }
        } catch (e: Throwable) {
            e.mapThrowable()
        }
    }
}