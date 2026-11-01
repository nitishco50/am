package allora.am.ui.components

import allora.am.model.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

// --- Color & Typography shortcuts ---
val bgPrimary = allora.am.ui.theme.BackgroundPrimary
val bgSecondary = allora.am.ui.theme.BackgroundSecondary
val surfaceCard = allora.am.ui.theme.SurfaceCard
val surfaceElevated = allora.am.ui.theme.SurfaceElevated
val accentPrimary = allora.am.ui.theme.AccentPrimary
val accentSecondary = allora.am.ui.theme.AccentSecondary
val textMain = allora.am.ui.theme.TextMain
val textSecondary = allora.am.ui.theme.TextSecondary
val textMuted = allora.am.ui.theme.TextMuted
val divider = allora.am.ui.theme.Divider
val success = allora.am.ui.theme.Success
val accentBlue = allora.am.ui.theme.AccentBlue
val accentCyan = allora.am.ui.theme.AccentCyan
val glassWhite = allora.am.ui.theme.GlassWhite
val glassBorder = allora.am.ui.theme.GlassBorder

val tHeadline = allora.am.ui.theme.Typography.headlineLarge
val tSection = allora.am.ui.theme.Typography.headlineMedium
val tSongTitle = allora.am.ui.theme.Typography.bodyLarge
val tArtist = allora.am.ui.theme.Typography.labelMedium
val tNavLabel = allora.am.ui.theme.Typography.labelSmall
val tPlayerTitle = allora.am.ui.theme.Typography.titleLarge

// --- Duration helper ---
val Long.durationFormatted: String
    get() {
        val seconds = this / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "$minutes:${remainingSeconds.toString().padStart(2, '0')}"
    }

// --- Placeholder artwork (vivid gradient tiles) ---
private val artworkGradients = listOf(
    listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)),
    listOf(Color(0xFFF72585), Color(0xFF7209B7)),
    listOf(Color(0xFF00C6FF), Color(0xFF0072FF)),
    listOf(Color(0xFF43E97B), Color(0xFF38F9D7)),
    listOf(Color(0xFFFF6A88), Color(0xFF6857E0)),
    listOf(Color(0xFFFA709A), Color(0xFFFEE140)),
    listOf(Color(0xFF30CFD0), Color(0xFF330867)),
    listOf(Color(0xFFFBAB7E), Color(0xFFF7CE68)),
    listOf(Color(0xFFB5179E), Color(0xFF3A0CA3)),
    listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)),
    listOf(Color(0xFFDA22FF), Color(0xFF9733EE)),
    listOf(Color(0xFFFF512F), Color(0xFFDD2476)),
)

@Composable
fun ArtworkPlaceholder(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp
) {
    val seed = ((url ?: contentDescription ?: "am").hashCode() % artworkGradients.size + artworkGradients.size) % artworkGradients.size
    val colors = artworkGradients[seed]
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .clip(shape)
            .background(Brush.linearGradient(colors))
            .background(
                Brush.verticalGradient(
                    listOf(Color.White.copy(alpha = 0.28f), Color.Transparent)
                )
            )
            .border(0.6.dp, Color.White.copy(alpha = 0.22f), shape),
        contentAlignment = Alignment.Center
    ) {
        if (!url.isNullOrBlank() && (url.startsWith("http://") || url.startsWith("https://"))) {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = contentDescription,
                tint = Color.White.copy(alpha = 0.92f),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

// --- 1. Floating Liquid Glass Navigation Bar ---
@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        Pair("Home", Icons.Default.Home),
        Pair("Explore", Icons.Default.Search),
        Pair("Library", Icons.Default.LibraryMusic),
        Pair("Radio", Icons.Default.Radio),
        Pair("Profile", Icons.Default.Person),
    )
    val shape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .shadow(
                    elevation = 22.dp,
                    shape = shape,
                    ambientColor = accentPrimary.copy(alpha = 0.55f),
                    spotColor = accentSecondary.copy(alpha = 0.55f)
                )
                .clip(shape)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.22f),
                            Color.White.copy(alpha = 0.07f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.65f),
                            Color.White.copy(alpha = 0.12f)
                        )
                    ),
                    shape = shape
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.White.copy(alpha = 0.55f))
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, (label, icon) ->
                    val isSelected = selectedIndex == index
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onTabSelected(index) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (isSelected)
                                        Brush.linearGradient(
                                            listOf(
                                                accentPrimary.copy(alpha = 0.80f),
                                                accentSecondary.copy(alpha = 0.70f)
                                            )
                                        )
                                    else Brush.linearGradient(
                                        listOf(Color.Transparent, Color.Transparent)
                                    )
                                )
                                .border(
                                    width = if (isSelected) 1.dp else 0.dp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.45f) else Color.Transparent,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSelected) Color.White else textSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = label,
                            style = tNavLabel,
                            color = if (isSelected) Color.White else textMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

// --- 2. Mini Player (liquid glass) ---
@Composable
fun MiniPlayer(
    song: Song?,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (song == null) return
    val shape = RoundedCornerShape(22.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .height(66.dp)
            .shadow(
                elevation = 18.dp,
                shape = shape,
                ambientColor = accentPrimary.copy(alpha = 0.5f),
                spotColor = accentSecondary.copy(alpha = 0.5f)
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.18f),
                        Color.White.copy(alpha = 0.06f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = shape
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onExpand() }
            ) {
                ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.fillMaxSize(), cornerRadius = 12.dp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    style = tSongTitle,
                    color = textMain,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = tArtist,
                    color = textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onPlayPause) {
                    Icon(Icons.Default.Pause, contentDescription = "Play/Pause", tint = Color.White)
                }
                IconButton(onClick = onNext) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = Color.White)
                }
            }
        }
    }
}

// --- 3. Song List Item ---
@Composable
fun SongItem(
    song: Song,
    index: Int = 0,
    onClick: () -> Unit = {},
    onFavorite: () -> Unit = {},
    showNumber: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showNumber) {
            Text(
                text = (index + 1).toString(),
                style = tArtist,
                color = textMuted,
                modifier = Modifier.width(24.dp)
            )
        }

        ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.size(40.dp), cornerRadius = 8.dp)

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                style = tSongTitle,
                color = textMain,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.artist,
                style = tArtist,
                color = textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = song.durationFormatted,
                style = tArtist,
                color = textMuted
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = onFavorite) {
                Icon(
                    imageVector = if (song.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (song.isFavorite) accentPrimary else textMuted
                )
            }
        }
    }
}

// --- 4. Album Card ---
@Composable
fun AlbumCard(
    album: Album,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                ArtworkPlaceholder(album.artworkUrl, album.title, Modifier.fillMaxSize(), cornerRadius = 16.dp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = album.title,
            style = tSongTitle,
            color = textMain,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = album.artist,
            style = tArtist,
            color = textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// --- 5. Artist Card ---
@Composable
fun ArtistCard(
    artist: Artist,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(artist.imageUrl, artist.name, Modifier.fillMaxSize(), cornerRadius = 75.dp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = artist.name,
            style = tSongTitle,
            color = textMain,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// --- 6. Playlist Card ---
@Composable
fun PlaylistCard(
    playlist: Playlist,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                ArtworkPlaceholder(playlist.artworkUrl, playlist.title, Modifier.fillMaxSize(), cornerRadius = 16.dp)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(accentPrimary, shape = CircleShape)
                        .size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = playlist.title,
            style = tSongTitle,
            color = textMain,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${playlist.songCount} songs",
            style = tArtist,
            color = textSecondary,
            maxLines = 1
        )
    }
}

// --- 7. Featured Banner ---
@Composable
fun FeaturedBanner(
    title: String,
    artworkUrl: String?,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onPlay() },
        contentAlignment = Alignment.BottomStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(artworkUrl, title, Modifier.fillMaxSize(), cornerRadius = 20.dp)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xB3000000))
                    )
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = tSection,
                    color = textMain
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onPlay,
                    colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play")
                }
            }
        }
    }
}

// --- 8. Search Bar ---
@Composable
fun SearchBar(
    placeholder: String = "Search music",
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(value) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(surfaceElevated, shape = RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = textMuted,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (text.isEmpty()) {
                    Text(text = placeholder, style = tArtist, color = textMuted)
                }
                BasicTextField(
                    value = text,
                    onValueChange = { text = it; onValueChange(it) },
                    textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = ""; onClear() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = textMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// --- 9. Filter Chips ---
@Composable
fun FilterChips(
    items: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            val isSelected = item == selected
            FilterChip(
                onClick = { onSelect(item) },
                selected = isSelected,
                label = { Text(text = item, style = tArtist, color = if (isSelected) Color.White else textSecondary) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = if (isSelected) accentPrimary else surfaceElevated
                ),
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

// --- 10. Genre Card ---
@Composable
fun GenreCard(
    genre: Genre,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(140.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(genre.artworkUrl, genre.name, Modifier.fillMaxSize(), cornerRadius = 16.dp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = genre.name,
            style = tArtist,
            color = textMain,
            maxLines = 1
        )
    }
}

// --- 11. Mood Card ---
@Composable
fun MoodCard(
    mood: Mood,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val gradientStart = Color(0xFF4CAF50)
    val gradientEnd = Color(0xFF2E7D32)

    Column(
        modifier = modifier
            .width(130.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(gradientStart, gradientEnd)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(mood.artworkUrl, mood.name, Modifier.fillMaxSize().alpha(0.6f), cornerRadius = 20.dp)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = mood.name,
            style = tArtist,
            color = textMain,
            maxLines = 1
        )
    }
}

// --- 12. Modal Bottom Sheet ---
@Composable
fun ModalBottomSheetContent(
    title: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .background(surfaceCard, shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = tSection, color = textMain)
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = textMuted)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = divider)
        Spacer(modifier = Modifier.height(16.dp))

        content()
    }
}

// --- 13. More Options Menu ---
@Composable
fun MoreOptionsMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = tArtist, color = textMain)
    }
}

// --- 14. Loading Skeleton ---
@Composable
fun LoadingSkeleton(
    width: Dp,
    height: Dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(surfaceElevated, shape = shape)
    )
}

// --- 15. Empty State ---
@Composable
fun EmptyState(
    icon: ImageVector = Icons.Default.Inbox,
    title: String = "No results found",
    subtitle: String = "Try searching for something else",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textMuted,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = title, style = tSection, color = textMain)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = subtitle, style = tArtist, color = textSecondary)
    }
}

// --- 16. Error State ---
@Composable
fun ErrorState(
    message: String = "Something went wrong",
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = accentPrimary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, style = tSongTitle, color = textMain)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// --- 17. Confirmation Dialog ---
@Composable
fun ConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "Confirm",
    message: String = "Are you sure?",
    confirmText: String = "Yes",
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Text(text = message, style = tArtist, color = textMain)
        },
        title = {
            Text(text = title, style = tSection, color = textMain)
        },
        modifier = modifier
    )
}

// --- 19. Audio Progress Slider ---
@Composable
fun AudioProgressSlider(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Slider(
        value = progress,
        onValueChange = onProgressChange,
        valueRange = 0f..1f,
        colors = SliderDefaults.colors(
            thumbColor = accentPrimary,
            activeTrackColor = accentPrimary,
            inactiveTrackColor = divider
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// --- 20. Premium Badge ---
@Composable
fun PremiumBadge(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .background(accentPrimary, shape = RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "PREMIUM",
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.5.sp
            )
        )
    }
}

// --- Divider helper ---
@Composable
fun ListDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier.padding(horizontal = 16.dp), color = divider)
}

// --- Section Header ---
@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    onSeeAll: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = title, style = tSection, color = textMain)
            subtitle?.let {
                Text(text = it, style = tArtist, color = textSecondary)
            }
        }
        onSeeAll?.let {
            TextButton(onClick = it) {
                Text(text = "See all", style = tNavLabel, color = accentPrimary)
            }
        }
    }
}
