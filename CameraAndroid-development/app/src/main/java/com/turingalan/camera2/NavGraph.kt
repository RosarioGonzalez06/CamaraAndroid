package com.turingalan.camera2

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val viewModel: CameraViewModel = hiltViewModel()
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    )
    { innerPadding ->
        NavHost(navController = navController, startDestination = destinations.Camera) {
            val hostModifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding)
            composable<destinations.Camera> {
                CameraScreen(
                    modifier = hostModifier,
                    viewModel=viewModel,
                    onNavigateToCapture = {
                        navController.navigate(destinations.Capture)
                    },
                )
            }
            composable <destinations.Capture>{
                CaptureScreen(
                    onCancel = {navController.popBackStack()},
                    )
                }
            }
        }
    }
