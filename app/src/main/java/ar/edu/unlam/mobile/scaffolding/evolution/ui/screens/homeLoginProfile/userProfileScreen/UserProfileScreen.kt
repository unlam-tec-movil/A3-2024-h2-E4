package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen

import android.Manifest
import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.SilverA
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.VioletSky
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    auth: FirebaseAuth,
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val onImageCapture: () -> Unit = { } // TODO Lógica de captura de imágen
    val onEditField: (String) -> Unit = { } // TODO Lógica de edición de campo

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            listOf(SilverA, VioletSky),
                            startY = 0f,
                            endY = 800f,
                        ),
                ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            // Ajuste del logo encima del AVATAR (Queda feo si se ubica en esquina superior derecha)
            Image(
                painter = painterResource(id = R.drawable.iv_logo), // probar insertar el Logo de FF
                contentDescription = "Future Fight Logo",
                modifier =
                    Modifier
                        .size(150.dp) // Ajusta el tamaño según sea necesario
                        .padding(16.dp),
            )
            Spacer(modifier = Modifier.height(1.dp))

            Box(contentAlignment = Alignment.Center) {
                Image(
                    modifier =
                        Modifier
                            .size((200.dp))
                            .clip(CircleShape)
                            .border(5.dp, Color.White, CircleShape),
                    painter = rememberAsyncImagePainter(auth.currentUser),
                    contentDescription = "Avatar Usuario",
                )
                IconButton(
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .offset(10.dp),
                    onClick = onImageCapture,
                ) {
                    Icon(
                        Icons.Default.CameraEnhance,
                        contentDescription = "Tomar Foto",
                        modifier = Modifier.size(50.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            // Información del Usuario
            UserInfoField(
                label = "Nombre",
                value = auth.currentUser?.email!!,
                onEditField = onEditField,
            )
            UserInfoField(
                label = "Apellido",
                value = auth.currentUser?.email!!,
                onEditField = onEditField,
            )
            UserInfoField(
                label = "Información Adicional",
                value = auth.currentUser!!.isAnonymous.toString(),
                onEditField = onEditField,
            )
            UserInfoField(
                label = "Fecha de Creación",
                value = auth.currentUser!!.uid,
                onEditField = onEditField,
            )
        }
    }
}

@Composable
fun UserInfoField(
    label: String,
    value: String,
    onEditField: ((String) -> Unit)?,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                ).padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.titleMedium)
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
        }
        onEditField?.let {
            IconButton(onClick = { it(label) }) {
                Icon(Icons.Default.Create, contentDescription = "Editar $label")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CaptureImage(permissionState: PermissionState) {
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {
        val context = LocalContext.current
        val cameraController =
            remember {
                LifecycleCameraController(context)
            }

        val cameraLifeCycle = LocalLifecycleOwner.current
        cameraController.bindToLifecycle(cameraLifeCycle)

        AndroidView(factory = { context ->
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
        })
    } else {
        Text(text = "PERMISO A LA CÁMARA DENEGADO")
    }
}

// @Composable
// fun UserProfileScreen(auth: FirebaseAuth) {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Column {
//            Text(text = "User Profile")
//            Spacer(modifier = Modifier.size(16.dp))
//            Text(text = "${auth.currentUser!!.email}")
//            Spacer(modifier = Modifier.size(16.dp))
//            Text(text = "${auth.currentUser!!.isAnonymous}")
//            Spacer(modifier = Modifier.size(16.dp))
//            Text(text = auth.currentUser!!.providerId)
//        }
//    }
// }
