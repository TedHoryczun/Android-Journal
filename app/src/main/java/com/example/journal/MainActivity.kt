package com.example.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.journal.speech.data.SpeechToTextRepo
import com.example.journal.speech.ui.SpeechToText
import com.example.journal.speech.ui.viewmodel.SpeechToTextViewModel
import com.example.journal.speech.ui.viewmodel.SpeechToTextViewModelImpl
import com.example.journal.ui.theme.JournalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val vm: SpeechToTextViewModelImpl by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JournalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permissions = rememberMultiplePermissionsState(
                        listOf(android.Manifest.permission.RECORD_AUDIO)
                    )
                    if (permissions.allPermissionsGranted) {
                        val textFromSpeech by vm.speechToTextResults.collectAsStateWithLifecycle()
                        if (textFromSpeech.fullResults.isNotEmpty()) {
                            SpeechToText(vm::getEnglishWordsFromText, textFromSpeech.fullResults)
                        } else {
                            SpeechToText(vm::getEnglishWordsFromText)

                        }
                    } else {
                        Column {

                            val textToShow =
                                if (permissions.permissions[0].status.shouldShowRationale) {
                                    "The camera is important for this app. Please grant the permission."
                                } else {
                                    "Camera permission required for this feature to be available. " +
                                            "Please grant the permission"
                                }
                            Text(textToShow)
                            Button(onClick = {
                                permissions.launchMultiplePermissionRequest()
                            }) {
                                Text("Request permission")
                            }
                        }
                    }
                }
            }
        }
    }
}