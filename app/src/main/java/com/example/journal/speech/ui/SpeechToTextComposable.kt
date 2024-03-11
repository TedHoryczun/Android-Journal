package com.example.journal.speech.ui

import android.content.Context
import android.speech.SpeechRecognizer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.journal.speech.data.SpeechToTextRepo
import com.example.journal.speech.viewmodel.SpeechToTextViewModel
import com.example.journal.speech.viewmodel.SpeechToTextViewModelImpl

@Composable
fun SpeechToText(context: Context, speechToTextRepo: SpeechToTextRepo, viewModel: SpeechToTextViewModel = SpeechToTextViewModelImpl(context, speechToTextRepo)) {
    if (SpeechRecognizer.isRecognitionAvailable(context)) {
        viewModel.startListening(context)
    }
    val results by viewModel.speechToTextResults.collectAsState()
    val text = results.fullResults.ifEmpty { results.partialResults }
    Text(text = text)
}