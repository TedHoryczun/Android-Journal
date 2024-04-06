package com.example.journal.speech.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.journal.speech.ui.viewmodel.MockSpeechToTextViewModel
import com.example.journal.speech.ui.viewmodel.SpeechToTextViewModel


fun EnglishAnnotatedString(
    text: String,
    addSpeechToString: (string: String) -> SnapshotStateList<AnnotatedString.Range<String>>
): AnnotatedString {
    val annontatedStringList = addSpeechToString(text)
    return buildAnnotatedString {
        annontatedStringList.forEach {
            when (it.tag) {
                "verb" -> addDesignToWord(this, it, Color.Red)
                "noun" -> addDesignToWord(this, it, Color.Blue)
                "adjective" -> addDesignToWord(this, it, Color.Green)
                else -> withStyle(style = SpanStyle()) { append(it.item) }
            }
        }
    }

}

private fun addDesignToWord(
    builder: AnnotatedString.Builder, text: AnnotatedString.Range<String>, color: Color
) {
    builder.pushStringAnnotation(tag = text.tag, annotation = text.item)
    builder.withStyle(style = SpanStyle(color = color)) { append(text.item) }
    builder.pop()
}

@Preview
@Composable
fun PreviewEnglishTextComposable(
    text: String = "I will make my dog walk tomorrow people are going strong ",
    modifier: Modifier = Modifier,
    viewModel: SpeechToTextViewModel = MockSpeechToTextViewModel()
) {
    val annotatedString = EnglishAnnotatedString(text) { viewModel.getEnglishWordsFromText(it) }
    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}
