package com.turingalan.camera2
import kotlinx.serialization.Serializable
@Serializable
sealed class destinations (val route: String) {
    @Serializable
    object Camera: destinations("CameraScreen")
    @Serializable
    object Capture: destinations("CaptureScreen")
}
