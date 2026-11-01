package allora.am.ui.navigation

import allora.am.data.MockData
import allora.am.model.Song
import allora.am.ui.components.*
import allora.am.ui.screens.library.*
import allora.am.ui.screens.main.*
import allora.am.ui.screens.onboarding.*
import allora.am.ui.screens.player.*
import allora.am.ui.screens.profile.*
import allora.am.ui.screens.radio.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// --- Main App Shell ---
@Composable
fun AppShell(
    navigation: AppNavigation,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    // Show full screen player as overlay when song is playing
    var showFullPlayer by remember { mutableStateOf(false) }

    LaunchedEffect(navigation.showMiniPlayer) {
        showFullPlayer = navigation.showMiniPlayer
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Main content
        when (navigation.currentScreen) {
            Screen.Splash -> SplashScreen(onFinish = { navigation.navigateTo(Screen.Welcome) })
            Screen.Welcome -> WelcomeScreen(
                onGetStarted = { navigation.navigateTo(Screen.MusicPreferences) },
                onContinueAsGuest = { navigation.navigateTo(Screen.Home) }
            )
            Screen.MusicPreferences -> MusicPreferencesScreen(
                selectedGenres = remember { mutableStateSetOf<String>() },
                onContinue = { navigation.navigateTo(Screen.SelectArtists) }
            )
            Screen.SelectArtists -> SelectFavoriteArtistsScreen(
                selectedArtists = remember { mutableStateSetOf<String>() },
                onSkip = { navigation.navigateTo(Screen.NotificationPermission) },
                onContinue = { navigation.navigateTo(Screen.NotificationPermission) }
            )
            Screen.NotificationPermission -> NotificationPermissionScreen(
                onEnable = { navigation.navigateTo(Screen.Home) },
                onNotNow = { navigation.navigateTo(Screen.Home) }
            )

            Screen.Home -> HomeScreen(
                onNavigate = { navigation.navigateTo(it) },
                onSongClick = { song -> navigation.playSong(song) }
            )
            Screen.Explore -> ExploreScreen(
                onNavigate = { navigation.navigateTo(it) }
            )
            Screen.Search -> SearchScreen(
                onSongClick = { song -> navigation.playSong(song) },
                onNavigate = { navigation.navigateTo(it) }
            )
            Screen.SearchResults -> SearchResultsScreen(
                query = "Search results",
                onSongClick = { song -> navigation.playSong(song) },
                onNavigate = { navigation.navigateTo(it) }
            )

            Screen.FullScreenPlayer -> FullScreenPlayerScreen(
                song = navigation.fullScreenPlayerSong,
                onClose = { navigation.navigateTo(Screen.Home) },
                onPlayPause = {},
                onPrevious = {},
                onNext = {},
                onShuffle = {},
                onRepeat = {},
                onQueue = { navigation.navigateTo(Screen.Queue) },
                onLyrics = { navigation.navigateTo(Screen.Lyrics) },
                onMoreOptions = {},
                onFavorite = {}
            )
            Screen.Lyrics -> LyricsScreen(
                song = navigation.fullScreenPlayerSong,
                onClose = { navigation.navigateTo(Screen.Home) }
            )
            Screen.Queue -> QueueScreen(
                onClose = { navigation.navigateTo(Screen.Home) },
                onClear = {}
            )
            Screen.PlayerMoreOptions -> PlayerMoreOptionsSheet(
                onClose = {},
                onAddPlaylist = {},
                onAddFavorite = {},
                onGoArtist = {},
                onGoAlbum = {},
                onViewLyrics = {},
                onShare = {},
                onSongInfo = {}
            )

            Screen.Library -> LibraryScreen(
                onNavigate = { navigation.navigateTo(it) },
                onSongClick = { song -> navigation.playSong(song) }
            )
            Screen.FavoriteSongs -> FavoriteSongsScreen(
                onSongClick = { song -> navigation.playSong(song) },
                onPlayAll = {},
                onShuffleAll = {}
            )
            Screen.PlaylistDetails -> PlaylistDetailsScreen(
                onClose = { navigation.navigateTo(Screen.Library) },
                onPlayAll = {},
                onShuffleAll = {},
                onDownload = {},
                onSongClick = { song -> navigation.playSong(song) }
            )
            Screen.CreatePlaylist -> CreatePlaylistScreen(
                onClose = { navigation.navigateTo(Screen.Library) },
                onCreate = { navigation.navigateTo(Screen.Library) }
            )
            Screen.EditPlaylist -> EditPlaylistScreen(
                onClose = { navigation.navigateTo(Screen.Library) },
                onSave = { navigation.navigateTo(Screen.Library) }
            )
            Screen.AddSongsToPlaylist -> AddSongsToPlaylistScreen(
                onClose = { navigation.navigateTo(Screen.Library) },
                onAddSelected = { navigation.navigateTo(Screen.Library) }
            )
            Screen.AlbumDetails -> AlbumDetailsScreen(
                onClose = { navigation.navigateTo(Screen.Library) },
                onPlayAll = {},
                onShuffleAll = {},
                onSave = {},
                onSongClick = { song -> navigation.playSong(song) }
            )
            Screen.ArtistDetails -> ArtistDetailsScreen(
                onClose = { navigation.navigateTo(Screen.Library) },
                onFollow = {},
                onPlayAll = {},
                onShuffleAll = {}
            )
            Screen.Downloads -> DownloadsScreen(
                onNavigate = { navigation.navigateTo(it) }
            )

            Screen.Radio -> RadioScreen(
                onNavigate = { navigation.navigateTo(it) }
            )
            Screen.RadioStationDetails -> RadioStationDetailsScreen(
                onClose = { navigation.navigateTo(Screen.Radio) },
                onPlay = {}
            )
            Screen.MoodMoments -> MoodMomentsScreen(
                onNavigate = { navigation.navigateTo(it) }
            )
            Screen.GenreDetails -> GenreDetailsScreen(
                onClose = { navigation.navigateTo(Screen.Explore) },
                onSongClick = { song -> navigation.playSong(song) }
            )
            Screen.Charts -> ChartsScreen(
                onSongClick = { song -> navigation.playSong(song) }
            )

            Screen.Profile -> ProfileScreen(
                onNavigate = { navigation.navigateTo(it) },
                onEditProfile = { navigation.navigateTo(Screen.EditProfile) },
                onSongClick = { song -> navigation.playSong(song) }
            )
            Screen.EditProfile -> EditProfileScreen(
                onClose = { navigation.navigateTo(Screen.Profile) },
                onSave = { navigation.navigateTo(Screen.Profile) }
            )
            Screen.Settings -> SettingsScreen(
                onNavigate = { navigation.navigateTo(it) }
            )
            Screen.AudioQuality -> AudioQualityScreen(
                onClose = { navigation.navigateTo(Screen.Settings) }
            )
            Screen.Equalizer -> EqualizerScreen(
                onClose = { navigation.navigateTo(Screen.Settings) }
            )
            Screen.Subscription -> SubscriptionScreen(
                onClose = { navigation.navigateTo(Screen.Settings) },
                onUpgrade = {}
            )
            Screen.ListeningHistory -> ListeningHistoryScreen(
                onClear = {}
            )
            Screen.Notifications -> NotificationsScreen()
            Screen.HelpSupport -> HelpSupportScreen()
        }

        // Mini player overlay
        if (navigation.showMiniPlayer && navigation.miniPlayerSong != null) {
            AnimatedVisibility(
                visible = navigation.showMiniPlayer,
                enter = slideInVertically(initialOffsetY = { it }),
                modifier = Modifier.align(Alignment.Bottom).padding(bottom = 80.dp)
            ) {
                MiniPlayer(
                    song = navigation.miniPlayerSong,
                    onPlayPause = {},
                    onNext = {},
                    onExpand = {
                        navigation.navigateTo(Screen.FullScreenPlayer)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        // Full screen player overlay
        if (showFullPlayer && navigation.fullScreenPlayerSong != null) {
            AnimatedVisibility(
                visible = showFullPlayer,
                enter = fadeIn(animationSpec = androidx.compose.animation.tween(300)),
                exit = fadeOut(animationSpec = androidx.compose.animation.tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xCC000000))
                        .clickable { navigation.navigateTo(Screen.Home) }
                ) {
                    FullScreenPlayerScreen(
                        song = navigation.fullScreenPlayerSong,
                        onClose = { navigation.navigateTo(Screen.Home) },
                        onPlayPause = {},
                        onPrevious = {},
                        onNext = {},
                        onShuffle = {},
                        onRepeat = {},
                        onQueue = { navigation.navigateTo(Screen.Queue) },
                        onLyrics = { navigation.navigateTo(Screen.Lyrics) },
                        onMoreOptions = {},
                        onFavorite = {}
                    )
                }
            }
        }

        // Bottom navigation (skip on onboarding screens)
        val isOnboarding = navigation.currentScreen !in listOf(
            Screen.Home, Screen.Explore, Screen.Library, Screen.Radio, Screen.Profile,
            Screen.FavoriteSongs, Screen.PlaylistDetails, Screen.CreatePlaylist, Screen.EditPlaylist,
            Screen.AddSongsToPlaylist, Screen.AlbumDetails, Screen.ArtistDetails, Screen.Downloads,
            Screen.Radio, Screen.RadioStationDetails, Screen.MoodMoments, Screen.GenreDetails, Screen.Charts,
            Screen.Settings, Screen.AudioQuality, Screen.Equalizer, Screen.Subscription, Screen.ListeningHistory,
            Screen.Notifications, Screen.HelpSupport, Screen.Search, Screen.SearchResults
        )

        if (!isOnboarding) {
            val currentTab = BottomTab.fromScreen(navigation.currentScreen) ?: BottomTab.Home
            val tabIndex = listOf(BottomTab.Home, BottomTab.Explore, BottomTab.Library, BottomTab.Radio, BottomTab.Profile).indexOf(currentTab)

            Box(
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                BottomNavBar(
                    selectedIndex = tabIndex,
                    onTabSelected = { index ->
                        val tab = listOf(BottomTab.Home, BottomTab.Explore, BottomTab.Library, BottomTab.Radio, BottomTab.Profile)[index]
                        navigation.navigateTo(when (tab) {
                            BottomTab.Home -> Screen.Home
                            BottomTab.Explore -> Screen.Explore
                            BottomTab.Library -> Screen.Library
                            BottomTab.Radio -> Screen.Radio
                            BottomTab.Profile -> Screen.Profile
                        })
                    }
                )
            }
        }
    }
}
