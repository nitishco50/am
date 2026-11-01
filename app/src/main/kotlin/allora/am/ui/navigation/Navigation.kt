package allora.am.ui.navigation

import allora.am.model.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// --- Navigation Routes ---
sealed class Screen {
    object Splash : Screen()
    object Welcome : Screen()
    object MusicPreferences : Screen()
    object SelectArtists : Screen()
    object NotificationPermission : Screen()
    object Home : Screen()
    object Explore : Screen()
    object Search : Screen()
    object SearchResults : Screen()
    object FullScreenPlayer : Screen()
    object Lyrics : Screen()
    object Queue : Screen()
    object PlayerMoreOptions : Screen()
    object Library : Screen()
    object FavoriteSongs : Screen()
    object PlaylistDetails : Screen()
    object CreatePlaylist : Screen()
    object EditPlaylist : Screen()
    object AddSongsToPlaylist : Screen()
    object AlbumDetails : Screen()
    object ArtistDetails : Screen()
    object Downloads : Screen()
    object Radio : Screen()
    object RadioStationDetails : Screen()
    object MoodMoments : Screen()
    object GenreDetails : Screen()
    object Charts : Screen()
    object Profile : Screen()
    object EditProfile : Screen()
    object Settings : Screen()
    object AudioQuality : Screen()
    object Equalizer : Screen()
    object Subscription : Screen()
    object ListeningHistory : Screen()
    object Notifications : Screen()
    object HelpSupport : Screen()
}

// --- Navigation State Holder ---
class AppNavigation {
    var currentScreen: Screen by mutableStateOf<Screen>(Screen.Splash)
    var miniPlayerSong: Song? = null
    var fullScreenPlayerSong: Song? = null
    var showMiniPlayer by mutableStateOf(false)

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun playSong(song: Song) {
        miniPlayerSong = song
        fullScreenPlayerSong = song
        showMiniPlayer = true
    }

    fun closeMiniPlayer() {
        showMiniPlayer = false
        miniPlayerSong = null
    }
}

@Composable
fun rememberAppNavigation(): AppNavigation = remember { AppNavigation() }

// --- Bottom Nav Tab ---
enum class BottomTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Home("Home", Icons.Default.Home),
    Explore("Explore", Icons.Default.Search),
    Library("Library", Icons.Default.LibraryMusic),
    Radio("Radio", Icons.Default.Radio),
    Profile("Profile", Icons.Default.Person);

    companion object {
        fun fromScreen(screen: Screen): BottomTab? = when (screen) {
            Screen.Home -> Home
            Screen.Explore -> Explore
            Screen.Library, Screen.FavoriteSongs, Screen.PlaylistDetails, Screen.CreatePlaylist, Screen.EditPlaylist, Screen.AddSongsToPlaylist, Screen.AlbumDetails, Screen.ArtistDetails, Screen.Downloads -> Library
            Screen.Radio, Screen.RadioStationDetails, Screen.MoodMoments, Screen.GenreDetails, Screen.Charts -> Radio
            Screen.Profile, Screen.EditProfile, Screen.Settings, Screen.AudioQuality, Screen.Equalizer, Screen.Subscription, Screen.ListeningHistory, Screen.Notifications, Screen.HelpSupport -> Profile
            else -> null
        }
    }
}
