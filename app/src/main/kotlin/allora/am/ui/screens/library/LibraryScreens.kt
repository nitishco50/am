package allora.am.ui.screens.library

import allora.am.data.MockData
import allora.am.model.Playlist
import allora.am.model.Song
import allora.am.ui.components.*
import allora.am.ui.navigation.Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun TabsRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(tabs) { index, tab ->
            val isSelected = index == selectedIndex
            Column(
                modifier = Modifier.clickable { onSelect(index) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tab,
                    style = tArtist,
                    color = if (isSelected) accentPrimary else textSecondary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(24.dp)
                        .background(if (isSelected) accentPrimary else Color.Transparent)
                )
            }
        }
    }
}

// --- Screen 15: Library ---
@Composable
fun LibraryScreen(
    onNavigate: (Screen) -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("Playlists", "Albums", "Artists", "Songs", "Downloads")
    var selectedTab by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Library", style = tHeadline, color = textMain)
                Row {
                    IconButton(onClick = { onNavigate(Screen.Search) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = textSecondary)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.FilterList, contentDescription = "Sort", tint = textSecondary)
                    }
                }
            }
        }

        item { TabsRow(tabs = tabs, selectedIndex = selectedTab, onSelect = { selectedTab = it }) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onNavigate(Screen.CreatePlaylist) },
                    colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = textMain, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Create", color = textMain)
                }
                Button(
                    onClick = { onNavigate(Screen.FavoriteSongs) },
                    colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Liked")
                }
            }
        }

        item { SectionHeader(title = "Your Playlists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.playlists) { playlist ->
                    PlaylistCard(playlist, onClick = { onNavigate(Screen.PlaylistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Saved Albums") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.albums) { album ->
                    AlbumCard(album, onClick = { onNavigate(Screen.AlbumDetails) })
                }
            }
        }

        item { SectionHeader(title = "Followed Artists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.artists) { artist ->
                    ArtistCard(artist, onClick = { onNavigate(Screen.ArtistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Recently Added") }
        items(MockData.songs.take(5)) { song ->
            SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }
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

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(accentPrimary, accentSecondary)))
                    .padding(vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(56.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Liked Songs", style = tHeadline, color = Color.White)
                Text(text = "${songs.size} songs", style = tArtist, color = Color.White.copy(alpha = 0.8f))
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onPlayAll,
                    colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Play")
                }
                Button(
                    onClick = onShuffleAll,
                    colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = null, tint = textMain, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Shuffle", color = textMain)
                }
            }
        }

        itemsIndexed(songs) { index, song ->
            SongItem(song, index = index, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

// --- Screen 17: Playlist Details ---
@Composable
fun PlaylistDetailsScreen(
    playlist: Playlist = MockData.playlists.first(),
    onClose: () -> Unit,
    onPlayAll: () -> Unit,
    onShuffleAll: () -> Unit,
    onDownload: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val songs = MockData.songs

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = textMain)
                }
                Text(text = "Playlist", style = tSection, color = textMain)
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(120.dp).clip(RoundedCornerShape(16.dp)).background(surfaceElevated)) {
                    ArtworkPlaceholder(playlist.artworkUrl, playlist.title, Modifier.fillMaxSize(), cornerRadius = 16.dp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = playlist.title, style = tSection, color = textMain, maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = playlist.description ?: "", style = tArtist, color = textSecondary, maxLines = 2)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${playlist.songCount} songs • ${playlist.totalDurationMs.durationFormatted}",
                        style = tNavLabel,
                        color = textMuted
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledIconButton(
                    onClick = onPlayAll,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = accentPrimary)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                }
                FilledIconButton(
                    onClick = onShuffleAll,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = surfaceElevated)
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = "Shuffle", tint = textMain)
                }
                FilledIconButton(
                    onClick = onDownload,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = surfaceElevated)
                ) {
                    Icon(Icons.Default.Download, contentDescription = "Download", tint = textMain)
                }
            }
        }

        item { SectionHeader(title = "Songs") }
        itemsIndexed(songs) { index, song ->
            SongItem(song, index = index, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }
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
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = textMain)
            }
            Text(text = "Create Playlist", style = tSection, color = textMain)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(surfaceElevated)
                .clickable {},
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.AddAPhoto, contentDescription = null, tint = textMuted, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Add playlist artwork", style = tArtist, color = textMuted)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Playlist Name", style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (name.isEmpty()) Text("Enter playlist name", style = tSongTitle, color = textMuted)
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Description", style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            if (description.isEmpty()) Text("Add a description", style = tSongTitle, color = textMuted)
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isPrivate,
                onCheckedChange = { isPrivate = it },
                colors = SwitchDefaults.colors(checkedTrackColor = accentPrimary)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Private playlist", style = tArtist, color = textMain)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onCreate,
            enabled = name.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (name.isNotEmpty()) accentPrimary else divider
            ),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Create Playlist")
        }
    }
}

// --- Screen 19: Edit Playlist ---
@Composable
fun EditPlaylistScreen(
    onClose: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("My Playlist") }
    var description by remember { mutableStateOf("A great playlist") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = textMain)
            }
            Text(text = "Edit Playlist", style = tSection, color = textMain)
            TextButton(onClick = onSave) {
                Text("Save", style = tArtist, color = accentPrimary)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(surfaceElevated)
        ) {
            ArtworkPlaceholder(MockData.playlists.first().artworkUrl, "Artwork", Modifier.fillMaxSize(), cornerRadius = 20.dp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Name", style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Description", style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Songs")

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(MockData.songs.take(5)) { index, song ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.DragHandle, contentDescription = null, tint = textMuted, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.size(40.dp), cornerRadius = 8.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = song.title, style = tSongTitle, color = textMain, maxLines = 1)
                        Text(text = song.artist, style = tArtist, color = textSecondary, maxLines = 1)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Remove", tint = textMuted)
                    }
                }
            }
        }
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
    val selected = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = textMain)
            }
            Text(text = "Add Songs", style = tSection, color = textMain)
            TextButton(onClick = onAddSelected) {
                Text("Add", style = tArtist, color = accentPrimary)
            }
        }

        SearchBar(
            placeholder = "Search songs...",
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(MockData.songs) { song ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clickable {
                            if (song.id in selected) selected.remove(song.id) else selected.add(song.id)
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = song.id in selected,
                        onCheckedChange = {
                            if (song.id in selected) selected.remove(song.id) else selected.add(song.id)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = accentPrimary,
                            checkmarkColor = Color.White,
                            uncheckedColor = textMuted
                        )
                    )
                    ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.size(40.dp), cornerRadius = 8.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = song.title, style = tSongTitle, color = textMain, maxLines = 1)
                        Text(text = song.artist, style = tArtist, color = textSecondary, maxLines = 1)
                    }
                }
            }
        }
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

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = textMain)
                }
                Text(text = "Album", style = tSection, color = textMain)
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(200.dp).clip(RoundedCornerShape(20.dp)).background(surfaceElevated)) {
                    ArtworkPlaceholder(album.artworkUrl, album.title, Modifier.fillMaxSize(), cornerRadius = 20.dp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = album.title, style = tHeadline, color = textMain, textAlign = TextAlign.Center)
                Text(text = album.artist, style = tSongTitle, color = textSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${album.releaseYear} • ${album.genre ?: "Music"} • ${album.trackCount} tracks",
                    style = tNavLabel,
                    color = textMuted
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledIconButton(
                    onClick = onPlayAll,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = accentPrimary)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                }
                FilledIconButton(
                    onClick = onShuffleAll,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = surfaceElevated)
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = "Shuffle", tint = textMain)
                }
                FilledIconButton(
                    onClick = onSave,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = surfaceElevated)
                ) {
                    Icon(
                        imageVector = if (album.isSaved) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = "Save",
                        tint = textMain
                    )
                }
            }
        }

        item { SectionHeader(title = "Tracks") }
        itemsIndexed(songs.take(album.trackCount)) { index, song ->
            SongItem(song, index = index, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

// --- Screen 22: Artist Details ---
@Composable
fun ArtistDetailsScreen(
    onClose: () -> Unit,
    onFollow: () -> Unit,
    onPlayAll: () -> Unit,
    onShuffleAll: () -> Unit,
    onSongClick: (Song) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val artist = MockData.artists.first()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(surfaceElevated)
            ) {
                ArtworkPlaceholder(artist.imageUrl, artist.name, Modifier.fillMaxSize(), cornerRadius = 0.dp)
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.TopStart).padding(top = 40.dp, start = 8.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = textMain)
                }
            }
        }

        item {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = artist.name, style = tHeadline, color = textMain)
                Text(
                    text = "${artist.monthlyListeners / 1000}K monthly listeners",
                    style = tArtist,
                    color = textSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = textMain, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Play", color = textMain)
                    }
                    Button(
                        onClick = onShuffleAll,
                        colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Icon(Icons.Default.Shuffle, contentDescription = null, tint = textMain, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        item { SectionHeader(title = "Popular Songs") }
        itemsIndexed(MockData.songs.take(5)) { index, song ->
            SongItem(song, index = index, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }

        item { SectionHeader(title = "Albums") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.albums) { album ->
                    AlbumCard(album, onClick = {})
                }
            }
        }
    }
}

// --- Screen 23: Downloads ---
@Composable
fun DownloadsScreen(
    onNavigate: (Screen) -> Unit,
    onSongClick: (Song) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Downloads", style = tHeadline, color = textMain)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudOff, contentDescription = null, tint = success, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Offline", style = tArtist, color = success)
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Storage used", style = tArtist, color = textSecondary)
                    Text(text = "2.4 GB / 64 GB", style = tArtist, color = textMain)
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.38f },
                    color = accentPrimary,
                    trackColor = divider,
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                )
            }
        }

        item { SectionHeader(title = "Downloaded Songs") }
        itemsIndexed(MockData.songs.take(6)) { index, song ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { onSongClick(song) }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.size(40.dp), cornerRadius = 8.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = song.title, style = tSongTitle, color = textMain, maxLines = 1)
                    Text(text = song.artist, style = tArtist, color = textSecondary, maxLines = 1)
                }
                Icon(Icons.Default.DownloadDone, contentDescription = "Downloaded", tint = accentPrimary, modifier = Modifier.size(20.dp))
            }
        }
    }
}
