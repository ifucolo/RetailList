package com.retaillist.ui.feature.detail.states

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.retaillist.ui.theme.margin16
import com.retaillist.ui.theme.margin8
import com.retaillist.R
import com.retaillist.domain.model.*
import com.retaillist.ui.components.ButtonMedium
import com.retaillist.ui.feature.ErrorScreenItem
import com.retaillist.ui.feature.LoadingScreenItem
import com.retaillist.ui.feature.detail.ProductDetailViewModel
import com.retaillist.ui.theme.buttonBottomMargin

@Composable
fun ProductDetailState(
    productDetailViewModelState: ProductDetailViewModel.ProductDetailViewModelState.Detail,
    productReviewViewModelState: ProductDetailViewModel.ProductReviewViewModelState,
    sendReviewViewModelState: ProductDetailViewModel.SendReviewViewModelState,
    onReviewCreated: (ProductReview) -> Unit,
    onRetryReviews: () -> Unit
) {
    val product = productDetailViewModelState.product
    val itemsModifier = Modifier
        .padding(start = margin16, end = margin16)

    val openReviewDialog = remember { mutableStateOf(false)}
    val productId: ProductId = productDetailViewModelState.product.id

    Box() {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(margin16),
            modifier = Modifier.padding(bottom = buttonBottomMargin).fillMaxWidth()
        ) {
            item {
                ImageHero(
                    imgUrl = product.imgUrl
                )
            }

            item {
                Text(
                    modifier = itemsModifier,
                    text = product.name.capitalize(),
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Left
                )
            }
            item {
                TitleDescriptionLabel(
                    title = stringResource(id = R.string.product_price),
                    description = product.getPriceWithCurrency(),
                    modifier = itemsModifier
                )
            }
            item {
                TitleDescriptionLabel(
                    title = stringResource(id = R.string.product_description),
                    description = product.description.capitalize(),
                    modifier = itemsModifier
                )
            }
            when (productReviewViewModelState) {
                ProductDetailViewModel.ProductReviewViewModelState.Error -> {
                    item {
                        ErrorScreenItem(
                            message = stringResource(id = R.string.error_load_review),
                            onRetryClick = {
                                onRetryReviews.invoke()
                            }
                        )
                    }
                }
                ProductDetailViewModel.ProductReviewViewModelState.Loading -> {
                    item {
                        LoadingScreenItem()
                    }
                }
                ProductDetailViewModel.ProductReviewViewModelState.NotShow -> {

                }
                is ProductDetailViewModel.ProductReviewViewModelState.Reviews -> {
                    item {
                        Text(
                            modifier = itemsModifier,
                            text = stringResource(id = R.string.reviews),
                            style = MaterialTheme.typography.h1,
                            textAlign = TextAlign.Left
                        )
                    }
                    productReviewViewModelState.reviews.forEach {
                        item {
                            ReviewComponent(
                                review = it,
                                modifier = itemsModifier
                            )
                        }
                    }
                }
            }
        }

        if (openReviewDialog.value) {
            ReviewDialog(
                sendReviewViewModelState = sendReviewViewModelState,
                productId = productId,
                onCloseDismiss = {
                    openReviewDialog.value = false
                },
                onReviewSending = {
                    onReviewCreated.invoke(it)
                }
            )
        }
        if (productReviewViewModelState is ProductDetailViewModel.ProductReviewViewModelState.Reviews) {
            ButtonMedium(
                text = stringResource(id = R.string.add_review),
                backgroundColor = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                openReviewDialog.value = true
            }
        }
    }
}

@Composable
private fun ImageHero(
    imgUrl: String,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        Image(
            painter = rememberCoilPainter(imgUrl, fadeIn = true),
            contentDescription = imgUrl,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun TitleDescriptionLabel(
    title: String,
    description: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement =  Arrangement.spacedBy(margin8)
    ) {
        Text(
            text = title.capitalize(),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Left
        )
        AnimatedVisibility(
            visible = description.isNotEmpty()
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Left
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ReviewComponent(
    review: ProductReview,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 4.dp, bottom = 4.dp),
                text = stringResource(id = R.string.rating),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Left
            )
            Star(position = 1, rating = review.rating)
            Star(position = 2, rating = review.rating)
            Star(position = 3, rating = review.rating)
            Star(position = 4, rating = review.rating)
            Star(position = 5, rating = review.rating)
        }
        AnimatedVisibility(
            visible = review.reviewDescription.value.isNotEmpty()
        ) {
            Text(
                text = review.reviewDescription.value.capitalize(),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Left
            )
        }
    }
}
@Composable
fun Star(
    position: Int,
    rating: Rating
) {
    val imageStar = if (position <= rating.value )
        Icons.Filled.Star
    else
        Icons.Filled.StarBorder

    Icon(
        imageStar,
        "star",
        modifier = Modifier
            .width(16.dp)
            .height(16.dp)
            .padding(end = 2.dp),
        tint = Color.Yellow
    )
}

@Preview
@Composable
fun RatingComposePreview() {
    val review = ProductReview(ProductId("HI333"), Rating(3), ReviewDescription("Amazing product the best"))
    ReviewComponent(
        review,
        Modifier.padding(start = margin16, end = margin16)
    )
}

