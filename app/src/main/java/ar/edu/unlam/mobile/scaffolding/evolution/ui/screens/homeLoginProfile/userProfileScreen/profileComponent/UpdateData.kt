package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.profileComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.BlackCustom
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.CyanWay
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.DarkPurple
import ar.edu.unlam.mobile.scaffolding.evolution.ui.theme.IndigoDye

@Composable
fun UpdateData(onDismiss: () -> Unit) {
    var nickname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var infoUser by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors =
                CardColors(
                    containerColor = IndigoDye,
                    contentColor = DarkPurple,
                    disabledContentColor = BlackCustom,
                    disabledContainerColor = CyanWay,
                ),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Update your information",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    placeholder = { Text(text = "New Nickname") },
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(text = "New Name") },
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = infoUser,
                    onValueChange = { infoUser = it },
                    placeholder = { Text(text = "New User Information") },
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Update Now!")
                }
            }
        }
    }
}
