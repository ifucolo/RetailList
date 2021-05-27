package com.retaillist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.retaillist.domain.model.ProductId
import com.retaillist.ui.feature.detail.ProductDetailViewModel
import com.retaillist.ui.feature.detail.ui.ARG_PRODUCT_ID
import com.retaillist.ui.feature.detail.ui.PRODUCT_DETAIL_SCREEN_ID
import com.retaillist.ui.feature.detail.ui.ProductDetailScreen
import com.retaillist.ui.feature.products.ProductListViewModel
import com.retaillist.ui.feature.products.ui.PRODUCT_LIST_SCREEN_ID
import com.retaillist.ui.feature.products.ui.ProductListScreen
import com.retaillist.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()

        NavHost(navController,
            startDestination = PRODUCT_LIST_SCREEN_ID
        ) {
            composable(PRODUCT_LIST_SCREEN_ID) {
                ProductListScreen(
                    viewModel = hiltViewModel<ProductListViewModel>(),
                    onProductClick = { product ->
                        navController.currentBackStackEntry?.arguments =
                            Bundle().apply {
                                putSerializable(ARG_PRODUCT_ID, product.id)
                            }
                        navController.navigate(PRODUCT_DETAIL_SCREEN_ID)
                    }
                )
            }

            composable(PRODUCT_DETAIL_SCREEN_ID) {
                val productId = navController.previousBackStackEntry
                    ?.arguments?.getSerializable(ARG_PRODUCT_ID) as ProductId

                ProductDetailScreen(
                    viewModel = hiltViewModel<ProductDetailViewModel>(),
                    productId = productId,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}