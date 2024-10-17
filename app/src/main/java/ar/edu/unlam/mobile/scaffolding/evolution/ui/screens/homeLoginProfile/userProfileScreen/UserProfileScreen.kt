package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(auth: FirebaseAuth) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = "User Profile")
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "${auth.currentUser!!.email}")
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "${auth.currentUser!!.isAnonymous}")
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = auth.currentUser!!.providerId)
        }
    }
}
