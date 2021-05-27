package com.retaillist.ui.feature.detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.retaillist.R
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ProductReview
import com.retaillist.ui.feature.ErrorScreen
import com.retaillist.ui.feature.LoadingScreen
import com.retaillist.ui.feature.detail.ProductDetailViewModel
import com.retaillist.ui.feature.detail.states.ProductDetailState

const val PRODUCT_DETAIL_SCREEN_ID = "product_detail_screen"
const val ARG_PRODUCT_ID = "product_id"

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    productId: ProductId,
    onBackClick: () -> Unit
) {
    val productDetailViewModelState by viewModel.productDetailViewModelState.collectAsState()
    val productReviewViewModelState by viewModel.productReviewViewModelState.collectAsState()
    val sendReviewViewModelState by viewModel.sendReviewViewModelState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.product_detail))
                },
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = {
                    // navigation icon is use
                    // for drawer icon.
                    IconButton(onClick = {
                        onBackClick.invoke()
                    }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(imageVector = Icons.Filled.ArrowBack, "back")
                    }
                },
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column {
            ProductDetailCompose(
                productDetailViewModelState = productDetailViewModelState,
                productReviewViewModelState = productReviewViewModelState,
                sendReviewViewModelState = sendReviewViewModelState,
                onReset = {
                    viewModel.loadProductDetail(productId)
                },
                onRetryReviews = {
                    viewModel.loadProductReviewWithScope(productId)
                },
                onReviewCreated = {
                    viewModel.sendReview(it)
                }
            )
        }
    }
    println("viewModel.loadProductDetail")
    viewModel.loadProductDetail(productId)
}

@Composable
fun ProductDetailCompose(
    productDetailViewModelState: ProductDetailViewModel.ProductDetailViewModelState,
    productReviewViewModelState: ProductDetailViewModel.ProductReviewViewModelState,
    sendReviewViewModelState: ProductDetailViewModel.SendReviewViewModelState,
    onReset: () -> Unit,
    onRetryReviews: () -> Unit,
    onReviewCreated: (ProductReview) -> Unit
) {
    when(productDetailViewModelState) {
        is ProductDetailViewModel.ProductDetailViewModelState.Detail -> {
            ProductDetailState(
                productDetailViewModelState = productDetailViewModelState,
                productReviewViewModelState = productReviewViewModelState,
                sendReviewViewModelState = sendReviewViewModelState,
                onRetryReviews = onRetryReviews,
                onReviewCreated = onReviewCreated
            )
        }
        ProductDetailViewModel.ProductDetailViewModelState.Error -> {
            ErrorScreen(
                onRetryClick = {
                    onReset.invoke()
                }
            )
        }
        ProductDetailViewModel.ProductDetailViewModelState.Loading -> {
            LoadingScreen()
        }
    }
}

