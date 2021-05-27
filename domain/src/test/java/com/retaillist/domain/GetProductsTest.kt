package com.retaillist.domain

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.retaillist.domain.model.ResultRequired
import com.retaillist.domain.repository.ProductsRepository
import com.retaillist.domain.usecase.GetProducts
import com.retaillist.domain.usecase.GetProductsImpl
import com.retaillist.productStub
import com.retaillist.productsStub
import kotlinx.coroutines.test.runBlockingTest
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class GetProductsTest: SubjectSpek<GetProducts>({

    class Env {
        val productsRepository: ProductsRepository = mock()
    }

    val env: Env by memoized { Env() }
    subject {
        with(env) {
            GetProductsImpl(
                repository = productsRepository
            )
        }
    }

    describe("GetProductsTest") {
        context("get products successfully") {
            beforeEachTest {
                runBlockingTest {
                    whenever(env.productsRepository.getProducts()) doReturn ResultRequired.Success(productsStub)
                }
            }
            it("should receive products: ResultRequired.Success") {
                runBlockingTest {
                    val result = subject.invoke()
                    assertEquals(ResultRequired.Success(productsStub), result)
                }
            }
        }

        context("get products error") {
            beforeEachTest {
                runBlockingTest {
                    whenever(env.productsRepository.getProducts()) doReturn ResultRequired.Error.ConnectivityError
                }
            }
            it("should receive products: ResultRequired.Error") {
                runBlockingTest {
                    val result = subject.invoke()
                    assertEquals(ResultRequired.Error.ConnectivityError, result)
                }
            }
        }
    }
})