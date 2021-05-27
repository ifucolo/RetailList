package com.retaillist.ui.feature.detail.states

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.retaillist.R
import com.retaillist.domain.model.ProductId
import com.retaillist.domain.model.ProductReview
import com.retaillist.domain.model.Rating
import com.retaillist.domain.model.ReviewDescription
import com.retaillist.ui.components.ButtonSmall
import com.retaillist.ui.feature.ErrorScreenDialog
import com.retaillist.ui.feature.LoadingScreenDialog
import com.retaillist.ui.feature.detail.ProductDetailViewModel
import com.retaillist.ui.theme.margin16

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReviewDialog(
    sendReviewViewModelState: ProductDetailViewModel.SendReviewViewModelState,
    productId: ProductId,
    onCloseDismiss: () -> Unit,
    onReviewSending: (ProductReview) -> Unit
) {
    val hintReview: String = stringResource(id = R.string.add_review_hint)

    val rating = remember {  mutableStateOf(Rating(value = 1)) }
    val description = remember {  mutableStateOf(hintReview) }
    var productReview: ProductReview? = null

    Column() {
        when(sendReviewViewModelState) {
            ProductDetailViewModel.SendReviewViewModelState.Error -> {
                ErrorScreenDialog(
                    onCancel = onCloseDismiss,
                    onRetryClick = {
                        //check it
                        onReviewSending.invoke(productReview!!)
                    }
                )
            }
            ProductDetailViewModel.SendReviewViewModelState.Sending -> {
                LoadingScreenDialog()
            }
            ProductDetailViewModel.SendReviewViewModelState.Sent -> {
                onCloseDismiss.invoke()
            }
            ProductDetailViewModel.SendReviewViewModelState.Send -> {
                CreateReviewDialog(
                    productId = productId,
                    rating = rating,
                    description = description,
                    onCloseDismiss = onCloseDismiss,
                    onReviewCreated = {
                        productReview = it
                        onReviewSending.invoke(it)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateReviewDialog(
    productId: ProductId,
    rating: MutableState<Rating>,
    description: MutableState<String>,
    onCloseDismiss: () -> Unit,
    onReviewCreated: (ProductReview) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onCloseDismiss.invoke()
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_review),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Left
            )
        },
        text = {
            val keyboardController = LocalSoftwareKeyboardController.current
            Column(
                verticalArrangement = Arrangement.spacedBy(margin16)
            ) {
                TextField(
                    modifier = Modifier
                        .padding(top = margin16),
                    value = description.value,
                    onValueChange = {
                        description.value = it
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    })
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 4.dp, bottom = 4.dp),
                        text = stringResource(id = R.string.rating),
                        style = MaterialTheme.typography.h2,
                        textAlign = TextAlign.Left
                    )
                    StarClickable(position = 1, rating = rating.value) { rating.value = it }
                    StarClickable(position = 2, rating = rating.value) { rating.value = it }
                    StarClickable(position = 3, rating = rating.value) { rating.value = it }
                    StarClickable(position = 4, rating = rating.value) { rating.value = it }
                    StarClickable(position = 5, rating = rating.value) { rating.value = it }
                }
            }

        },
        confirmButton = {
            ButtonSmall(
                text = stringResource(id = R.string.send_review),
                backgroundColor = MaterialTheme.colors.onSurface.copy(),
                onClick = {
                    onReviewCreated.invoke(
                        ProductReview(
                            productId,
                            rating.value,
                            ReviewDescription(description.value)
                        )
                    )
                }
            )
        },
        dismissButton = {
            ButtonSmall(
                text = stringResource(id = R.string.cancel_review),
                backgroundColor = MaterialTheme.colors.onSurface.copy(),
                onClick = {
                    onCloseDismiss.invoke()
                }
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun StarClickable(
    position: Int,
    rating: Rating,
    onStarClick: (Rating) -> Unit
) {
    val imageStar = if (position <= rating.value )
        Icons.Filled.Star
    else
        Icons.Filled.StarBorder

    Icon(
        imageStar,
        "star",
        modifier = Modifier
            .width(25.dp)
            .height(25.dp)
            .padding(end = 4.dp)
            .clickable {
                onStarClick.invoke(Rating(position))
            },
        tint = Color.Yellow
    )
}