package com.mash.frogmi.ui.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mash.frogmi.R
import com.mash.frogmi.ui.theme.FrogmiTestTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimateLoaderComponent() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.car))
    LottieAnimation(composition = composition)
}

@Composable
fun CircularIndicator(color: Color = MaterialTheme.colorScheme.primary) {
    CircularProgressIndicator(color = color)
}

@Preview(apiLevel = 33)
@Composable
fun CircularIndicatorPreview() {
    FrogmiTestTheme {
        CircularIndicator()
    }
}

@Preview(apiLevel = 33)
@Composable
fun AnimateLoaderComponentPreview() {
    FrogmiTestTheme {
        AnimateLoaderComponent()
    }
}