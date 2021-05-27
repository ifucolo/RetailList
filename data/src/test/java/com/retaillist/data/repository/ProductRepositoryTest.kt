package com.retaillist.data.repository

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.retaillist.data.remote.api.ProductsApi
import com.retaillist.data.remote.model.RemoteResponse
import com.retaillist.data.util.*
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import retrofit2.Response
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class ProductRepositoryTest: SubjectSpek<ProductsRepository>({

    class Env {
        val productsApi: ProductsApi = mock()
    }

    val env: Env by memoized { Env() }
    subject {
        with(env) {
            ProductsRepositoryImpl(
                productsApi = productsApi
            )
        }
    }


    describe("ProductRepositoryTest") {
        describe("getProducts") {
            context("get products from api successfully") {
                beforeEachTest {
                    runBlockingTest {
                        whenever(env.productsApi.getProducts()) doReturn Response.success(productsPayloadStub)
                    }
                }
                it("should receive products Success") {
                    runBlockingTest {
                        val result = subject.getProducts()
                        assertEquals(ResultRequired.Success(productsStubRep), result)
                    }
                }
            }

            context("get products from api client error") {
                beforeEachTest {
                    runBlockingTest {
                        whenever(env.productsApi.getProducts()) doReturn Response.error(404, errorBodyPayload)
                    }
                }
                it("should receive products client error") {
                    runBlockingTest {
                        val result = subject.getProducts()
                        assertEquals(ResultRequired.Error.ClientError, result)
                    }
                }
            }
        }

        describe("getProductById") {
            context("get product by id from api successfully") {
                beforeEachTest {
                    runBlockingTest {
                        whenever(env.productsApi.getProductById(productIdPayloadStub)) doReturn Response.success(
                            productPayloadStub
                        )
                    }
                }
                it("should receive products Success") {
                    runBlockingTest {
                        val result = subject.getProductDetail(productIdStubRep)
                        assertEquals(ResultRequired.Success(productStubRep), result)
                    }
                }
            }

            context("get product by id from api client error") {
                beforeEachTest {
                    runBlockingTest {
                        whenever(env.productsApi.getProductById(productIdPayloadStub)) doReturn Response.error(404, errorBodyPayload)
                    }
                }
                it("should receive product by id client error") {
                    runBlockingTest {
                        val result = subject.getProductDetail(productIdStubRep)
                        assertEquals(ResultRequired.Error.ClientError, result)
                    }
                }
            }
        }
    }
})