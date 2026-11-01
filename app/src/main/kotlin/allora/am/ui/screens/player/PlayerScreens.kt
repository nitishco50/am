package allora.am.ui.screens.player

import allora.am.data.MockData
import allora.am.model.Song
import allora.am.ui.components.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    var isPlaying by remember { mutableStateOf(true) }
    var isShuffle by remember { mutableStateOf(false) }
    var isRepeat by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0.35f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 48.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = textMain)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "NOW PLAYING", style = tNavLabel, color = textMuted)
                Text(text = "From ${song.album ?: "Single"}", style = tArtist, color = textSecondary)
            }
            IconButton(onClick = onMoreOptions) {
                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = textSecondary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.fillMaxSize(), cornerRadius = 24.dp)
            Icon(Icons.Default.Album, contentDescription = null, tint = textSecondary.copy(alpha = 0.5f), modifier = Modifier.size(96.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = song.title, style = tPlayerTitle, color = textMain, maxLines = 1)
                Text(text = song.artist, style = tSongTitle, color = textSecondary, maxLines = 1)
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    imageVector = if (song.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (song.isFavorite) accentPrimary else textSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        AudioProgressSlider(progress = progress, onProgressChange = { progress = it })
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val elapsed = (song.durationMs * progress).toLong().durationFormatted
            Text(text = elapsed, style = tNavLabel, color = textMuted)
            Text(text = song.durationFormatted, style = tNavLabel, color = textMuted)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { isShuffle = !isShuffle; onShuffle() }) {
                Icon(
                    Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = if (isShuffle) accentPrimary else textSecondary
                )
            }
            IconButton(onClick = onPrevious) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", tint = textMain, modifier = Modifier.size(36.dp))
            }
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(accentPrimary, shape = CircleShape)
                    .clickable { isPlaying = !isPlaying; onPlayPause() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
            IconButton(onClick = onNext) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = textMain, modifier = Modifier.size(36.dp))
            }
            IconButton(onClick = { isRepeat = !isRepeat; onRepeat() }) {
                Icon(
                    Icons.Default.Repeat,
                    contentDescription = "Repeat",
                    tint = if (isRepeat) accentPrimary else textSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SecondaryControl(Icons.Default.QueueMusic, "Queue", onQueue)
            SecondaryControl(Icons.Default.Subtitles, "Lyrics", onLyrics)
            SecondaryControl(Icons.Default.Share, "Share") {}
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SecondaryControl(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = textSecondary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
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
        "Midnight echoes in the quiet night",
        "Stars are shining bright above",
        "Lost in the rhythm of your heart",
        "We're dancing in the moonlight",
        "Everything feels so right",
        "The world fades away when you're near",
        "Just you and me, nothing else matters",
        "This moment will last forever",
        "Hear the music in the air",
        "Feel the beat of your heart",
        "We're flying high tonight",
        "Nothing can bring us down",
        "Just let the music play",
        "Until the morning light",
        "Midnight echoes fade away",
        "But the memory remains"
    )
    val currentLine = 3

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = textMain)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = song.title, style = tArtist, color = textMain)
                    Text(text = song.artist, style = tNavLabel, color = textSecondary)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = textSecondary)
                }
            }
        }

        itemsIndexed(lyrics) { index, line ->
            val isCurrent = index == currentLine
            Text(
                text = line,
                style = TextStyle(
                    fontSize = if (isCurrent) 20.sp else 16.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCurrent) textMain else if (index < currentLine) textMuted else textSecondary
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }
    }
}

// --- Screen 13: Queue / Up Next ---
@Composable
fun QueueScreen(
    onClose: () -> Unit,
    onClear: () -> Unit,
    onSongClick: (Song) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val songs = MockData.songs
    val currentSong = songs.first()
    val upNext = songs.drop(1)

    Column(
        modifier = modifier
            .fillMaxSize()
            
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = textMain)
            }
            Text(text = "Playing Next", style = tSection, color = textMain)
            IconButton(onClick = onClear) {
                Icon(Icons.Default.DeleteOutline, contentDescription = "Clear", tint = textMuted)
            }
        }

        Text(
            text = "NOW PLAYING",
            style = tNavLabel,
            color = textMuted,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        SongItem(song = currentSong, showNumber = false, modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "UP NEXT",
            style = tNavLabel,
            color = textMuted,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(upNext) { index, song ->
                SongItem(
                    song = song,
                    index = index,
                    showNumber = false,
                    onClick = { onSongClick(song) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
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
        MoreOptionsMenuItem("Add to Playlist", Icons.Default.PlaylistAdd, onAddPlaylist)
        MoreOptionsMenuItem("Add to Favorites", Icons.Default.Favorite, onAddFavorite)
        MoreOptionsMenuItem("Go to Artist", Icons.Default.Person, onGoArtist)
        MoreOptionsMenuItem("Go to Album", Icons.Default.Album, onGoAlbum)
        MoreOptionsMenuItem("View Lyrics", Icons.Default.Subtitles, onViewLyrics)
        MoreOptionsMenuItem("Share", Icons.Default.Share, onShare)
        MoreOptionsMenuItem("Song Information", Icons.Default.Info, onSongInfo)
    }
}
