package com.turingalan.camera2

import android.content.Context
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class   CameraViewModel : ViewModel() {

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()

    private var cameraSelector = DEFAULT_BACK_CAMERA
    private var cameraProvider: ProcessCameraProvider? = null

    private val previewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { request ->
            _surfaceRequest.value = request
        }
    }

    suspend fun bindToCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ) {
        cameraProvider = ProcessCameraProvider.awaitInstance(context)
        bind(lifecycleOwner)
        awaitCancellation()
    }

    fun switchCamera(lifecycleOwner: LifecycleOwner) {
        cameraSelector =
            if (cameraSelector == DEFAULT_BACK_CAMERA)
                DEFAULT_FRONT_CAMERA
            else
                DEFAULT_BACK_CAMERA

        bind(lifecycleOwner)
    }

    private fun bind(lifecycleOwner: LifecycleOwner) {
        cameraProvider?.apply {
            unbindAll()
            bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase
            )
        }
    }
}

