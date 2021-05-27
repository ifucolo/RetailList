package com.retaillist.ui.feature.products.states

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.retaillist.R
import com.retaillist.domain.model.Product
import com.retaillist.ui.components.ButtonMedium
import com.retaillist.ui.components.SearchButton
import com.retaillist.ui.components.SearchInputText
import com.retaillist.ui.feature.products.ProductListViewModel
import com.retaillist.ui.theme.margin16
import com.retaillist.ui.theme.margin8
import com.retaillist.ui.theme.shapes

@Composable
fun ProductListState(
    state: ProductListViewModel.ProductListViewModelState.ListProducts,
    onSearchQuery: (String) -> Unit,
    onResetResearch: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = margin16),
    ) {
        when(state) {
            is ProductListViewModel.ProductListViewModelState.ListProducts.FreshResult -> {
                SearchBar(
                    onItemSearch = {
                        onSearchQuery.invoke(it)
                    }
                )
            }
            is ProductListViewModel.ProductListViewModelState.ListProducts.SearchResult -> {
                ResetSearch(
                    query = state.query,
                    onResetResearch = onResetResearch
                )
            }
        }
        ProductListComponent(
            state = state,
            onProductClick = {
                onProductClick.invoke(it)
            }
        )
    }
}

@Composable
fun ResetSearch(
    query: String,
    onResetResearch: () -> Unit
) {
    Box(
        Modifier
            .padding(horizontal = margin16)
            .padding(top = margin16)
            .height(IntrinsicSize.Min)
    ) {
        ButtonMedium(
            text = stringResource(R.string.reset_search_for, query),
            backgroundColor = MaterialTheme.colors.onSurface,
            onClick = {
                onResetResearch.invoke()
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchBar(
    onItemSearch: (String) -> Unit, buttonText: String = stringResource(R.string.search)
) {
    val (text, onTextChange) = rememberSaveable { mutableStateOf("") }

    val submit = {
        if (text.isNotBlank()) {
            onItemSearch(text)
            //onTextChange("")
        }
    }

    InputField(
        text = text,
        onTextChange = onTextChange,
        submit = submit
    ) {
        SearchButton(onClick = submit, text = buttonText, enabled = text.isNotBlank())
    }
}


@Composable
fun InputField(
    text: String,
    onTextChange: (String) -> Unit,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit,
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = margin16)
                .padding(top = margin16)
                .height(IntrinsicSize.Min)
        ) {
            SearchInputText(
                text = text,
                onTextChange = onTextChange,
                onImeAction = submit,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListComponent(
    state: ProductListViewModel.ProductListViewModelState.ListProducts,
    onProductClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(margin8, margin8, margin8, margin8)
    ) {
        items(state.list) { itemProduct ->
            ProductItem(
                product = itemProduct,
                onProductClick = { product ->
                    onProductClick.invoke(product)
                }
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Product) -> Unit
) {
    Column(
        verticalArrangement =  Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onProductClick.invoke(product)
            }
    ) {
        ImageProductList(product.imgUrl)
        Text(
            text = product.name,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Left
        )
        Text(
            text = product.description,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Left
        )
        Text(
            text = product.price.toString(),//if have time apply currency
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun ImageProductList(url: String) {
    Image(
        painter = rememberCoilPainter(
            request = url
        ),
        contentDescription = "url",
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(shape = shapes.small),
        contentScale = ContentScale.FillBounds,
    )
}
