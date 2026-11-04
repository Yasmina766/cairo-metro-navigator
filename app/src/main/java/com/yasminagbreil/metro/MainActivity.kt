package com.yasminagbreil.metro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yasminagbreil.metro.ui.home.HomeRoute
import com.yasminagbreil.metro.ui.theme.CairoMetroNavigatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CairoMetroNavigatorTheme {
                HomeRoute(onRouteRequested = { _, _ -> })
            }
        }
    }
}

