package com.retaillist.ui.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retaillist.R
import com.retaillist.ui.components.ButtonMedium
import com.retaillist.ui.feature.products.ProductListViewModel
import com.retaillist.ui.theme.margin16

@Composable
fun EmptyScreen(
    onResetSearch: () -> Unit,
    state: ProductListViewModel.ProductListViewModelState
) {
    val messageEmpty = when(state) {
        ProductListViewModel.ProductListViewModelState.Empty.Fresh -> {
            stringResource(R.string.no_results_to_show)
        }
        is ProductListViewModel.ProductListViewModelState.Empty.Search -> {
            stringResource(R.string.no_results, state.query)
        }
        else -> stringResource(R.string.no_results_to_show)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(margin16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.DirectionsRun,
            "run",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(bottom = margin16)
        )
        Text(
            modifier = Modifier
                .padding(start = margin16, bottom = margin16),
            text = messageEmpty,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        ButtonMedium(
            text = stringResource(R.string.reset_search),
            backgroundColor = MaterialTheme.colors.onSurface,
            onClick = {
                onResetSearch.invoke()
            }
        )
    }
}

@Preview
@Composable
fun EmptyScreenPreview() {
    EmptyScreen(
        onResetSearch = {},
        state = ProductListViewModel.ProductListViewModelState.Empty.Fresh
    )
}
