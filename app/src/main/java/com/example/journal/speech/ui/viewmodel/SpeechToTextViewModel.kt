package com.example.journal.speech.ui.viewmodel

import android.content.Context
import com.example.journal.speech.data.SpeechState
import com.example.journal.speech.data.SpeechToTextResults
import kotlinx.coroutines.flow.MutableStateFlow

interface SpeechToTextViewModel {
    fun startListening(context: Context)
    val speechToTextResults: MutableStateFlow<SpeechToTextResults>
}