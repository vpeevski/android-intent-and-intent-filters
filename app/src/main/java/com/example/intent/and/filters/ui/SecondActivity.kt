package com.example.intent.and.filters.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.intent.and.filters.ui.theme.IntentAndIntentFiltersTheme

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        intent.getStringExtra("some_input_extra")
        setContent {
            IntentAndIntentFiltersTheme {
                Text(
                    text = "Second Activity"
                )
            }
        }
    }

}