package com.example.journal.speech

import android.content.Context
import android.speech.SpeechRecognizer
import com.example.journal.speech.data.SpeechToTextRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpeechModule {
    @Provides
    @Singleton
    fun provideSpeechToTextRepo(): SpeechToTextRepo {
        return SpeechToTextRepo()
    }
    @Provides
    @Singleton
    fun providesSpeechRecognizer(@ApplicationContext context: Context): SpeechRecognizer {
        return SpeechRecognizer.createSpeechRecognizer(context)

    }
}
