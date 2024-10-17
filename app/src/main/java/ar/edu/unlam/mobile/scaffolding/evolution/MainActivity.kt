package ar.edu.unlam.mobile.scaffolding.evolution
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.edu.unlam.mobile.scaffolding.ui.core.NavigationWrapper
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme(darkTheme = true) {
                NavigationWrapper()
            }
        }
    }
}
