package com.retaillist.viewmodel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.usecase.GetProductDetail
import com.retaillist.domain.usecase.ProductReviews
import com.retaillist.productIdStub
import com.retaillist.productStub
import com.retaillist.ui.feature.detail.ProductDetailViewModel
import com.retaillist.util.instantTaskExecutorRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith


//Not working because of an issue with launch scope
//@OptIn(ExperimentalCoroutinesApi::class)
//@RunWith(JUnitPlatform::class)
//object ProductDetailViewModelTest : SubjectSpek<ProductDetailViewModel>({
//    instantTaskExecutorRule()
//
//    class Env {
//        val getProductDetail: GetProductDetail = mock()
//        val productReviews: ProductReviews = mock()
//    }
//
//    val env: Env by memoized { Env() }
//
//    subject {
//        with(env) {
//            ProductDetailViewModel(
//                getProductDetail = getProductDetail,
//                productReviews = productReviews
//            )
//        }
//    }
//
//    describe("ProductDetailViewModelTest") {
//        describe("get product detail") {
//            context("should emit ProductDetailViewModelState.Detail") {
//                beforeEachTest {
//                    runBlockingTest {
//                        whenever(env.getProductDetail.invoke(productIdStub)) doReturn ResultRequired.Success(productStub)
//                    }
//                }
//                it("Success") {
//                    runBlockingTest {
//                        val emissions = mutableListOf<ProductDetailViewModel.ProductDetailViewModelState>()
//                        val job = launch {
//                            subject.productDetailViewModelState.toList(emissions)
//                        }
//
//                        subject.loadProductDetail(productIdStub)
//                        assertEquals(emissions[0], ProductDetailViewModel.ProductDetailViewModelState.Loading)
//
//                        emissions[1].let {
//                            assertTrue(
//                                it is ProductDetailViewModel.ProductDetailViewModelState.Detail && productStub == it.product
//                            )
//                        }
//
//                        job.cancel()
//                    }
//                }
//            }
//            context("should emit ProductDetailViewModelState.Error.ConnectivityError") {
//                beforeEachTest {
//                    runBlockingTest {
//                        whenever(env.getProductDetail.invoke(productIdStub)) doReturn ResultRequired.Error.ConnectivityError
//                    }
//                }
//                it("Error") {
//                    runBlockingTest {
//                        val job = launch {
//                            val emissions = mutableListOf<ProductDetailViewModel.ProductDetailViewModelState>()
//                            subject.productDetailViewModelState.toList(emissions)
//
//
//                            subject.loadProductDetail(productIdStub)
//
//                            assertEquals(emissions[0], ProductDetailViewModel.ProductDetailViewModelState.Loading)
//                            delay(3_000)
//
//                            emissions[1].let {
//                                assertTrue(
//                                    it is ProductDetailViewModel.ProductDetailViewModelState.Loading
//                                )
//                            }
//                        }
//                        job.cancel()
//                    }
//                }
//            }
//        }
//}
//})
//
//
