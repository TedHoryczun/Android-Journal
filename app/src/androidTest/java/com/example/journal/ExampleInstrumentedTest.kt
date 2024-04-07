package com.example.journal

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.journal.speech.ui.SpeechToText
import com.example.journal.speech.ui.viewmodel.MockSpeechToTextViewModel
import com.example.journal.ui.theme.JournalTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun runApp() {
        composeTestRule.setContent {
            JournalTheme {
                val vm = MockSpeechToTextViewModel()
                SpeechToText(addSpeechToString = vm::getEnglishWordsFromText)

            }
        }
        composeTestRule.onNodeWithText("This is displayed now").isNotDisplayed()
        composeTestRule.onNodeWithText("Hello").performClick()
        composeTestRule.onNodeWithText("This is displayed now").isDisplayed()
    }
}