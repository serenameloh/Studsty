package com.studsty.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.studsty.app.navigation.AppNavigation
import com.studsty.app.ui.theme.StudstyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            StudstyTheme {
                AppNavigation()
            }
        }
    }
}