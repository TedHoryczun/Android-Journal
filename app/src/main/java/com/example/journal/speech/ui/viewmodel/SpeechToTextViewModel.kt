package com.example.journal.speech.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.AnnotatedString
import com.example.journal.speech.data.SpeechState
import com.example.journal.speech.data.SpeechToTextResults
import kotlinx.coroutines.flow.MutableStateFlow

interface SpeechToTextViewModel {
    fun startListening(context: Context)
    val speechToTextResults: MutableStateFlow<SpeechToTextResults>
    fun getEnglishWordsFromText(text: String): SnapshotStateList<AnnotatedString.Range<String>>
}