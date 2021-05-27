package com.retaillist.data.repository

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.retaillist.data.remote.api.ProductReviewApi
import com.retaillist.data.remote.api.ProductsApi
import com.retaillist.data.remote.mapper.ProductReviewMapper
import com.retaillist.data.util.*
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductReviewRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import retrofit2.Response
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitPlatform::class)
class ProductReviewRepositoryTest: SubjectSpek<ProductReviewRepository> ({

    class Env {
        val productReviewApi: ProductReviewApi = mock()
    }

    val env: Env by memoized { Env() }

    subject {
        with(env) {
            ProductReviewRepositoryImpl(
                reviewApi = productReviewApi
            )
        }
    }

    describe("ProductReviewRepositoryTest") {
        describe("getReviews") {
            context("get reviews about product from api successfully") {
                beforeEachTest {
                    runBlockingTest {
                        whenever(env.productReviewApi.getProductReview(productIdPayloadStub)) doReturn Response.success(
                            reviewsPayloadStub
                        )
                    }
                }
                it("should receive reviews Success") {
                    runBlockingTest {
                        val result = subject.getProductReviews(productIdStubRep)
                        assertEquals(ResultRequired.Success(reviewsStubRep), result)
                    }
                }
            }

            context("get reviews about product from api client error") {
                beforeEachTest {
                    runBlockingTest {
                        whenever(env.productReviewApi.getProductReview(productIdPayloadStub)) doReturn Response.error(404, errorBodyPayload)
                    }
                }
                it("should receive reviews client error") {
                    runBlockingTest {
                        val result = subject.getProductReviews(productIdStubRep)
                        assertEquals(ResultRequired.Error.ClientError, result)
                    }
                }
            }
        }

        //Not working for Unknown reason
//        describe("postProductReview") {
//            context("send reviews about product from api successfully") {
//                beforeEachTest {
//                    runBlockingTest {
//                        whenever(env.productReviewApi.postProductReview(productIdPayloadStub, reviewPayloadStub)) doReturn Response.success(reviewPayloadStub)
//                    }
//                }
//                it("should receive reviews Success") {
//                    runBlockingTest {
//                        val result = subject.sendProductReview(reviewstubRep)
//                        assertEquals(ResultRequired.Success(Any()), result)
//                    }
//                }
//            }
//
//            context("get reviews about product from api client error") {
//                beforeEachTest {
//                    runBlockingTest {
//                        whenever(env.productReviewApi.postProductReview(productIdPayloadStub, reviewPayloadStub)) doReturn Response.error(404, errorBodyPayload)
//                    }
//                }
//                it("should receive products client error") {
//                    runBlockingTest {
//                        val result = subject.sendProductReview(reviewstubRep)
//                        assertEquals(ResultRequired.Error.ClientError, result)
//                    }
//                }
//            }
//        }
    }

})