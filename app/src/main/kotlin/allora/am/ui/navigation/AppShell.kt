package allora.am.ui.navigation

import allora.am.data.MockData
import allora.am.data.MusicRepository
import allora.am.model.Song
import allora.am.playback.MusicPlayer
import allora.am.ui.components.MiniPlayer
import allora.am.ui.components.BottomNavBar
import allora.am.ui.screens.library.*
import allora.am.ui.screens.main.*
import allora.am.ui.screens.onboarding.*
import allora.am.ui.screens.player.*
import allora.am.ui.screens.profile.*
import allora.am.ui.screens.radio.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val onboardingScreens = setOf(
    Screen.Splash, Screen.Welcome, Screen.MusicPreferences,
    Screen.SelectArtists, Screen.NotificationPermission
)

private val playerScreens = setOf(
    Screen.FullScreenPlayer, Screen.Lyrics, Screen.Queue
)

private val bottomNavScreens = setOf(
    Screen.Home, Screen.Explore, Screen.Library, Screen.Radio, Screen.Profile,
    Screen.Search, Screen.SearchResults, Screen.FavoriteSongs, Screen.PlaylistDetails,
    Screen.CreatePlaylist, Screen.EditPlaylist, Screen.AddSongsToPlaylist,
    Screen.AlbumDetails, Screen.ArtistDetails, Screen.Downloads,
    Screen.RadioStationDetails, Screen.MoodMoments, Screen.GenreDetails, Screen.Charts,
    Screen.Settings, Screen.AudioQuality, Screen.Equalizer, Screen.Subscription,
    Screen.ListeningHistory, Screen.Notifications, Screen.HelpSupport, Screen.EditProfile
)

@Composable
fun AppShell(
    navigation: AppNavigation,
    modifier: Modifier = Modifier
) {
    val nav = navigation
    val screen = nav.currentScreen
    val context = LocalContext.current

    LaunchedEffect(Unit) { MusicRepository.bootstrap() }

    val play: (Song) -> Unit = { song ->
        nav.playSong(song)
        MusicPlayer.play(context, song)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF2A0E52),
                        Color(0xFF4B1A78),
                        Color(0xFF22103F),
                        Color(0xFF0C0620)
                    )
                )
            )
    ) {
        when (screen) {
            // Onboarding
            Screen.Splash -> SplashScreen(onFinish = { nav.navigateTo(Screen.Welcome) })
            Screen.Welcome -> WelcomeScreen(
                onGetStarted = { nav.navigateTo(Screen.MusicPreferences) },
                onContinueAsGuest = { nav.navigateTo(Screen.Home) }
            )
            Screen.MusicPreferences -> MusicPreferencesScreen(
                onContinue = { nav.navigateTo(Screen.SelectArtists) }
            )
            Screen.SelectArtists -> SelectFavoriteArtistsScreen(
                onSkip = { nav.navigateTo(Screen.NotificationPermission) },
                onContinue = { nav.navigateTo(Screen.NotificationPermission) }
            )
            Screen.NotificationPermission -> NotificationPermissionScreen(
                onEnable = { nav.navigateTo(Screen.Home) },
                onNotNow = { nav.navigateTo(Screen.Home) }
            )

            // Main tabs
            Screen.Home -> HomeScreen(
                onNavigate = { nav.navigateTo(it) },
                onSongClick = { play(it) }
            )
            Screen.Explore -> ExploreScreen(onNavigate = { nav.navigateTo(it) })
            Screen.Search -> SearchScreen(
                onSongClick = { play(it) },
                onNavigate = { nav.navigateTo(it) }
            )
            Screen.SearchResults -> SearchResultsScreen(
                query = "music",
                onSongClick = { play(it) },
                onNavigate = { nav.navigateTo(it) }
            )

            // Player
            Screen.FullScreenPlayer -> FullScreenPlayerScreen(
                song = nav.fullScreenPlayerSong ?: MockData.songs.firstOrNull(),
                onClose = { nav.navigateTo(Screen.Home) },
                onPlayPause = { MusicPlayer.toggle() },
                onPrevious = { MusicPlayer.previous(context) },
                onNext = { MusicPlayer.next(context) },
                onShuffle = {},
                onRepeat = {},
                onQueue = { nav.navigateTo(Screen.Queue) },
                onLyrics = { nav.navigateTo(Screen.Lyrics) },
                onMoreOptions = {},
                onFavorite = {}
            )
            Screen.Lyrics -> LyricsScreen(
                song = nav.fullScreenPlayerSong ?: MockData.songs.firstOrNull(),
                onClose = { nav.navigateTo(Screen.FullScreenPlayer) }
            )
            Screen.Queue -> QueueScreen(
                onClose = { nav.navigateTo(Screen.FullScreenPlayer) },
                onClear = {},
                onSongClick = { play(it) }
            )

            // Library
            Screen.Library -> LibraryScreen(
                onNavigate = { nav.navigateTo(it) },
                onSongClick = { play(it) }
            )
            Screen.FavoriteSongs -> FavoriteSongsScreen(
                onSongClick = { play(it) },
                onPlayAll = {},
                onShuffleAll = {}
            )
            Screen.PlaylistDetails -> PlaylistDetailsScreen(
                onClose = { nav.navigateTo(Screen.Library) },
                onPlayAll = {},
                onShuffleAll = {},
                onDownload = {},
                onSongClick = { play(it) }
            )
            Screen.CreatePlaylist -> CreatePlaylistScreen(
                onClose = { nav.navigateTo(Screen.Library) },
                onCreate = { nav.navigateTo(Screen.Library) }
            )
            Screen.EditPlaylist -> EditPlaylistScreen(
                onClose = { nav.navigateTo(Screen.Library) },
                onSave = { nav.navigateTo(Screen.Library) }
            )
            Screen.AddSongsToPlaylist -> AddSongsToPlaylistScreen(
                onClose = { nav.navigateTo(Screen.Library) },
                onAddSelected = { nav.navigateTo(Screen.Library) }
            )
            Screen.AlbumDetails -> AlbumDetailsScreen(
                onClose = { nav.navigateTo(Screen.Library) },
                onPlayAll = {},
                onShuffleAll = {},
                onSave = {},
                onSongClick = { play(it) }
            )
            Screen.ArtistDetails -> ArtistDetailsScreen(
                onClose = { nav.navigateTo(Screen.Library) },
                onFollow = {},
                onPlayAll = {},
                onShuffleAll = {},
                onSongClick = { play(it) }
            )
            Screen.Downloads -> DownloadsScreen(
                onNavigate = { nav.navigateTo(it) },
                onSongClick = { play(it) }
            )

            // Radio
            Screen.Radio -> RadioScreen(onNavigate = { nav.navigateTo(it) })
            Screen.RadioStationDetails -> RadioStationDetailsScreen(
                onClose = { nav.navigateTo(Screen.Radio) },
                onPlay = {}
            )
            Screen.MoodMoments -> MoodMomentsScreen(onNavigate = { nav.navigateTo(it) })
            Screen.GenreDetails -> GenreDetailsScreen(
                onClose = { nav.navigateTo(Screen.Explore) },
                onSongClick = { play(it) }
            )
            Screen.Charts -> ChartsScreen(onSongClick = { play(it) })

            // Profile
            Screen.Profile -> ProfileScreen(
                onNavigate = { nav.navigateTo(it) },
                onEditProfile = { nav.navigateTo(Screen.EditProfile) },
                onSongClick = { play(it) }
            )
            Screen.EditProfile -> EditProfileScreen(
                onClose = { nav.navigateTo(Screen.Profile) },
                onSave = { nav.navigateTo(Screen.Profile) }
            )
            Screen.Settings -> SettingsScreen(onNavigate = { nav.navigateTo(it) })
            Screen.AudioQuality -> AudioQualityScreen(onClose = { nav.navigateTo(Screen.Settings) })
            Screen.Equalizer -> EqualizerScreen(onClose = { nav.navigateTo(Screen.Settings) })
            Screen.Subscription -> SubscriptionScreen(
                onClose = { nav.navigateTo(Screen.Settings) },
                onUpgrade = {}
            )
            Screen.ListeningHistory -> ListeningHistoryScreen(onClear = {})
            Screen.Notifications -> NotificationsScreen()
            Screen.HelpSupport -> HelpSupportScreen()
            Screen.PlayerMoreOptions -> FullScreenPlayerScreen(
                song = nav.fullScreenPlayerSong ?: MockData.songs.firstOrNull(),
                onClose = { nav.navigateTo(Screen.Home) },
                onPlayPause = { MusicPlayer.toggle() },
                onPrevious = { MusicPlayer.previous(context) },
                onNext = { MusicPlayer.next(context) },
                onShuffle = {},
                onRepeat = {},
                onQueue = { nav.navigateTo(Screen.Queue) },
                onLyrics = { nav.navigateTo(Screen.Lyrics) },
                onMoreOptions = {},
                onFavorite = {}
            )
        }

        // Mini player + bottom nav on main screens
        if (screen in bottomNavScreens) {
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimatedVisibility(
                    visible = nav.showMiniPlayer && nav.miniPlayerSong != null,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    nav.miniPlayerSong?.let { song ->
                        MiniPlayer(
                            song = song,
                            onPlayPause = { MusicPlayer.toggle() },
                            onNext = { MusicPlayer.next(context) },
                            onExpand = { nav.navigateTo(Screen.FullScreenPlayer) },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }

                val currentTab = BottomTab.fromScreen(screen) ?: BottomTab.Home
                val tabs = listOf(BottomTab.Home, BottomTab.Explore, BottomTab.Library, BottomTab.Radio, BottomTab.Profile)
                BottomNavBar(
                    selectedIndex = tabs.indexOf(currentTab).coerceAtLeast(0),
                    onTabSelected = { index ->
                        nav.navigateTo(
                            when (tabs[index]) {
                                BottomTab.Home -> Screen.Home
                                BottomTab.Explore -> Screen.Explore
                                BottomTab.Library -> Screen.Library
                                BottomTab.Radio -> Screen.Radio
                                BottomTab.Profile -> Screen.Profile
                            }
                        )
                    }
                )
            }
        }
    }
}
