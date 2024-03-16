package com.example.journal.speech.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journal.speech.data.SpeechToTextRepo
import com.example.journal.speech.data.SpeechToTextResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeechToTextViewModelImpl @Inject constructor(
    var speechToTextRepo: SpeechToTextRepo,
    var speechRecog: SpeechRecognizer
) : ViewModel(), SpeechToTextViewModel {
    override val speechToTextResults =
        MutableStateFlow(SpeechToTextResults())

    private fun observe() {
        speechRecog.setRecognitionListener(speechToTextRepo)
        viewModelScope.launch {
            speechToTextRepo.state.collectLatest {
                speechToTextResults.value = speechToTextResults.value.copy(state = it)
            }
            speechToTextRepo.fullResults.collectLatest {
                speechToTextResults.value = speechToTextResults.value.copy(fullResults = it)
            }
            speechToTextRepo.partialResults.collectLatest {
                speechToTextResults.value = speechToTextResults.value.copy(partialResults = it)
            }
        }

    }

    override fun startListening(context: Context) {
        val recognizerIntent =
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
            }
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecog.startListening(recognizerIntent)
            observe()
        }
    }
}