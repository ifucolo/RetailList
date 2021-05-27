package com.retaillist.ui.feature.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retaillist.domain.model.*
import com.retaillist.domain.usecase.GetProducts
import com.retaillist.domain.usecase.ProductReviews
import com.retaillist.ui.feature.detail.ProductDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor (
    private val getProducts: GetProducts,
    private val productReviews : ProductReviews,
    val handle: SavedStateHandle
): ViewModel() {

    private val _productListViewModelState: MutableStateFlow<ProductListViewModelState> = MutableStateFlow(
        ProductListViewModelState.Loading
    )
    val productListViewModelState: StateFlow<ProductListViewModelState> = _productListViewModelState

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            _productListViewModelState.value = ProductListViewModelState.Loading

            val result = getProducts.invoke()

            _productListViewModelState.value = when(result) {
                is ResultRequired.Success -> {
                    if (result.result.isEmpty()) {
                        ProductListViewModelState.Empty.Fresh
                    } else {
                        ProductListViewModelState.ListProducts.FreshResult(
                            list = result.result
                        )
                    }
                }
                is ResultRequired.Error -> {
                    ProductListViewModelState.Error
                }
            }

            println("ProductListViewModel $result")
        }
    }

    fun searchProductsByQuery(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _productListViewModelState.value = ProductListViewModelState.Loading
            val result = getProducts.invoke()

            _productListViewModelState.value = when(result) {
                is ResultRequired.Success -> {
                    val filteredList = result.result.filter { product ->
                        product.name.contains(query)||
                                product.description.contains(query)
                    }
                    if (filteredList.isEmpty()) {
                        ProductListViewModelState.Empty.Search(query = query)
                    } else {
                        ProductListViewModelState.ListProducts.SearchResult(
                            list = filteredList,
                            query = query
                        )
                    }
                }
                is ResultRequired.Error -> {
                    ProductListViewModelState.Error
                }
            }
        }
    }

    sealed class ProductListViewModelState {
        sealed class ListProducts(open val list: List<Product>): ProductListViewModelState() {
            data class FreshResult(override val list: List<Product>): ListProducts(list)
            data class SearchResult(override val list: List<Product>, val query: String): ListProducts(list)
        }

        sealed class Empty: ProductListViewModelState() {
            object Fresh: Empty()
            data class Search(val query: String): Empty()
        }

        object Error: ProductListViewModelState()
        object Loading: ProductListViewModelState()
    }
}