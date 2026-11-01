package allora.am.ui.screens.main

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

// --- Screen 6: Home ---
@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val songs = MockData.songs
    val albums = MockData.albums
    val playlists = MockData.playlists
    val artists = MockData.artists

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Good evening,",
                    style = tSection,
                    color = textMain
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Notifications,
                            contentDescription = null,
                            tint = textSecondary
                        )
                    }
                    Clip(
                        shape = androidx.compose.foundation.shape.CircleShape,
                        modifier = Modifier.size(32.dp).clickable { onNavigate(Screen.Profile) }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(surfaceElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = androidx.compose.material3.Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = textSecondary
                            )
                        }
                    }
                }
            }
        }

        // Made for You
        item {
            SectionHeader(
                title = "Made for You",
                onSeeAll = { onNavigate(Screen.Library) }
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(playlists.take(4)) { playlist ->
                    PlaylistCard(playlist, onClick = { onNavigate(Screen.PlaylistDetails) })
                }
            }
        }

        // Recently Played
        item {
            SectionHeader(
                title = "Recently Played",
                onSeeAll = { onNavigate(Screen.ListeningHistory) }
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) })
                }
            }
        }

        // Quick Picks
        item {
            SectionHeader(title = "Quick Picks")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(artists.take(4)) { artist ->
                    ArtistCard(artist, onClick = { onNavigate(Screen.ArtistDetails) })
                }
            }
        }

        // Trending Now
        item {
            SectionHeader(title = "Trending Now")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) })
                }
            }
        }

        // New Releases
        item {
            SectionHeader(title = "New Releases")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(albums.take(4)) { album ->
                    AlbumCard(album, onClick = { onNavigate(Screen.AlbumDetails) })
                }
            }
        }

        // Popular Artists
        item {
            SectionHeader(title = "Popular Artists")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(artists.take(4)) { artist ->
                    ArtistCard(artist, onClick = { onNavigate(Screen.ArtistDetails) })
                }
            }
        }

        // Mood & Moments
        item {
            SectionHeader(title = "Mood & Moments")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.moods.take(4)) { mood ->
                    MoodCard(mood, onClick = { onNavigate(Screen.MoodMoments) })
                }
            }
        }

        // Recommended Playlists
        item {
            SectionHeader(title = "Recommended Playlists", onSeeAll = { onNavigate(Screen.Library) })
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(playlists.take(4)) { playlist ->
                    PlaylistCard(playlist, onClick = { onNavigate(Screen.PlaylistDetails) })
                }
            }
        }

        // Top Charts
        item {
            SectionHeader(title = "Top Charts")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) })
                }
            }
        }

        // Continue Listening
        item {
            SectionHeader(title = "Continue Listening")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(songs.take(4)) { song ->
                    SongItem(song, onClick = { onSongClick(song) })
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 7: Explore ---
@Composable
fun ExploreScreen(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = MockData.genres.take(11)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Explore", style = tHeadline, color = textMain)
            IconButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Search,
                    contentDescription = null,
                    tint = textSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            placeholder = "Search music, artists, albums...",
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Browse Categories")

        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { genre ->
                GenreCard(genre, onClick = { onNavigate(Screen.GenreDetails) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Popular Genres")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { genre ->
                GenreCard(genre, onClick = { onNavigate(Screen.GenreDetails) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "New Releases")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.albums.take(4)) { album ->
                AlbumCard(album, onClick = { onNavigate(Screen.AlbumDetails) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Trending Artists")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.artists.take(4)) { artist ->
                ArtistCard(artist, onClick = { onNavigate(Screen.ArtistDetails) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Featured Playlists")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.playlists.take(4)) { playlist ->
                PlaylistCard(playlist, onClick = { onNavigate(Screen.PlaylistDetails) })
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 8: Search ---
@Composable
fun SearchScreen(
    onSongClick: (Song) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        SearchBar(
            placeholder = "Search music",
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Recent Searches", onSeeAll = { onNavigate(Screen.SearchResults) })

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(3)) { song ->
                SongItem(song, onClick = { onSongClick(song) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Trending Searches")

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(listOf("Pop Hits 2024", "Bollywood Best", "Punjabi Beats", "Lo-Fi Study")) { term ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable { query = term },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = textMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = term, style = tArtist, color = textMain)
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 9: Search Results ---
@Composable
fun SearchResultsScreen(
    query: String,
    onSongClick: (Song) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("All", "Songs", "Artists", "Albums", "Playlists")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
    ) {
        Text(
            text = query,
            style = tSection,
            color = textMain,
            modifier = Modifier.padding(horizontal = 16.dp, top = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalScroll(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
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

        // Top result card
        item {
            TopResultCard(onNavigate = onNavigate)
        }

        // Results list
        item {
            SectionHeader(title = "Songs", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }

        item {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
            ) {
                items(MockData.songs) { song ->
                    SongItem(song, onClick = { onSongClick(song) })
                }
            }
        }
    }
}

@Composable
private fun TopResultCard(
    onNavigate: (Screen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp)
            .background(surfaceElevated, shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(100.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(surfaceCard),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Album,
                    contentDescription = null,
                    tint = textSecondary,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Midnight Echoes", style = tSongTitle, color = textMain)
                Text(text = "Luna Vale", style = tArtist, color = textSecondary)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = accentPrimary)) {
                Text("Play")
            }
        }
    }
}
