package allora.am.ui.screens.player

import allora.am.data.MockData
import allora.am.model.Song
import allora.am.ui.components.*
import allora.am.ui.navigation.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.AsyncImage

// --- Screen 11: Full-Screen Music Player ---
@Composable
fun FullScreenPlayerScreen(
    song: Song?,
    onClose: () -> Unit,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onQueue: () -> Unit,
    onLyrics: () -> Unit,
    onMoreOptions: () -> Unit,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (song == null) return

    val isPlaying by remember { mutableStateOf(true) }
    val progress by remember { mutableStateOf(0.35f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = textMain
                )
            }
            Text(text = "Now Playing", style = tSection, color = textMain)
            IconButton(onClick = onMoreOptions) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = textSecondary
                )
            }
        }

        // Album artwork
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .aspectRatio(1f)
                .background(surfaceElevated, shape = RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = song.artworkUrl,
                contentDescription = song.title,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Album,
                        contentDescription = null,
                        tint = textSecondary,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Song info
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = song.title,
                style = tPlayerTitle,
                color = textMain
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = song.artist,
                style = tSongTitle,
                color = textSecondary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Favorite button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onFavorite) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Favorite,
                    contentDescription = null,
                    tint = accentPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Progress slider
        Column(
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = song.durationFormatted, style = tNavLabel, color = textMuted)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "-${song.durationFormatted}", style = tNavLabel, color = textMuted)
            }
            Spacer(modifier = Modifier.height(8.dp))
            AudioProgressSlider(
                progress = progress,
                onProgressChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Player controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onShuffle) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Shuffle,
                    contentDescription = null,
                    tint = textSecondary,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onPrevious) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.SkipPrevious,
                    contentDescription = null,
                    tint = textMain,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Main play/pause - larger
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(accentPrimary, shape = CircleShape)
                    .clickable { onPlayPause() },
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onNext) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.SkipNext,
                    contentDescription = null,
                    tint = textMain,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onRepeat) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Repeat,
                    contentDescription = null,
                    tint = textSecondary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Secondary controls row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SecondaryControlButton(
                icon = Icons.Default.QueueMusic,
                label = "Queue",
                onClick = onQueue
            )
            SecondaryControlButton(
                icon = Icons.Default.Caption,
                label = "Lyrics",
                onClick = onLyrics
            )
            SecondaryControlButton(
                icon = Icons.Default.Share,
                label = "Share",
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun SecondaryControlButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textSecondary,
            modifier = Modifier.size(24.dp)
        )
        Text(text = label, style = tNavLabel, color = textSecondary)
    }
}

// --- Screen 12: Lyrics ---
@Composable
fun LyricsScreen(
    song: Song?,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (song == null) return

    val lyrics = listOf(
        "0:12 - Midnight echoes in the quiet night",
        "0:25 - Stars are shining bright above",
        "0:38 - Lost in the rhythm of your heart",
        "0:51 - We're dancing in the moonlight",
        "1:04 - Everything feels so right",
        "1:17 - The world fades away when you're near",
        "1:30 - Just you and me, nothing else matters",
        "1:43 - This moment will last forever",
        "1:56 - Hear the music in the air",
        "2:09 - Feel the beat of your heart",
        "2:22 - We're flying high tonight",
        "2:35 - Nothing can bring us down",
        "2:48 - Just let the music play",
        "3:01 - Until the morning light",
        "3:14 - Midnight echoes fade away",
        "3:27 - But the memory remains"
    )
    val currentLyricIndex by remember { mutableStateOf(3) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = textMain
                )
            }
            Text(text = "Lyrics", style = tSection, color = textMain)
            IconButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = textSecondary
                )
            }
        }

        // Album thumbnail
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)).background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = song.artworkUrl,
                    contentDescription = song.title,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Album,
                            contentDescription = null,
                            tint = textSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Column {
                Text(text = song.title, style = tSongTitle, color = textMain)
                Text(text = song.artist, style = tArtist, color = textSecondary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lyrics content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            items(lyrics.indexed) { (index, line) ->
                val isCurrent = index == currentLyricIndex
                val alpha = if (isCurrent) 1f else if (index < currentLyricIndex) 0.4f else 0.6f
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = androidx.compose.animation.tween(300))
                ) {
                    Text(
                        text = line,
                        style = TextStyle(
                            fontSize = if (isCurrent) 20.sp else 16.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) textMain else textSecondary.copy(alpha = alpha)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .animateContentSize(),
                        textAlign = androidx.compose.ui.text.TextAlign.Center
                    )
                }
            }
        }
    }
}

// --- Screen 13: Queue / Up Next ---
@Composable
fun QueueScreen(
    onClose: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val songs = MockData.songs
    val currentSong = songs.first()
    val upNext = songs.drop(1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = textMain
                )
            }
            Text(text = "Playing Next", style = tSection, color = textMain)
            IconButton(onClick = onClear) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = textMuted
                )
            }
        }

        // Currently playing
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)).background(surfaceElevated),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = currentSong.artworkUrl,
                        contentDescription = currentSong.title,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            androidx.compose.material3.Icon(
                                imageVector = androidx.compose.material3.Icons.Default.Album,
                                contentDescription = null,
                                tint = textSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = currentSong.title, style = tSongTitle, color = textMain)
                    Text(text = currentSong.artist, style = tArtist, color = textSecondary)
                }
            }
        }

        ListDivider()

        // Up Next list
        Text(
            text = "Up Next",
            style = tArtist,
            color = textMuted,
            modifier = Modifier.padding(horizontal = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(upNext.indexed) { (index, song) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {}
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = (index + 2).toString(),
                        style = tArtist,
                        color = textMuted,
                        modifier = Modifier.width(24.dp)
                    )
                    Box(
                        modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(surfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = song.artworkUrl,
                            contentDescription = song.title,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material3.Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = textSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = song.title, style = tSongTitle, color = textMain, maxLines = 1)
                        Text(text = song.artist, style = tArtist, color = textSecondary, maxLines = 1)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = song.durationFormatted, style = tArtist, color = textMuted)
                }
                if (index < upNext.size - 1) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 14: Player More Options ---
@Composable
fun PlayerMoreOptionsSheet(
    onClose: () -> Unit,
    onAddPlaylist: () -> Unit,
    onAddFavorite: () -> Unit,
    onGoArtist: () -> Unit,
    onGoAlbum: () -> Unit,
    onViewLyrics: () -> Unit,
    onShare: () -> Unit,
    onSongInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheetContent(
        title = "Options",
        onClose = onClose,
        modifier = modifier
    ) {
        MoreOptionsMenuItem("Add to Playlist", Icons.Default.AddToPlaylist, onAddPlaylist)
        Divider(color = divider).let { Divider(modifier = Modifier.padding(horizontal = 8.dp), color = divider) }
        MoreOptionsMenuItem("Add to Favorites", Icons.Default.Favorite, onAddFavorite)
        Divider(modifier = Modifier.padding(horizontal = 8.dp), color = divider)
        MoreOptionsMenuItem("Go to Artist", Icons.Default.Person, onGoArtist)
        Divider(modifier = Modifier.padding(horizontal = 8.dp), color = divider)
        MoreOptionsMenuItem("Go to Album", Icons.Default.Album, onGoAlbum)
        Divider(modifier = Modifier.padding(horizontal = 8.dp), color = divider)
        MoreOptionsMenuItem("View Lyrics", Icons.Default.Caption, onViewLyrics)
        Divider(modifier = Modifier.padding(horizontal = 8.dp), color = divider)
        MoreOptionsMenuItem("Share", Icons.Default.Share, onShare)
        Divider(modifier = Modifier.padding(horizontal = 8.dp), color = divider)
        MoreOptionsMenuItem("Song Information", Icons.Default.Info, onSongInfo)
    }
}
