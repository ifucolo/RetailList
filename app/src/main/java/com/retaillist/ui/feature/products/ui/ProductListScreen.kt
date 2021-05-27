package com.retaillist.ui.feature.products.ui

import android.os.Bundle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.retaillist.domain.model.Product
import com.retaillist.domain.model.ProductId
import com.retaillist.ui.feature.EmptyScreen
import com.retaillist.ui.feature.ErrorScreen
import com.retaillist.ui.feature.LoadingScreen
import com.retaillist.ui.feature.detail.ui.ARG_PRODUCT_ID
import com.retaillist.ui.feature.detail.ui.PRODUCT_DETAIL_SCREEN_ID
import com.retaillist.ui.feature.products.states.ProductListState
import com.retaillist.ui.feature.products.ProductListViewModel

const val PRODUCT_LIST_SCREEN_ID = "product_list_screen"

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onProductClick: (Product) -> Unit
) {
    val viewState by viewModel.productListViewModelState.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background
    ) {
        when(viewState) {
            is ProductListViewModel.ProductListViewModelState.Empty -> {
                EmptyScreen(
                    state = viewState,
                    onResetSearch = {
                        viewModel.init()
                    })
            }
            ProductListViewModel.ProductListViewModelState.Error -> ErrorScreen {
                viewModel.init()
            }
            is ProductListViewModel.ProductListViewModelState.ListProducts -> {
                ProductListState(
                    state = viewState as ProductListViewModel.ProductListViewModelState.ListProducts,
                    onSearchQuery = { query ->
                        viewModel.searchProductsByQuery(query)
                    },
                    onProductClick = { product ->
                        onProductClick.invoke(product)
                    },
                    onResetResearch = {
                        viewModel.init()
                    }
                )
            }
            ProductListViewModel.ProductListViewModelState.Loading -> LoadingScreen()
        }
    }
    if (viewState !is ProductListViewModel.ProductListViewModelState.ListProducts) {
        viewModel.init()
    }
}


//@Preview
//@Composable
//fun ProductListScreenLoadingPreview() {
//    ProductListScreen(ProductListViewModel.ProductListViewModelState.Loading, {}, {}, {})
//}
//
//@Preview
//@Composable
//fun ProductListScreenErrorPreview() {
//    ProductListScreen(ProductListViewModel.ProductListViewModelState.Error, {}, {}, {})
//}
//
//@Preview
//@Composable
//fun ProductListScreenEmptyPreview() {
//    ProductListScreen(ProductListViewModel.ProductListViewModelState.Empty.Fresh, {}, {}, {})
//}
//
//@Preview
//@Composable
//fun ProductListScreenListFreshResultPreview() {
//    ProductListScreen(
//        ProductListViewModel.ProductListViewModelState.ListProducts.FreshResult(
//        list = listOf(Product(ProductId(""), "$", 55, "shoes", "best shoes", ""))
//    ), {}, {}, {})
//}
//
//@Preview
//@Composable
//fun ProductListScreenListSearchResultPreview() {
//    ProductListScreen(
//        ProductListViewModel.ProductListViewModelState.ListProducts.SearchResult(
//        list = listOf(Product(ProductId(""), "$", 55, "shoes", "best shoes", "")),
//        query = "shoes"
//    ), {}, {}, {})
//}