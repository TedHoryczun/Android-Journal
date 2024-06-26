package com.example.journal.speech.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.journal.speech.ui.viewmodel.MockSpeechToTextViewModel
import com.example.journal.ui.theme.JournalTheme
import com.example.journal.ui.theme.spacing

@Composable
fun SpeechToText(
    addSpeechToString: (string: String) -> AnnotatedString,
    initTextValue: String = "I will make my dog walk tomorrow people are going strong "
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.spacing.medium)
    ) {
        var textValue by remember {
            mutableStateOf(
                initTextValue,
            )
        }
        Box(Modifier.height(500.dp)) {
            Column {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textValue,
                    onValueChange = { textValue = it },
                    visualTransformation = TextFieldColorTransformation(addSpeechToString(textValue))
                )
                var isDisplayed by remember {
                    mutableStateOf(false)
                }
                if(isDisplayed){
                    Text("This is displayed now")

                }
                Button(onClick = { isDisplayed = true }) {
                    Text("Hello")

                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSpeechToText() {
    JournalTheme {
        val vm = MockSpeechToTextViewModel()
        SpeechToText(
            vm::getEnglishWordsFromText,
            "I will make my dog walk tomorrow people are going strong "
        )
    }
}