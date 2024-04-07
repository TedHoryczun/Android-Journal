package com.example.journal.speech.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

class EnglishRepo {
    fun getEnglishWordsFromString(text: String): AnnotatedString {
        val verbsList = English.commonVerbs.joinToString(separator = " | ")
        val nounList = English.commonNouns.joinToString(separator = " | ")
        val adjectiveList = English.commonAdjectives.joinToString(separator = " | ")
        val regex =
            Regex("\\($verbsList\\)|\\($nounList\\)|\\($adjectiveList\\)", RegexOption.IGNORE_CASE)
        var lastIndex = 0
        val annotatedStringList = mutableStateListOf<AnnotatedString.Range<String>>()
        regex.findAll(text).forEach { match ->
            val verbsMatch = regexMatch(verbsList, match)
            val nounMatch = regexMatch(nounList, match)
            val adjectiveMatch = regexMatch(adjectiveList, match)
            if (verbsMatch) {
                lastIndex = matchText("verb", match, text, lastIndex, annotatedStringList)
            } else if (nounMatch) {
                lastIndex = matchText("noun", match, text, lastIndex, annotatedStringList)
            } else if (adjectiveMatch) {
                lastIndex = matchText("adjective", match, text, lastIndex, annotatedStringList)
            }
        }
        if (lastIndex < text.length) {
            annotatedStringList.add(
                AnnotatedString.Range(
                    text.substring(lastIndex, text.length), lastIndex, text.length, "text"
                )
            )
        }
        return addColorsToString(annotatedStringList)

    }

    private fun addColorsToString(text: SnapshotStateList<AnnotatedString.Range<String>>): AnnotatedString {
        return buildAnnotatedString {
            text.forEach {
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

    private fun regexMatch(listOfWords: String, match: MatchResult): Boolean {
        return Regex("\\($listOfWords\\)", RegexOption.IGNORE_CASE).matches(match.value)
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
                    text.substring(lastIndex, start), lastIndex, start, "text"
                )
            )
        }
        annotatedStringList.add(AnnotatedString.Range(string, start, end, tag))
        return end
    }

}