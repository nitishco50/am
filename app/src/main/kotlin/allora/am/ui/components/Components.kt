package allora.am.ui.components

import allora.am.model.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

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

val tHeadline = allora.am.ui.theme.Typography.h1
val tSection = allora.am.ui.theme.Typography.h2
val tSongTitle = allora.am.ui.theme.Typography.bodyLarge
val tArtist = allora.am.ui.theme.Typography.labelMedium
val tNavLabel = allora.am.ui.theme.Typography.labelSmall
val tPlayerTitle = allora.am.ui.theme.Typography.titleLarge

// --- Helper: Aspect Ratio Box for artwork ---
@Composable
fun AspectRatioBox(
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.aspectRatio(aspectRatio)
    ) {
        content()
    }
}

// --- Placeholder artwork ---
@Composable
fun ArtworkPlaceholder(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .background(surfaceElevated)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            imageVector = androidx.compose.material3.Icons.Default.Album,
            contentDescription = contentDescription,
            tint = textSecondary,
            modifier = Modifier.size(32.dp)
        )
    }
}

// --- 1. Bottom Navigation Bar ---
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(bgPrimary)
    ) {
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
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (isSelected) accentPrimary else textMuted,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = label,
                        style = tNavLabel,
                        color = if (isSelected) accentPrimary else textMuted,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// --- 2. Mini Player ---
@Composable
fun MiniPlayer(
    song: Song?,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (song == null) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(surfaceElevated)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album art
            Clip(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                modifier = Modifier.size(48.dp).clickable { onExpand() }
            ) {
                ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.fillMaxSize(), cornerRadius = 0.dp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Song info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    style = tSongTitle,
                    color = textMain,
                    maxLines = 1,
                    overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = tArtist,
                    color = textSecondary,
                    maxLines = 1,
                    overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onPlayPause) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = null,
                        tint = textMain
                    )
                }
                IconButton(onClick = onNext) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = null,
                        tint = textMain
                    )
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
                overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
            )
            Text(
                text = song.artist,
                style = tArtist,
                color = textSecondary,
                maxLines = 1,
                overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
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
                androidx.compose.material3.Icon(
                    imageVector = if (song.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
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
        AspectRatioBox(aspectRatio = 1f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                    .background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                ArtworkPlaceholder(album.artworkUrl, album.title, Modifier.fillMaxSize(), cornerRadius = 0.dp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = album.title,
            style = tSongTitle,
            color = textMain,
            maxLines = 1,
            overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
        )
        Text(
            text = album.artist,
            style = tArtist,
            color = textSecondary,
            maxLines = 1,
            overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
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
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(artist.imageUrl, artist.name, Modifier.fillMaxSize(), cornerRadius = 0.dp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = artist.name,
            style = tSongTitle,
            color = textMain,
            maxLines = 1,
            overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
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
        AspectRatioBox(aspectRatio = 1f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                    .background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                ArtworkPlaceholder(playlist.artworkUrl, playlist.title, Modifier.fillMaxSize(), cornerRadius = 0.dp)
                // Play overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                        .background(accentPrimary, shape = androidx.compose.foundation.shape.CircleShape)
                        .size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
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
            overflow = androidx.compose.foundation.layout.Overflow.Ellipsis
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
    val gradient = Brush.linearGradient(
        colors = listOf(Color.Transparent, Color(0x80000000)),
        start = Alignment.TopCenter,
        end = Alignment.BottomCenter
    )

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
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(artworkUrl, title, Modifier.fillMaxSize(), cornerRadius = 0.dp)
        }

        Box(
            modifier = Modifier.fillMaxSize().clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.BottomStart,
            background = gradient
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(40.dp))
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

    BasicTextField(
        value = text,
        onValueChange = { text = it; onValueChange(it) },
        textStyle = TextStyle(color = textMain, fontSize = 16.sp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(surfaceElevated, shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp),
        decorationBox = { inner ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = textMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    inner(
                        modifier = Modifier.weight(1f)
                    )
                    if (text.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { text = ""; onClear() }) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = textMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    )
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
                colors = FilterChipDefaults.colors(
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
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(genre.artworkUrl, genre.name, Modifier.fillMaxSize(), cornerRadius = 0.dp)
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
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(gradientStart, gradientEnd),
                        start = Alignment.TopLeft,
                        end = Alignment.BottomRight
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            ArtworkPlaceholder(mood.artworkUrl, mood.name, Modifier.fillMaxSize().alpha(0.6f), cornerRadius = 0.dp)
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
            .background(surfaceCard, shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = tSection, color = textMain)
            IconButton(onClick = onClose) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = textMuted
                )
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
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
        androidx.compose.material3.Icon(
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
    shape: androidx.compose.foundation.shape.Shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
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
    icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Default.Inbox,
    title: String = "No results found",
    subtitle: String = "Try searching for something else",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.Icon(
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
        androidx.compose.material3.Icon(
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
        modifier = modifier
    ) {
        Text(text = title, style = tSection, color = textMain)
    }
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
        onValueChangeFinished = {},
        colors = SliderDefaults.colors(
            activeTrackColor = accentPrimary,
            inactiveTrackColor = divider,
            thumbColor = accentPrimary
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
            .background(accentPrimary, shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)),
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

// --- Duration helper ---
fun Long.durationFormatted: String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "$minutes:${remainingSeconds.toString().padStart(2, '0')}"
}
