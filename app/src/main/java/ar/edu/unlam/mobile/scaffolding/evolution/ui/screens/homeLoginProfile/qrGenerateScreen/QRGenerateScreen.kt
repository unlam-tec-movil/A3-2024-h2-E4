package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.qrGenerateScreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun QRGenerateScreen(auth: FirebaseAuth) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.im_ironman),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier =
                    Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.size(50.dp))

                Text(
                    text = "READ THE CODE",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.creepster_regular)),
                    modifier = Modifier.padding(bottom = 20.dp),
                )

                Box(
                    modifier =
                        Modifier
                            .size(250.dp)
                            .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(15.dp))
                            .border(3.dp, Color.White, shape = RoundedCornerShape(15.dp)) // Marco blanco
                            .padding(16.dp),
                ) {
                    Image(
                        bitmap = getQrCodeBitMap("${auth.currentUser?.uid}"),
                        contentDescription = "My QR code",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

fun getQrCodeBitMap(qrCodeContent: String): ImageBitmap {
    val size = 1024
    val hints =
        hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 1
        }
    val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)
    val bitmap =
        Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    val color = if (bits[x, y]) Color.White else Color.Black
                    it.setPixel(x, y, color.toArgb())
                }
            }
        }
    return bitmap.asImageBitmap()
}
