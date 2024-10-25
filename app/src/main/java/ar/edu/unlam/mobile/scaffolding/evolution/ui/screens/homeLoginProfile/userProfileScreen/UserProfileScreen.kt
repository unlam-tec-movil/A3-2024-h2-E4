package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.Routes
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.SilverA
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.VioletSky
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun UserProfileScreen(
    navController: NavController,
    auth: FirebaseAuth,
) {
    val userId = auth.currentUser?.uid ?: ""
    val avatarUrl by produceState<String?>(initialValue = null) {
        value = getUserAvatarUrl(auth.currentUser!!.uid)
    }

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
                    painter =
                        rememberAsyncImagePainter(
                            // model = auth.currentUser?.photoUrl,
                            model = avatarUrl,
                            placeholder = painterResource(id = R.drawable.im_default_avatar),
                            error = painterResource(id = R.drawable.im_default_avatar),
                        ),
                    contentDescription = "Avatar Usuario",
                )
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .offset(10.dp),
                ) {
                    IconButton(
                        onClick = { navController.navigate(Routes.UploadImageScreenRoute) }, // Navegación a la pantalla de carga de imagen
                        modifier =
                            Modifier
                                .size(60.dp)
                                .background(
                                    colorResource(id = R.color.whatsappGreenSoft),
                                    shape = CircleShape,
                                ).clip(CircleShape),
                    ) {
                        Icon(
                            Icons.Default.CameraEnhance,
                            contentDescription = "Tomar Foto",
                            tint = Color.Black,
                            modifier = Modifier.size(35.dp),
                        )
                    }
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

suspend fun getUserAvatarUrl(
    // auth: FirebaseAuth,
    userId: String,
): String? {
    val db = FirebaseFirestore.getInstance()
    val docRef = db.collection("userAvatarImages").document(userId)
    return try {
        val documentSnapshot = docRef.get().await()
        if (documentSnapshot.exists()) {
            documentSnapshot.getString("url")
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
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
