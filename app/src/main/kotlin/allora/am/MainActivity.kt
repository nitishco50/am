package allora.am

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import allora.am.ui.navigation.AppShell
import allora.am.ui.navigation.AppNavigation
import allora.am.ui.navigation.rememberAppNavigation
import allora.am.ui.theme.MusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                val navigation = rememberAppNavigation()
                AppShell(
                    navigation = navigation,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
