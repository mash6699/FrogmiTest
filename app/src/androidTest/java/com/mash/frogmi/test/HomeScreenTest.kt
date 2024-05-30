package com.mash.frogmi.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import com.mash.frogmi.domain.usecase.GetStoresUseCase
import com.mash.frogmi.ui.HomeApp
import com.mash.frogmi.ui.screen.HomeViewModel
import com.mash.frogmi.ui.theme.FrogmiTestTheme
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var getStoreUseCase: GetStoresUseCase

    /*@Inject
    lateinit var viewModel: HomeViewModel*/

    @Before
    fun setUp() {

    }

    @Test
    fun testListScreen() {
        composeTestRule.setContent {
            FrogmiTestTheme {
                HomeApp()
            }
        }
        composeTestRule.onNodeWithTag("progress_indicator").assertIsDisplayed()
        composeTestRule.onNodeWithText("").performClick()

        /*val items = listOf(Item("1", "Item 1"))
        val pagingData = PagingData.from(items)

        runBlocking {
            viewModel.items.emit(pagingData)
        }
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()*/
    }

}