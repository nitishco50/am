package allora.am.ui.screens.main

import allora.am.data.MockData
import allora.am.model.Genre
import allora.am.model.Song
import allora.am.ui.components.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp

// --- Screen 6: Home ---
@Composable
fun HomeScreen(
    onNavigate: (allora.am.ui.navigation.Screen) -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val songs = MockData.songs
    val albums = MockData.albums
    val playlists = MockData.playlists
    val artists = MockData.artists

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
                Column {
                    Text(text = "Good evening", style = tNavLabel, color = textSecondary)
                    Text(text = MockData.currentUser.name, style = tSection, color = textMain)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = textSecondary)
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(surfaceElevated)
                            .clickable { onNavigate(allora.am.ui.navigation.Screen.Profile) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = textSecondary, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        item { SectionHeader(title = "Made for You", onSeeAll = { onNavigate(allora.am.ui.navigation.Screen.Library) }) }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(playlists.take(4)) { playlist ->
                    PlaylistCard(playlist, onClick = { onNavigate(allora.am.ui.navigation.Screen.PlaylistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Recently Played", onSeeAll = { onNavigate(allora.am.ui.navigation.Screen.ListeningHistory) }) }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.width(300.dp))
                }
            }
        }

        item { SectionHeader(title = "Quick Picks") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(artists.take(4)) { artist ->
                    ArtistCard(artist, onClick = { onNavigate(allora.am.ui.navigation.Screen.ArtistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Trending Now") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(albums.take(4)) { album ->
                    AlbumCard(album, onClick = { onNavigate(allora.am.ui.navigation.Screen.AlbumDetails) })
                }
            }
        }

        item { SectionHeader(title = "New Releases") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(albums) { album ->
                    AlbumCard(album, onClick = { onNavigate(allora.am.ui.navigation.Screen.AlbumDetails) })
                }
            }
        }

        item { SectionHeader(title = "Popular Artists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(artists) { artist ->
                    ArtistCard(artist, onClick = { onNavigate(allora.am.ui.navigation.Screen.ArtistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Mood & Moments") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.moods) { mood ->
                    MoodCard(mood, onClick = { onNavigate(allora.am.ui.navigation.Screen.MoodMoments) })
                }
            }
        }

        item { SectionHeader(title = "Recommended Playlists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(playlists) { playlist ->
                    PlaylistCard(playlist, onClick = { onNavigate(allora.am.ui.navigation.Screen.PlaylistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Top Charts", onSeeAll = { onNavigate(allora.am.ui.navigation.Screen.Charts) }) }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.width(300.dp))
                }
            }
        }

        item { SectionHeader(title = "Continue Listening") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.width(300.dp))
                }
            }
        }
    }
}

// --- Screen 7: Explore ---
@Composable
fun ExploreScreen(
    onNavigate: (allora.am.ui.navigation.Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = MockData.genres

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
                Text(text = "Explore", style = tHeadline, color = textMain)
                IconButton(onClick = { onNavigate(allora.am.ui.navigation.Screen.Search) }) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = textSecondary)
                }
            }
        }

        item {
            SearchBar(
                placeholder = "Search music, artists, albums...",
                value = "",
                onValueChange = {},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item { SectionHeader(title = "Browse Categories") }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                categories.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { genre ->
                            GenreCard(
                                genre = genre,
                                onClick = { onNavigate(allora.am.ui.navigation.Screen.GenreDetails) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        item { SectionHeader(title = "New Releases") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.albums) { album ->
                    AlbumCard(album, onClick = { onNavigate(allora.am.ui.navigation.Screen.AlbumDetails) })
                }
            }
        }

        item { SectionHeader(title = "Trending Artists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.artists) { artist ->
                    ArtistCard(artist, onClick = { onNavigate(allora.am.ui.navigation.Screen.ArtistDetails) })
                }
            }
        }

        item { SectionHeader(title = "Featured Playlists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.playlists) { playlist ->
                    PlaylistCard(playlist, onClick = { onNavigate(allora.am.ui.navigation.Screen.PlaylistDetails) })
                }
            }
        }
    }
}

// --- Screen 8: Search ---
@Composable
fun SearchScreen(
    onSongClick: (Song) -> Unit,
    onNavigate: (allora.am.ui.navigation.Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            SearchBar(
                placeholder = "Search music",
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        item { SectionHeader(title = "Recent Searches", onSeeAll = { onNavigate(allora.am.ui.navigation.Screen.SearchResults) }) }
        items(MockData.songs.take(3)) { song ->
            SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }

        item { SectionHeader(title = "Trending Searches") }
        items(listOf("Pop Hits 2024", "Bollywood Best", "Punjabi Beats", "Lo-Fi Study")) { term ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { query = term }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = textMuted, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = term, style = tArtist, color = textMain)
            }
        }
    }
}

// --- Screen 9: Search Results ---
@Composable
fun SearchResultsScreen(
    query: String,
    onSongClick: (Song) -> Unit,
    onNavigate: (allora.am.ui.navigation.Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("All", "Songs", "Artists", "Albums", "Playlists")
    var selectedTab by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Results for \"$query\"",
                style = tSection,
                color = textMain,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(tabs) { index, tab ->
                    val isSelected = index == selectedTab
                    Column(
                        modifier = Modifier.clickable { selectedTab = index },
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

        item {
            Box(modifier = Modifier.padding(16.dp)) {
                TopResultCard(onPlay = {})
            }
        }

        item { SectionHeader(title = "Songs") }
        items(MockData.songs) { song ->
            SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun TopResultCard(onPlay: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(surfaceElevated, shape = RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(96.dp).clip(RoundedCornerShape(12.dp)).background(surfaceCard),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Album, contentDescription = null, tint = textSecondary, modifier = Modifier.size(40.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Midnight Echoes", style = tSongTitle, color = textMain, maxLines = 1)
            Text(text = "Luna Vale", style = tArtist, color = textSecondary, maxLines = 1)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onPlay,
                colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                modifier = Modifier.height(36.dp)
            ) {
                Text("Play")
            }
        }
    }
}
