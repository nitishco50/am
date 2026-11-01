package allora.am.ui.screens.radio

import allora.am.data.MockData
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

// --- Screen 24: Radio ---
@Composable
fun RadioScreen(
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
            Text(text = "Radio", style = tHeadline, color = textMain)
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Featured Stations")

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            items(MockData.radioStations.take(3)) { station ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable { onNavigate(Screen.RadioStationDetails) }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)).background(surfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = station.artworkUrl,
                            contentDescription = station.name,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material3.Icons.Default.Radio,
                                    contentDescription = null,
                                    tint = textSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = station.name, style = tSongTitle, color = textMain)
                        Text(text = station.genre ?: "Music", style = tArtist, color = textSecondary)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Play", style = TextStyle(fontSize = 14.sp))
                    }
                }
                ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Genre Radio")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listOf("Pop Radio", "Rock Radio", "Hip-Hop Radio", "Electronic Radio", "Jazz Radio")) { genre ->
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .clickable { onNavigate(Screen.GenreDetails) }
                        .background(surfaceElevated, shape = RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Radio,
                            contentDescription = null,
                            tint = accentPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = genre, style = tArtist, color = textMain, maxLines = 1)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Mood Radio")

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.moods.take(5)) { mood ->
                MoodCard(mood, onClick = { onNavigate(Screen.MoodMoments) })
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 25: Radio Station Details ---
@Composable
fun RadioStationDetailsScreen(
    onClose: () -> Unit,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val station = MockData.radioStations.first()

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
            Text(text = station.name, style = tSection, color = textMain)
        }

        // Station artwork
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = station.artworkUrl,
                contentDescription = station.name,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.Icons.Default.Radio,
                        contentDescription = null,
                        tint = textSecondary,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = station.name, style = tHeadline, color = textMain)
            Text(text = station.description ?: "Great music, all day long", style = tSongTitle, color = textSecondary)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onPlay,
            colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
            modifier = Modifier.fillMaxWidth(0.6f).height(52.dp)
        ) {
            Text("Play Station")
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionHeader(title = "Recommended Songs", modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(6).indexed) { (index, song) ->
                SongItem(song, index = index, onClick = {})
                if (index < 5) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Related Stations", modifier = Modifier.padding(horizontal = 16.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.radioStations.drop(1)) { station ->
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .clickable {}
                        .background(surfaceElevated, shape = RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = station.artworkUrl,
                            contentDescription = station.name,
                            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp))
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material3.Icons.Default.Radio,
                                    contentDescription = null,
                                    tint = textSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = station.name, style = tArtist, color = textMain, maxLines = 1)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 26: Mood & Moments ---
@Composable
fun MoodMomentsScreen(
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
            Text(text = "Mood & Moments", style = tHeadline, color = textMain)
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(MockData.moods) { mood ->
                MoodCard(mood, onClick = { onNavigate(Screen.GenreDetails) })
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- Screen 27: Genre Details ---
@Composable
fun GenreDetailsScreen(
    onClose: () -> Unit,
    onSongClick: (alora.am.model.Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val genre = MockData.genres.first()

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
            Text(text = genre.name, style = tSection, color = textMain)
        }

        // Genre banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(accentPrimary, accentSecondary),
                        start = Alignment.TopLeft,
                        end = Alignment.BottomRight
                    ),
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = genre.name, style = tHeadline, color = Color.White)
                Text(text = "Discover the best of ${genre.name.toLowerCase()} music", style = tArtist, color = textSecondary.copy(alpha = 0.8f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Popular Songs", modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(MockData.songs.take(8).indexed) { (index, song) ->
                SongItem(song, index = index, onClick = { onSongClick(song) })
                if (index < 7) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Top Artists", modifier = Modifier.padding(horizontal = 16.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.artists.take(4)) { artist ->
                ArtistCard(artist, onClick = {})
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Featured Albums", modifier = Modifier.padding(horizontal = 16.dp))

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

// --- Screen 28: Charts ---
@Composable
fun ChartsScreen(
    onSongClick: (alora.am.model.Song) -> Unit,
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
            Text(text = "Charts", style = tHeadline, color = textMain)
        }

        Spacer(modifier = Modifier.height(24.dp))

        listOf("Global Top 50", "India Top 50", "Trending Songs", "Viral Songs", "New Music Friday").forEachIndexed { index, chartName ->
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text(text = chartName, style = tSection, color = textMain)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(MockData.songs.take(5).indexed) { (songIndex, song) ->
                        Column(
                            modifier = Modifier
                                .width(150.dp)
                                .clickable { onSongClick(song) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(surfaceElevated)
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
                                            tint = textSecondary
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(6.dp)
                                        .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                                ) {
                                    Text(
                                        text = (songIndex + 1).toString(),
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

                if (index < 4) Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
