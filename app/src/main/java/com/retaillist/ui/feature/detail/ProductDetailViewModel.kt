package com.retaillist.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retaillist.domain.model.*
import com.retaillist.domain.usecase.GetProductDetail
import com.retaillist.domain.usecase.ProductReviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetail: GetProductDetail,
    private val productReviews : ProductReviews
): ViewModel() {

    private val _productDetailViewModelState: MutableStateFlow<ProductDetailViewModelState> = MutableStateFlow(
        ProductDetailViewModelState.Loading
    )
    val productDetailViewModelState: StateFlow<ProductDetailViewModelState> = _productDetailViewModelState

    private val _productReviewViewModelState: MutableStateFlow<ProductReviewViewModelState> = MutableStateFlow(
        ProductReviewViewModelState.Loading
    )
    val productReviewViewModelState: StateFlow<ProductReviewViewModelState> = _productReviewViewModelState

    private val _sendReviewViewModelState: MutableStateFlow<SendReviewViewModelState> = MutableStateFlow(
        SendReviewViewModelState.Send
    )
    val sendReviewViewModelState: StateFlow<SendReviewViewModelState> = _sendReviewViewModelState


    fun loadProductDetail(productId: ProductId) {
        viewModelScope.launch(Dispatchers.IO) {
            _productDetailViewModelState.value = ProductDetailViewModelState.Loading
            val productDetail = getProductDetail.invoke(productId)

            when(productDetail) {
                is ResultRequired.Success -> {
                    _productDetailViewModelState.value = ProductDetailViewModelState.Detail(productDetail.result)

                    loadProductReview(productId)
                }
                is ResultRequired.Error -> {
                    _productDetailViewModelState.value = ProductDetailViewModelState.Error
                    _productReviewViewModelState.value = ProductReviewViewModelState.NotShow
                }
            }
        }
    }

    fun loadProductReviewWithScope(productId: ProductId) {
        viewModelScope.launch(Dispatchers.IO) {
            loadProductReview(productId)
        }
    }

    suspend fun loadProductReview(productId: ProductId) {
        _productReviewViewModelState.value = ProductReviewViewModelState.Loading

        val reviews = productReviews.getReviews(productId)
        when(reviews) {
            is ResultRequired.Success -> {
                _productReviewViewModelState.value = ProductReviewViewModelState.Reviews(reviews.result.reversed())
            }
            is ResultRequired.Error -> {
                _productReviewViewModelState.value = ProductReviewViewModelState.Error
            }
        }
    }

    fun sendReview(
        productReview: ProductReview
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _sendReviewViewModelState.value = SendReviewViewModelState.Sending

            val review = productReviews.sendReview(productReview)
            when(review) {
                is ResultRequired.Success -> {
                    _sendReviewViewModelState.value = SendReviewViewModelState.Sent
                    loadProductReview(productReview.id)
                    delay(1000)
                    _sendReviewViewModelState.value = SendReviewViewModelState.Send
                }
                is ResultRequired.Error -> {
                    _sendReviewViewModelState.value = SendReviewViewModelState.Error
                }
            }
        }
    }

    sealed class ProductDetailViewModelState {
        data class Detail(
            val product: Product
        ): ProductDetailViewModelState()

        object Loading:  ProductDetailViewModelState()
        object Error: ProductDetailViewModelState()
    }

    sealed class ProductReviewViewModelState {
        data class Reviews(
            val reviews: List<ProductReview>
        ): ProductReviewViewModelState()

        object Loading:  ProductReviewViewModelState()
        object Error: ProductReviewViewModelState()
        object NotShow: ProductReviewViewModelState()
    }

    sealed class SendReviewViewModelState {
        object Send: SendReviewViewModelState()
        object Sending: SendReviewViewModelState()
        object Sent: SendReviewViewModelState()
        object Error: SendReviewViewModelState()
    }
}