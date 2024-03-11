package com.example.journal.speech.ui

import android.content.Context
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.journal.speech.data.SpeechState
import com.example.journal.speech.viewmodel.SpeechToTextViewModelImpl

@Composable
fun SpeechToText(context: Context, viewModel: SpeechToTextViewModelImpl = viewModel()) {
    Box(Modifier.fillMaxSize()) {
        val results by viewModel.speechToTextResults.collectAsState()
        val text = results.fullResults.ifEmpty { results.partialResults }
        val state = results.state
        val listeningButtonText = if (state == SpeechState.NOT_LISTENING) {
            "Start Listening"
        } else {
            "Stop Listening"
        }
        Box(Modifier.height(500.dp)) {
            Text(text = text, modifier = Modifier.align(Alignment.TopStart))
        }
        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = { viewModel.startListening(context) })
        {
            Text(text = listeningButtonText)
        }
    }
}