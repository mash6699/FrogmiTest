package com.mash.frogmi.test

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.test.Tags.TAG_DOWNLOADING_LOADER
import com.mash.frogmi.test.Tags.TAG_ERROR_BUTTON
import com.mash.frogmi.test.Tags.TAG_ERROR_COMPONENT
import com.mash.frogmi.test.Tags.TAG_ERROR_IMAGE
import com.mash.frogmi.test.Tags.TAG_ERROR_TEXT
import com.mash.frogmi.test.Tags.TAG_FINISH_COMPONENT
import com.mash.frogmi.test.Tags.TAG_FINISH_IMAGE
import com.mash.frogmi.test.Tags.TAG_FINISH_TEXT
import com.mash.frogmi.test.Tags.TAG_IDLE_LOADER
import com.mash.frogmi.ui.screen.HomeScrollLazyColumn
import com.mash.frogmi.ui.screen.HomeUiState
import com.mash.frogmi.ui.theme.FrogmiTestTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.mash.frogmi.R


class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var uiState by mutableStateOf<HomeUiState>(HomeUiState.Idle)

    @Before
    fun setUp() {
        composeTestRule.setContent {
            FrogmiTestTheme {
                HomeScrollLazyColumn(
                    uiState = uiState,
                    onLoadMore = {}
                )
            }
        }
    }

    @Test
    fun testHomeStateIdle() {
        composeTestRule.onRoot().printToLog("TAG")
        composeTestRule.apply {
            onNodeWithTag(TAG_IDLE_LOADER).assertExists().assertIsDisplayed()
        }
    }

    @Test
    fun testHomeStateDownloading() {
        uiState = HomeUiState.Downloading
        composeTestRule.apply{
            onNodeWithTag(TAG_DOWNLOADING_LOADER).assertExists().assertIsDisplayed()
        }
    }

    @Test
    fun testHomeStateError() {
        uiState = HomeUiState.Error(ApiException(0, "Error network connection"))
        val textRetry = composeTestRule.activity.getString(R.string.retry_action)
        composeTestRule.apply{
            onNodeWithTag(TAG_ERROR_COMPONENT).assertExists().assertIsDisplayed()
            onNodeWithTag(TAG_ERROR_IMAGE).assertExists().assertIsDisplayed()
            onNodeWithTag(TAG_ERROR_TEXT).assertExists().assertIsDisplayed()
            onNodeWithTag(TAG_ERROR_BUTTON).assertExists().assertIsDisplayed()
            onNodeWithText(textRetry).assertIsDisplayed()
        }
    }


    @Test
    fun testHomeStateFinish() {
        uiState = HomeUiState.Finish
        val text = composeTestRule.activity.getString(R.string.there_are_no_more_records)
        composeTestRule.apply{
            onNodeWithTag(TAG_FINISH_COMPONENT).assertExists().assertIsDisplayed()
            onNodeWithTag(TAG_FINISH_IMAGE).assertExists().assertIsDisplayed()
            onNodeWithTag(TAG_FINISH_TEXT).assertExists().assertIsDisplayed()
            onNodeWithText(text).assertIsDisplayed()
        }
    }
}

object Tags {
    const val TAG_FINISH_COMPONENT = "finishComponent"
    const val TAG_FINISH_IMAGE = "imageFinish"
    const val TAG_FINISH_TEXT = "textFinish"


    const val TAG_ERROR_COMPONENT = "errorComponent"
    const val TAG_ERROR_IMAGE = "imageError"
    const val TAG_ERROR_TEXT = "textError"
    const val TAG_ERROR_BUTTON = "buttonError"

    const val TAG_DOWNLOADING_LOADER = "circularLoader"
    const val TAG_IDLE_LOADER = "animationLoader"
}