package com.retaillist.di

import com.retaillist.data.repository.ProductReviewRepositoryImpl
import com.retaillist.data.repository.ProductsRepositoryImpl
import com.retaillist.domain.repository.ProductReviewRepository
import com.retaillist.domain.repository.ProductsRepository
import com.retaillist.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ProductModule {

    @Binds
    fun providesProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    fun providesProductReviewRepository(impl: ProductReviewRepositoryImpl): ProductReviewRepository

    @Binds
    fun providesGetProductDetail(impl: GetProductDetailImpl): GetProductDetail

    @Binds
    fun providesGetProducts(impl: GetProductsImpl): GetProducts

    @Binds
    fun providesProductReviews(impl: ProductReviewImpl): ProductReviews
}