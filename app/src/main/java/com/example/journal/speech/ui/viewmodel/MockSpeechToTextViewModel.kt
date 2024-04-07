package com.example.journal.speech.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.AnnotatedString
import com.example.journal.speech.data.EnglishRepo
import com.example.journal.speech.data.SpeechToTextResults
import kotlinx.coroutines.flow.MutableStateFlow

class MockSpeechToTextViewModel : SpeechToTextViewModel{
    override fun startListening(context: Context) {
    }

    override val speechToTextResults: MutableStateFlow<SpeechToTextResults> = MutableStateFlow(SpeechToTextResults())
    override fun getEnglishWordsFromText(text: String): AnnotatedString {
        return EnglishRepo().getEnglishWordsFromString(text)
    }
}