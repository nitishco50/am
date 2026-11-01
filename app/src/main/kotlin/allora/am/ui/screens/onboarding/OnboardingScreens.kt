package allora.am.ui.screens.onboarding

import allora.am.data.MockData
import allora.am.model.Genre
import allora.am.ui.components.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

// --- Screen 1: Splash Screen ---
@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationStarted = true
        delay(2200)
        onFinish()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = animationStarted,
            enter = fadeIn(animationSpec = tween(1000))
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(accentPrimary, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "App Logo",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = animationStarted,
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 400))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { i ->
                    val h = if (i % 2 == 0) 24.dp else 14.dp
                    Box(
                        modifier = Modifier
                            .width(5.dp)
                            .height(h)
                            .background(accentPrimary, shape = RoundedCornerShape(3.dp))
                    )
                }
            }
        }
    }
}

// --- Screen 2: Welcome Screen ---
@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onContinueAsGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(accentPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LibraryMusic,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(96.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Welcome to Orra",
            style = tHeadline,
            color = textMain,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Discover, play, and enjoy your favorite music anytime, anywhere.",
            style = tSongTitle,
            color = textSecondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onGetStarted,
            colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp)
        ) {
            Text("Get Started")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onContinueAsGuest,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = textMain),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp)
        ) {
            Text("Continue as Guest")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Terms of Service", style = tNavLabel, color = textMuted)
            Text(text = "  •  ", style = tNavLabel, color = textMuted)
            Text(text = "Privacy Policy", style = tNavLabel, color = textMuted)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- Screen 3: Music Preferences ---
@Composable
fun MusicPreferencesScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = MockData.genres
    val selected = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "What do you love listening to?",
            style = tHeadline,
            color = textMain,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
        )

        Text(
            text = "Select your favorite genres to personalize your experience.",
            style = tArtist,
            color = textSecondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        genres.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { genre ->
                    GenreSelectCard(
                        genre = genre,
                        selected = genre.name in selected,
                        onToggle = {
                            if (genre.name in selected) selected.remove(genre.name)
                            else selected.add(genre.name)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinue,
            enabled = selected.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected.isNotEmpty()) accentPrimary else divider
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun GenreSelectCard(
    genre: Genre,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.clickable { onToggle() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.3f)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceElevated)
        ) {
            ArtworkPlaceholder(genre.artworkUrl, genre.name, Modifier.fillMaxSize(), cornerRadius = 16.dp)
            if (selected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(accentPrimary, shape = CircleShape)
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = genre.name,
            style = tArtist,
            color = if (selected) accentPrimary else textMain,
            maxLines = 1
        )
    }
}

// --- Screen 4: Select Favorite Artists ---
@Composable
fun SelectFavoriteArtistsScreen(
    onSkip: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val artists = MockData.artists
    var searchQuery by remember { mutableStateOf("") }
    val selected = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Select your favorite artists",
            style = tSection,
            color = textMain,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
        )

        SearchBar(
            placeholder = "Search artists...",
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filtered = artists.filter { it.name.contains(searchQuery, ignoreCase = true) }

        filtered.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { artist ->
                    ArtistSelectCard(
                        name = artist.name,
                        selected = artist.id in selected,
                        onToggle = {
                            if (artist.id in selected) selected.remove(artist.id)
                            else selected.add(artist.id)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onSkip) {
                Text("Skip", style = tArtist, color = textSecondary)
            }
            Button(
                onClick = onContinue,
                enabled = selected.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected.isNotEmpty()) accentPrimary else divider
                ),
                modifier = Modifier.height(48.dp)
            ) {
                Text("Continue")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ArtistSelectCard(
    name: String,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onToggle() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = name, tint = textSecondary, modifier = Modifier.size(48.dp))
            if (selected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(accentPrimary, shape = CircleShape)
                        .size(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = name, style = tArtist, color = textMain, maxLines = 1)
    }
}

// --- Screen 5: Notification Permission ---
@Composable
fun NotificationPermissionScreen(
    onEnable: () -> Unit,
    onNotNow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgPrimary)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(120.dp).background(surfaceElevated, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = null,
                tint = accentPrimary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Never Miss Your Music",
            style = tHeadline,
            color = textMain,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Get notified about new releases, playlist updates, and personalized recommendations.",
            style = tSongTitle,
            color = textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onEnable,
            colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
            modifier = Modifier.fillMaxWidth(0.8f).height(52.dp)
        ) {
            Text("Enable Notifications")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onNotNow) {
            Text("Not Now", style = tArtist, color = textSecondary)
        }
    }
}
