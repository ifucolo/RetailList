package com.retaillist.domain

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductReviewRepository
import com.retaillist.domain.repository.ProductsRepository
import com.retaillist.domain.usecase.GetProducts
import com.retaillist.domain.usecase.GetProductsImpl
import com.retaillist.domain.usecase.ProductReviewImpl
import com.retaillist.domain.usecase.ProductReviews
import com.retaillist.productIdStub
import com.retaillist.productStub
import com.retaillist.productsStub
import com.retaillist.reviewsStub
import kotlinx.coroutines.test.runBlockingTest
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class ProductReviewTest: SubjectSpek<ProductReviews>({

    class Env {
        val productReviewRepository: ProductReviewRepository = mock()
    }

    val env: Env by memoized { Env() }
    subject {
        with(env) {
            ProductReviewImpl(
                productReviewRepository = productReviewRepository
            )
        }
    }

    describe("ProductReviewTest") {
        context("get reviews from product successfully") {
            beforeEachTest {
                runBlockingTest {
                    whenever(env.productReviewRepository.getProductReviews(productIdStub)) doReturn ResultRequired.Success(reviewsStub)
                }
            }
            it("should receive reviews from a product: ResultRequired.Success") {
                runBlockingTest {
                    val result = subject.getReviews(productIdStub)
                    assertEquals(ResultRequired.Success(reviewsStub), result)
                }
            }
        }

        context("get reviews from product error") {
            beforeEachTest {
                runBlockingTest {
                    whenever(env.productReviewRepository.getProductReviews(productIdStub)) doReturn ResultRequired.Error.ConnectivityError
                }
            }
            it("should receive reviews error: ResultRequired.Error") {
                runBlockingTest {
                    val result = subject.getReviews(productIdStub)
                    assertEquals(ResultRequired.Error.ConnectivityError, result)
                }
            }
        }
    }
})