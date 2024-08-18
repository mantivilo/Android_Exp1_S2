package com.example.calendapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calendapp.ui.theme.login.LoginPage
import com.example.calendapp.ui.theme.CalendappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendappTheme {
                LoginPage()
            }
        }
    }
}
