package com.example.journal.speech.data

data class SpeechToTextResults(
    val fullResults: String = "",
    val partialResults: String = "",
    val state: SpeechState = SpeechState.NOT_LISTENING

)