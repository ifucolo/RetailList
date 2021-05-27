package com.retaillist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.retaillist.ui.theme.btnColor
import com.retaillist.ui.theme.buttonHeight
import com.retaillist.ui.theme.shapes

@Composable
fun ButtonMedium(
    text: String,
    paddingBottom: Dp = 0.dp,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(bottom = paddingBottom)
            .fillMaxWidth()
            .height(buttonHeight),
        shape = shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        onClick = {
            onClick.invoke()
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = btnColor(),
            maxLines = 1
        )
    }
}

@Composable
fun ButtonSmall(
    text: String,
    paddingBottom: Dp = 0.dp,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(bottom = paddingBottom),
        shape = shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        onClick = {
            onClick.invoke()
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = btnColor(),
            maxLines = 1
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun SearchButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}
