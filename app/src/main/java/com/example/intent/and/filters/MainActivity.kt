package com.example.intent.and.filters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.intent.and.filters.ui.ImageViewModel
import com.example.intent.and.filters.ui.SecondActivity
import com.example.intent.and.filters.ui.theme.IntentAndIntentFiltersTheme
import java.net.URI

class MainActivity : ComponentActivity() {

    private val TAG = this.javaClass.simpleName

    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // By default brand new instance of the app is opened on receive image intent from other apps.
        // Add android:launchMode="singleTop" to use active app process if the app is already running.
        // This will call onNewIntent method instead.
//        intent.getStringExtra("image_uri")
        setContent {
            IntentAndIntentFiltersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModel.updateUri(uri)
    }

    @Composable
    fun Greeting() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewModel.uri?.let {
                Text("Image from external app ...")
                AsyncImage(model = it, contentDescription = "Image desc")
            }
            Button(onClick = {
                Intent(applicationContext, SecondActivity::class.java).also {
                    startActivity(it)
                }
            }) {
                Text(text = "Go to second activity")
            }

            Button(onClick = {
                Intent(Intent.ACTION_MAIN).also {
                    it.`package` = "com.google.android.youtube"
                    try {
                        startActivity(it)
                    } catch (e: ActivityNotFoundException) {
                        Log.d(TAG, "Youtube app is not installed on device: $e")
                    }
//                if (it.resolveActivity(context.packageManager) != null) {
//                    context.startActivity(it)
//                }
                }
            }) {
                Text(text = "Launch youtube")
            }

            Button(onClick = {
                val eMailIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
                    putExtra(Intent.EXTRA_SUBJECT, arrayOf("Test e-mail subject"))
                    putExtra(Intent.EXTRA_TEXT, arrayOf("Test e-mail content"))
                }
                if (eMailIntent.resolveActivity(packageManager) != null) {
                    startActivity(eMailIntent)
                }
            }) {
                Text(text = "Send e-mail")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun GreetingPreview() {
        Greeting()
    }
}