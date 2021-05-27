package com.retaillist.ui.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.retaillist.R
import com.retaillist.ui.theme.margin16

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(margin16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            Modifier
                .padding(margin16)
                .width(50.dp)
                .height(50.dp)
        )
        Text(
            modifier = Modifier
                .padding(start = margin16),
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun LoadingScreenItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(margin16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            Modifier
                .padding(margin16)
                .width(50.dp)
                .height(50.dp)
        )
        Text(
            modifier = Modifier
                .padding(start = margin16),
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun LoadingScreenDialog() {
    AlertDialog(
        onDismissRequest = {

        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = margin16)
                        .fillMaxWidth(),
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        },
        confirmButton = {
            CircularProgressIndicator(
                Modifier
                    .padding(margin16)
                    .width(50.dp)
                    .height(50.dp)
            )
        },
        dismissButton = {

        },
        backgroundColor = MaterialTheme.colors.background,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    )
}


@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}