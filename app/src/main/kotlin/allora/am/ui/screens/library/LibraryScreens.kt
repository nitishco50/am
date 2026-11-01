package allora.am.ui.screens.library

import allora.am.data.MockData
import allora.am.model.Song
import allora.am.ui.components.*
import allora.am.ui.navigation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.AsyncImage

// --- Screen 15: Library ---
@Composable
fun LibraryScreen(
    onNavigate: (Screen) -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("Playlists", "Albums", "Artists", "Songs", "Downloads")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Library", style = tHeadline, color = textMain)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Search,
                        contentDescription = null,
                        tint = textSecondary
                    )
                }
                IconButton(onClick = {}) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.FilterList,
                        contentDescription = null,
                        tint = textSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalScroll(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                items(tabs) { tab ->
                    val isSelected = tab == tabs[selectedTab.value]
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { selectedTab.value = tabs.indexOf(tab) }
                    ) {
                        Text(
                            text = tab,
                            style = tArtist,
                            color = if (isSelected) accentPrimary else textSecondary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .height(2.dp)
                                    .width(24.dp)
                                    .background(accentPrimary)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recently added
        SectionHeader(title = "Recently Added")

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(5)) { song ->
                SongItem(song, onClick = { onSongClick(song) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Favorite songs
        SectionHeader(title = "Favorite Songs")

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            items(MockData.favoriteSongs) { song ->
                SongItem(song, onClick = { onSongClick(song) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // User playlists
        SectionHeader(title = "Your Playlists")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.playlists.filter { it.isCreatedByUser }) { playlist ->
                PlaylistCard(playlist, onClick = { onNavigate(Screen.PlaylistDetails) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Saved albums
        SectionHeader(title = "Saved Albums")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.albums.filter { it.isSaved }) { album ->
                AlbumCard(album, onClick = { onNavigate(Screen.AlbumDetails) })
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 16: Favorite Songs ---
@Composable
fun FavoriteSongsScreen(
    onSongClick: (Song) -> Unit,
    onPlayAll: () -> Unit,
    onShuffleAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val songs = MockData.favoriteSongs

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(accentPrimary, accentSecondary),
                        start = Alignment.TopLeft,
                        end = Alignment.BottomRight
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Liked Songs", style = tHeadline, color = Color.White)
                Text(text = "${songs.size} songs", style = tArtist, color = textSecondary.copy(alpha = 0.8f))
            }
        }

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onPlayAll,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Play", color = textMain)
            }
            Button(
                onClick = onShuffleAll,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Shuffle,
                        contentDescription = null,
                        tint = textMain,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Shuffle", color = textMain)
                }
            }
        }

        // Song list
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(songs.indexed) { (index, song) ->
                SongItem(song, index = index, onClick = { onSongClick(song) })
                if (index < songs.size - 1) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 17: Playlist Details ---
@Composable
fun PlaylistDetailsScreen(
    onClose: () -> Unit,
    onPlayAll: () -> Unit,
    onShuffleAll: () -> Unit,
    onDownload: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val playlist = MockData.playlists.first()
    val songs = MockData.songs

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 40.dp),
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
            Text(text = playlist.title, style = tSection, color = textMain)
        }

        // Playlist header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = playlist.artworkUrl,
                contentDescription = playlist.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.LibraryMusic,
                        contentDescription = null,
                        tint = textSecondary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = playlist.title, style = tHeadline, color = textMain)
                Text(text = playlist.description ?: "A great playlist", style = tArtist, color = textSecondary)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "${playlist.songCount} songs", style = tNavLabel, color = textSecondary)
                    Text(text = "•", style = tNavLabel, color = textSecondary)
                    Text(text = playlist.totalDurationMs.durationFormatted, style = tNavLabel, color = textSecondary)
                }
            }
        }

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onPlayAll,
                colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Play")
            }
            Button(
                onClick = onShuffleAll,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Shuffle,
                        contentDescription = null,
                        tint = textMain,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Shuffle", color = textMain)
                }
            }
            Button(
                onClick = onDownload,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Download,
                        contentDescription = null,
                        tint = textMain,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download", color = textMain)
                }
            }
        }

        // Song list
        SectionHeader(title = "Songs", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(songs.indexed) { (index, song) ->
                SongItem(song, index = index, onClick = { onSongClick(song) })
                if (index < songs.size - 1) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 18: Create Playlist ---
@Composable
fun CreatePlaylistScreen(
    onClose: () -> Unit,
    onCreate: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 40.dp),
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
            Text(text = "Create Playlist", style = tSection, color = textMain)
            IconButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = textSecondary
                )
            }
        }

        // Artwork placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.AddAPhoto,
                    contentDescription = null,
                    tint = textMuted,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Add playlist artwork", style = tArtist, color = textMuted)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Playlist Name", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Enter playlist name", style = tSongTitle, color = textMuted) },
                textStyle = TextStyle(color = textMain, fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                decorationBox = { inner ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterVertically
                    ) {
                        inner(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Description", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Add a description", style = tSongTitle, color = textMuted) },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth().height(96.dp),
                decorationBox = { inner ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        inner(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isPrivate,
                onCheckedChange = { isPrivate = it },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = surfaceElevated,
                    checkedColor = accentPrimary
                )
            )
            Text(text = "Private playlist", style = tArtist, color = textMain)
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCreate,
            enabled = name.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (name.isNotEmpty()) accentPrimary else divider
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp)
        ) {
            Text("Create Playlist")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 19: Edit Playlist ---
@Composable
fun EditPlaylistScreen(
    onClose: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 40.dp),
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
            Text(text = "Edit Playlist", style = tSection, color = textMain)
            TextButton(onClick = onSave) {
                Text("Save Changes", style = tArtist, color = accentPrimary)
            }
        }

        // Artwork
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = MockData.playlists.first().artworkUrl,
                contentDescription = "Playlist artwork",
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.LibraryMusic,
                        contentDescription = null,
                        tint = textSecondary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Name", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = "My Playlist",
                onValueChange = {},
                placeholder = { Text("Playlist name", style = tSongTitle, color = textMuted) },
                textStyle = TextStyle(color = textMain, fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                decorationBox = { inner ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterVertically) {
                        inner(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Description", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = "A great playlist",
                onValueChange = {},
                placeholder = { Text("Description", style = tSongTitle, color = textMuted) },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth().height(80.dp),
                decorationBox = { inner ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                        inner(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Songs", modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(5).indexed) { (index, song) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {}
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.DragHandle,
                        contentDescription = null,
                        tint = textMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
                    IconButton(onClick = {}) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.RemoveCircleOutline,
                            contentDescription = null,
                            tint = textMuted
                        )
                    }
                }
                if (index < 4) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 20: Add Songs to Playlist ---
@Composable
fun AddSongsToPlaylistScreen(
    onClose: () -> Unit,
    onAddSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 40.dp),
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
            Text(text = "Add Songs", style = tSection, color = textMain)
        }

        SearchBar(
            placeholder = "Search songs...",
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SectionHeader(title = "Recommended Songs", modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(10).indexed) { (index, song) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {}
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            uncheckedColor = surfaceElevated,
                            checkedColor = accentPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
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
                }
                if (index < 9) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 21: Album Details ---
@Composable
fun AlbumDetailsScreen(
    onClose: () -> Unit,
    onPlayAll: () -> Unit,
    onShuffleAll: () -> Unit,
    onSave: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val album = MockData.albums.first()
    val songs = MockData.songs

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 40.dp),
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
            Text(text = album.title, style = tSection, color = textMain)
        }

        // Album artwork
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = album.artworkUrl,
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
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

            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = album.title, style = tHeadline, color = textMain)
                Text(text = album.artist, style = tSongTitle, color = textSecondary)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = album.releaseYear.toString(), style = tNavLabel, color = textSecondary)
                    Text(text = "•", style = tNavLabel, color = textSecondary)
                    Text(text = album.genre ?: "Music", style = tNavLabel, color = textSecondary)
                    Text(text = "•", style = tNavLabel, color = textSecondary)
                    Text(text = "${album.trackCount} tracks", style = tNavLabel, color = textSecondary)
                }
            }
        }

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onPlayAll,
                colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Play")
            }
            Button(
                onClick = onShuffleAll,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Shuffle,
                        contentDescription = null,
                        tint = textMain,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Shuffle", color = textMain)
                }
            }
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Icon(
                        imageVector = if (album.isSaved) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = null,
                        tint = textMain,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (album.isSaved) "Saved" else "Save", color = textMain)
                }
            }
        }

        // Track listing
        SectionHeader(title = "Tracks", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(songs.take(album.trackCount).indexed) { (index, song) ->
                SongItem(song, index = index, onClick = { onSongClick(song) })
                if (index < songs.size - 1) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 22: Artist Details ---
@Composable
fun ArtistDetailsScreen(
    onClose: () -> Unit,
    onFollow: () -> Unit,
    onPlayAll: () -> Unit,
    onShuffleAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val artist = MockData.artists.first()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Artist banner
        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp).background(surfaceElevated),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = artist.imageUrl,
                contentDescription = artist.name,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Person,
                        contentDescription = null,
                        tint = textSecondary,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(surfaceElevated)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = -48.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = artist.imageUrl,
                    contentDescription = artist.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Person,
                            contentDescription = null,
                            tint = textSecondary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = artist.name, style = tHeadline, color = textMain)
            Text(text = "${artist.monthlyListeners.toString().substringBeforeLast("0000")}K monthly listeners", style = tArtist, color = textSecondary)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onFollow,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (artist.isFollowed) surfaceElevated else accentPrimary
                    ),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(if (artist.isFollowed) "Following" else "Follow")
                }
                Button(
                    onClick = onPlayAll,
                    colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                    modifier = Modifier.height(40.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = textMain,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Play", color = textMain)
                    }
                }
                Button(
                    onClick = onShuffleAll,
                    colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                    modifier = Modifier.height(40.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Shuffle,
                            contentDescription = null,
                            tint = textMain,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Shuffle", color = textMain)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Popular Songs
        SectionHeader(title = "Popular Songs", modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(5).indexed) { (index, song) ->
                SongItem(song, index = index, onClick = {})
                if (index < 4) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Albums", modifier = Modifier.padding(horizontal = 16.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.albums.take(4)) { album ->
                AlbumCard(album, onClick = {})
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 23: Downloads ---
@Composable
fun DownloadsScreen(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Downloads", style = tHeadline, color = textMain)
            Text(text = "Offline", style = tArtist, color = accentPrimary)
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Downloaded Songs")

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(5).indexed) { (index, song) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {}
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                                    imageVector = androidx.compose.material3.Icons.Default.DownloadDone,
                                    contentDescription = null,
                                    tint = accentPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = song.title, style = tSongTitle, color = textMain, maxLines = 1)
                        Text(text = song.artist, style = tArtist, color = textSecondary, maxLines = 1)
                    }
                }
                if (index < 4) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Storage")

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "2.4 GB used of 64 GB", style = tArtist, color = textMain)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = 0.38f,
                color = accentPrimary,
                trackColor = divider,
                modifier = Modifier.fillMaxWidth().height(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
