package com.example.journal.speech.data

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.ERROR_NO_MATCH
import android.speech.SpeechRecognizer.ERROR_SPEECH_TIMEOUT
import kotlinx.coroutines.flow.MutableStateFlow

class SpeechToTextRepo : RecognitionListener {
    val state = MutableStateFlow(SpeechState.NOT_LISTENING)
    val partialResults = MutableStateFlow("")
    val fullResults = MutableStateFlow("")
    override fun onEndOfSegmentedSession() {
        super.onEndOfSegmentedSession()
        state.value = SpeechState.NOT_LISTENING
    }

    override fun onReadyForSpeech(bundle: Bundle?) {
        state.value = SpeechState.LISTENING
    }

    override fun onBeginningOfSpeech() {
        state.value = SpeechState.LISTENING
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
        state.value = SpeechState.NOT_LISTENING
    }

    override fun onError(error: Int) {
        if (state.value == SpeechState.LISTENING &&
            (error == ERROR_NO_MATCH || error == ERROR_SPEECH_TIMEOUT)
        ) {
            state.value = SpeechState.NOT_LISTENING
        }
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && matches.size > 0) {
            val text = matches[0];
            fullResults.value = text
            println("speech-to-text: $text")
        }
        state.value = SpeechState.NOT_LISTENING
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
        println("event")
    }
}