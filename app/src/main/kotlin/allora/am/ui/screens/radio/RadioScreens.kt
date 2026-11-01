package allora.am.ui.screens.radio

import allora.am.data.MockData
import allora.am.model.RadioStation
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Screen 24: Radio ---
@Composable
fun RadioScreen(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Radio",
                style = tHeadline,
                color = textMain,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        item { SectionHeader(title = "Featured Stations") }
        items(MockData.radioStations) { station ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .clickable { onNavigate(Screen.RadioStationDetails) }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)).background(surfaceElevated)) {
                    ArtworkPlaceholder(station.artworkUrl, station.name, Modifier.fillMaxSize(), cornerRadius = 14.dp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = station.name, style = tSongTitle, color = textMain, maxLines = 1)
                    Text(text = station.genre ?: "Music", style = tArtist, color = textSecondary, maxLines = 1)
                }
                FilledIconButton(
                    onClick = {},
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = accentPrimary)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                }
            }
        }

        item { SectionHeader(title = "Genre Radio") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listOf("Pop", "Rock", "Hip-Hop", "Electronic", "Jazz")) { genre ->
                    Column(
                        modifier = Modifier
                            .width(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(surfaceElevated)
                            .clickable { onNavigate(Screen.GenreDetails) }
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Radio, contentDescription = null, tint = accentPrimary, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "$genre Radio", style = tArtist, color = textMain, maxLines = 1)
                    }
                }
            }
        }

        item { SectionHeader(title = "Mood Radio") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.moods) { mood ->
                    MoodCard(mood, onClick = { onNavigate(Screen.MoodMoments) })
                }
            }
        }
    }
}

// --- Screen 25: Radio Station Details ---
@Composable
fun RadioStationDetailsScreen(
    station: RadioStation = MockData.radioStations.first(),
    onClose: () -> Unit,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                Text(text = "Radio Station", style = tSection, color = textMain)
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(180.dp).clip(RoundedCornerShape(20.dp)).background(surfaceElevated)) {
                    ArtworkPlaceholder(station.artworkUrl, station.name, Modifier.fillMaxSize(), cornerRadius = 20.dp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = station.name, style = tHeadline, color = textMain)
                Text(text = station.description ?: "", style = tSongTitle, color = textSecondary)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onPlay,
                    colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                    modifier = Modifier.width(200.dp).height(48.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Play Station")
                }
            }
        }

        item { SectionHeader(title = "Recommended Songs") }
        itemsIndexed(MockData.songs.take(6)) { index, song ->
            SongItem(song, index = index, onClick = {}, modifier = Modifier.padding(horizontal = 16.dp))
        }

        item { SectionHeader(title = "Related Stations") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.radioStations) { st ->
                    Column(
                        modifier = Modifier.width(120.dp).clickable {},
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.size(120.dp).clip(RoundedCornerShape(16.dp)).background(surfaceElevated)) {
                            ArtworkPlaceholder(st.artworkUrl, st.name, Modifier.fillMaxSize(), cornerRadius = 16.dp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = st.name, style = tArtist, color = textMain, maxLines = 1)
                    }
                }
            }
        }
    }
}

// --- Screen 26: Mood & Moments ---
@Composable
fun MoodMomentsScreen(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val moods = MockData.moods

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Mood & Moments",
                style = tHeadline,
                color = textMain,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                moods.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { mood ->
                            MoodCard(
                                mood = mood,
                                onClick = { onNavigate(Screen.GenreDetails) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// --- Screen 27: Genre Details ---
@Composable
fun GenreDetailsScreen(
    onClose: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val genre = MockData.genres.first()

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
                Text(text = "Genre", style = tSection, color = textMain)
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(accentPrimary, accentSecondary))),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = genre.name, style = tHeadline, color = Color.White)
                    Text(text = "The best of ${genre.name}", style = tArtist, color = Color.White.copy(alpha = 0.8f))
                }
            }
        }

        item { SectionHeader(title = "Popular Songs") }
        itemsIndexed(MockData.songs) { index, song ->
            SongItem(song, index = index, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }

        item { SectionHeader(title = "Top Artists") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockData.artists) { artist ->
                    ArtistCard(artist, onClick = {})
                }
            }
        }

        item { SectionHeader(title = "Featured Albums") }
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

// --- Screen 28: Charts ---
@Composable
fun ChartsScreen(
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val chartNames = listOf("Global Top 50", "India Top 50", "Trending Songs", "Viral Songs", "New Music Friday")

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Charts",
                style = tHeadline,
                color = textMain,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        chartNames.forEach { chartName ->
            item { SectionHeader(title = chartName) }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(MockData.songs.take(5)) { index, song ->
                        Column(
                            modifier = Modifier.width(150.dp).clickable { onSongClick(song) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(surfaceElevated)
                            ) {
                                ArtworkPlaceholder(song.artworkUrl, song.title, Modifier.fillMaxSize(), cornerRadius = 16.dp)
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(6.dp)
                                        .background(Color.Black.copy(alpha = 0.55f), shape = RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = (index + 1).toString(),
                                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = song.title, style = tArtist, color = textMain, maxLines = 1)
                            Text(text = song.artist, style = tNavLabel, color = textSecondary, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}
