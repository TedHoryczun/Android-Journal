package com.example.journal.speech.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.journal.speech.data.English

@Preview
@Composable
fun EnglishTextComposable(
    text: String = "I will make my dog walk tomorrow people are going",
    modifier: Modifier = Modifier
) {
    val verbsList = English.commonVerbs.joinToString(separator = " | ")
    val nounList = English.commonNouns.joinToString(separator = " | ")
    val regex = Regex("\\($verbsList\\)|\\($nounList\\)", RegexOption.IGNORE_CASE)
    val annontatedStringList = remember {
        var lastIndex = 0
        val annotatedStringList = mutableStateListOf<AnnotatedString.Range<String>>()
        regex.findAll(text).forEach { match ->
            val verbsMatch = Regex("\\($verbsList\\)").matches(match.value)
            val nounMatch = Regex("\\($nounList\\)").matches(match.value)
            if (verbsMatch) {
                lastIndex = matchText("verb", match, text, lastIndex, annotatedStringList)
            } else if (nounMatch) {
                lastIndex = matchText("noun", match, text, lastIndex, annotatedStringList)
            }
        }
        if (lastIndex < text.length) {
            annotatedStringList.add(
                AnnotatedString.Range(
                    text.substring(lastIndex, text.length),
                    lastIndex,
                    text.length,
                    "text"
                )
            )
        }
        annotatedStringList
    }
    val annotatedString = buildAnnotatedString {
        annontatedStringList.forEach {
            when (it.tag) {
                "verb" -> addDesignToWord(this, it, Color.Red)
                "noun" -> addDesignToWord(this, it, Color.Blue)
                else -> withStyle(style = SpanStyle()) { append(it.item) }
            }
        }
    }

    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}

private fun addDesignToWord(
    builder: AnnotatedString.Builder,
    text: AnnotatedString.Range<String>,
    color: Color
) {
    builder.pushStringAnnotation(tag = text.tag, annotation = text.item)
    builder.withStyle(style = SpanStyle(color = color)) { append(text.item) }
    builder.pop()
}

private fun matchText(
    tag: String,
    regex: MatchResult,
    text: String,
    lastIndex: Int,
    annotatedStringList: SnapshotStateList<AnnotatedString.Range<String>>
): Int {
    val start = regex.range.first
    val end = regex.range.last + 1
    val string = text.substring(start, end)
    if (start > lastIndex) {
        annotatedStringList.add(
            AnnotatedString.Range(
                text.substring(lastIndex, start),
                lastIndex,
                start,
                "text"
            )
        )
    }
    annotatedStringList.add(AnnotatedString.Range(string, start, end, tag))
    return end
}