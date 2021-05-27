package com.retaillist.ui.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retaillist.R
import com.retaillist.domain.model.ProductReview
import com.retaillist.domain.model.ReviewDescription
import com.retaillist.ui.components.ButtonMedium
import com.retaillist.ui.components.ButtonSmall
import com.retaillist.ui.theme.margin16


@Composable
fun ErrorScreen(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(margin16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Error,
            "error",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(bottom = margin16)
        )
        Text(
            modifier = Modifier
                .padding(start = margin16, bottom = margin16),
            text = stringResource(R.string.error_message),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        ButtonMedium(
            text = stringResource(R.string.error_retry),
            backgroundColor = MaterialTheme.colors.onSurface,
            onClick = {
                onRetryClick.invoke()
            }
        )
    }
}

@Composable
fun ErrorScreenItem(
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Error,
            "error",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(bottom = margin16)
        )
        Text(
            modifier = Modifier
                .padding(start = margin16, bottom = margin16),
            text = message,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        ButtonMedium(
            text = stringResource(R.string.error_retry),
            backgroundColor = MaterialTheme.colors.onSurface,
            onClick = {
                onRetryClick.invoke()
            }
        )
    }
}

@Composable
fun ErrorScreenDialog(
    onRetryClick: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {

        },
        text = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.error_message),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )
        },
        confirmButton = {
            ButtonSmall(
                text = stringResource(id = R.string.error_retry),
                backgroundColor = MaterialTheme.colors.onSurface.copy(),
                onClick = onRetryClick
            )
        },
        dismissButton = {
            ButtonSmall(
                text = stringResource(id = R.string.cancel_review),
                backgroundColor = MaterialTheme.colors.onSurface.copy(),
                onClick = onCancel
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(onRetryClick = {})
}
