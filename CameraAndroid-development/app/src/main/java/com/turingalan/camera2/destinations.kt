package com.turingalan.camera2
import kotlinx.serialization.Serializable
@Serializable
sealed class destinations (val route: String) {
    @Serializable
    object Camera: destinations("CameraScreen")
    @Serializable
    data class Capture(
        val photoPath: String
    ) : destinations("CaptureScreen/{photoPath}")
}
