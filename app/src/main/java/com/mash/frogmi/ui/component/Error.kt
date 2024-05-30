package com.mash.frogmi.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mash.frogmi.R
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.ui.theme.FrogmiTestTheme

@Composable
fun ErrorScreen(
    message: String,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = modifier.size(125.dp),
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Image Error",
                    contentScale = ContentScale.Fit,
                )
                Text(text = message, modifier = Modifier.padding(16.dp))
                Button(onClick = retryAction) {
                    Text(stringResource(id = R.string.retry_action))
                }
            }
        }
    }
}

@Composable
fun ErrorApiExceptionScreen(
    exception: ApiException,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = modifier.size(125.dp),
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Image Error",
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = stringResource(id = R.string.api_exception, exception.code, exception.message ?: "N/A"),
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = retryAction) {
                    Text(stringResource(id = R.string.retry_action))
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ErrorApiExceptionScreenPreview() {
    FrogmiTestTheme {
        ErrorApiExceptionScreen(ApiException(1, "Desconocido"), retryAction = { })
    }
}

@Preview(apiLevel = 33)
@Composable
fun ErrorScreenPreview() {
    FrogmiTestTheme {
        ErrorScreen(message = stringResource(id = R.string.network_error), retryAction = { })
    }
}