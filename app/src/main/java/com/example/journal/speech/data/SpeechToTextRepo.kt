package com.example.journal.speech.data

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow

class SpeechToTextRepo : RecognitionListener {
    val state = MutableStateFlow(SpeechState.NOT_LISTENING)
    val partialResults = MutableStateFlow("")
    val fullResults = MutableStateFlow("")
    override fun onReadyForSpeech(bundle: Bundle?) {
    }

    override fun onBeginningOfSpeech() {
        state.value = SpeechState.LISTENING
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(p0: Int) {
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && matches.size > 0) {
            val text = matches[0];
            fullResults.value = text
            println("speech-to-text: $text")
        }
    }

    override fun onPartialResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && matches.size > 0) {
            val text = matches[0];
            partialResults.value = text
            println("partial-speech: $text")
        }
    }

    override fun onEvent(p0: Int, event: Bundle?) {
    }

    private fun clear() {
        partialResults.value = ""
        state.value = SpeechState.NOT_LISTENING
    }
}