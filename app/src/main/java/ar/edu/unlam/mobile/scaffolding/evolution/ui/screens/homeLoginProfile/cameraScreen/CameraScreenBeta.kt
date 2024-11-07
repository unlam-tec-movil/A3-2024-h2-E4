package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.cameraScreen

import android.Manifest
import android.os.Environment
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.util.concurrent.Executor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreenBeta(
    navController: NavController,
    auth: FirebaseAuth,
) {
    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val cameraController =
        remember {
            LifecycleCameraController(context)
        }

    val permissionState =
        rememberMultiplePermissionsState(
            permissions =
                listOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
        )
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val executor = ContextCompat.getMainExecutor(context)
                    takePicture(cameraController, executor, directory)
                },
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Ironman style camera",
                    tint = Color.White,
                )
            }
        },
    ) {
        if (permissionState.allPermissionsGranted) {
            CamaraComposable(cameraController, lifecycle, modifier = Modifier.padding(it))
            Toast.makeText(context, "Photo successfully saved in Pictures", Toast.LENGTH_SHORT).show()
        } else {
            Text(text = "Permissions has been delegated", modifier = Modifier.padding(it))
        }
    }
}

fun takePicture(
    cameraController: LifecycleCameraController,
    executor: Executor,
    directory: File,
) {
    val image = File.createTempFile("img_", ".png", directory)
    val outputDirectory = ImageCapture.OutputFileOptions.Builder(image).build()
    cameraController.takePicture(
        outputDirectory,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println(outputFileResults.savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                println()
            }
        },
    )
}

@Composable
fun CamaraComposable(
    cameraController: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
) {
//    val cameraLifeCycle = LocalLifecycleOwner.current
//    cameraController.bindToLifecycle(cameraLifeCycle)
    cameraController.bindToLifecycle(lifecycleOwner)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView =
                PreviewView(context).apply {
                    layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                }
            previewView.controller = cameraController
            previewView
        },
    )
}

// @OptIn(ExperimentalPermissionsApi::class)
// @Composable
// private fun CaptureImage(permissionState: PermissionState) {
//    LaunchedEffect(Unit) {
//        permissionState.launchPermissionRequest()
//    }
//    if (permissionState.status.isGranted) {
//        CamaraComposable()
//    } else {
//        Text(text = "Permissions to camera delegated")
//    }
// }
