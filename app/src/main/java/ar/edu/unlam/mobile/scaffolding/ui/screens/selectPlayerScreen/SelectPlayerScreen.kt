package ar.edu.unlam.mobile.scaffolding.ui.screens.selectPlayerScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun SelectPlayer(navController: NavController){

    Text(
        text = stringResource(id = R.string.EnterGame),
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily(Font(R.font.font_firestar)),
        fontStyle = FontStyle.Italic,
        fontSize = 28.sp,
        color = Color.White
    )
}