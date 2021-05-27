package com.retaillist.domain

import com.nhaarman.mockito_kotlin.description
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import com.retaillist.domain.usecase.GetProductDetail
import com.retaillist.domain.usecase.GetProductDetailImpl
import com.retaillist.productIdStub
import com.retaillist.productStub
import kotlinx.coroutines.test.runBlockingTest
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class GetProductDetailTest: SubjectSpek<GetProductDetail>({

    class Env {
        val productsRepository: ProductsRepository = mock()
    }

    val env: Env by memoized { Env() }
    subject {
        with(env) {
            GetProductDetailImpl(
                repository = productsRepository
            )
        }
    }

    describe("GetProductDetail") {
        context("get product detail successfully") {
            beforeEachTest {
                runBlockingTest {
                    whenever(env.productsRepository.getProductDetail(productId = productIdStub)) doReturn ResultRequired.Success(productStub)
                }
            }
            it("should receive product ResultRequired.Success") {
                runBlockingTest {
                    val result = subject.invoke(productId = productIdStub)
                    assertEquals(ResultRequired.Success(productStub), result)
                }
            }
        }

        context("get product detail error") {
            beforeEachTest {
                runBlockingTest {
                    whenever(env.productsRepository.getProductDetail(productId = productIdStub)) doReturn ResultRequired.Error.ConnectivityError
                }
            }
            it("should receive product ResultRequired.Error") {
                runBlockingTest {
                    val result = subject.invoke(productId = productIdStub)
                    assertEquals(ResultRequired.Error.ConnectivityError, result)
                }
            }
        }
    }
})