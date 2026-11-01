package allora.am.ui.screens.onboarding

import allora.am.R
import allora.am.model.Song
import allora.am.ui.components.*
import allora.am.ui.navigation.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import allora.am.ui.theme.BackgroundPrimary
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.AsyncImage

// --- Screen 1: Splash Screen ---
@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationStarted = true
        delay(2500)
        onFinish()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = animationStarted,
            enter = fadeIn(animationSpec = androidx.compose.animation.tween(1000))
        ) {
            Box(
                modifier = Modifier.size(80.dp).background(Color(0xFFA020F0), shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.MusicNote,
                    contentDescription = "App Logo",
                    tint = Color.White
                )
            }
        }

        AnimatedVisibility(
            visible = animationStarted,
            enter = fadeIn(animationSpec = androidx.compose.animation.tween(1000, delayMillis = 300))
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color(0xFF15151B))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp)
                ) {
                    repeat(5) { i ->
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(if (i % 2 == 0) 16.dp else 8.dp)
                                .background(accentPrimary)
                        )
                    }
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
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero artwork
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(accentPrimary, accentSecondary),
                        start = Alignment.TopLeft,
                        end = Alignment.BottomRight
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material3.Icons.Default.LibraryMusic,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(80.dp)
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
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = androidx.compose.ui.text.TextAlign.Center
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
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = textMain,
                borderColor = divider
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp)
        ) {
            Text("Continue as Guest")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(text = "Terms of Service", style = tNavLabel, color = textMuted)
            Text(text = "  •  ", style = tNavLabel, color = textMuted)
            Text(text = "Privacy Policy", style = tNavLabel, color = textMuted)
        }
    }
}

// --- Screen 3: Music Preferences ---
@Composable
fun MusicPreferencesScreen(
    selectedGenres: MutableSet<String>,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = allora.am.data.MockData.genres

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "What do you love listening to?",
            style = tHeadline,
            color = textMain,
            modifier = Modifier.padding(horizontal = 24.dp, top = 40.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Select your favorite genres to personalize your experience.",
            style = tArtist,
            color = textSecondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(genres) { genre ->
                val isSelected = genre.name in selectedGenres
                Column(
                    modifier = Modifier
                        .width((LocalConfiguration.current.screenWidthDp - 48) / 2)
                        .clickable {
                            if (isSelected) selectedGenres.remove(genre.name) else selectedGenres.add(genre.name)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(surfaceElevated)
                    ) {
                        AsyncImage(
                            model = genre.artworkUrl,
                            contentDescription = genre.name,
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

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(accentPrimary, shape = CircleShape)
                                    .size(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material3.Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = genre.name,
                        style = tArtist,
                        color = if (isSelected) accentPrimary else textMain,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinue,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedGenres.isNotEmpty()) accentPrimary else divider
            ),
            enabled = selectedGenres.isNotEmpty(),
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

// --- Screen 4: Select Favorite Artists ---
@Composable
fun SelectFavoriteArtistsScreen(
    selectedArtists: MutableSet<String>,
    onSkip: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val artists = allora.am.data.MockData.artists
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Select your favorite artists",
            style = tSection,
            color = textMain,
            modifier = Modifier.padding(horizontal = 24.dp, top = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchBar(
            placeholder = "Search artists...",
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filteredArtists = artists.filter { it.name.contains(searchQuery, ignoreCase = true) }

        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredArtists) { artist ->
                val isSelected = artist.id in selectedArtists
                Column(
                    modifier = Modifier
                        .width((LocalConfiguration.current.screenWidthDp - 48) / 2)
                        .clickable {
                            if (isSelected) selectedArtists.remove(artist.id) else selectedArtists.add(artist.id)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(surfaceElevated),
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
                                    tint = textSecondary
                                )
                            }
                        }

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp)
                                    .background(accentPrimary, shape = CircleShape)
                                    .size(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material3.Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = artist.name,
                        style = tArtist,
                        color = textMain,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onSkip) {
                Text("Skip", style = tArtist, color = textSecondary)
            }

            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedArtists.isNotEmpty()) accentPrimary else divider
                ),
                enabled = selectedArtists.isNotEmpty(),
                modifier = Modifier.height(48.dp)
            ) {
                Text("Continue")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
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
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(120.dp).background(surfaceElevated, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material3.Icons.Default.NotificationsActive,
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
            textAlign = androidx.compose.ui.text.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Get notified about new releases, playlist updates, and personalized recommendations.",
            style = tSongTitle,
            color = textSecondary,
            textAlign = androidx.compose.ui.text.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onEnable,
            colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(52.dp)
        ) {
            Text("Enable Notifications")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onNotNow) {
            Text("Not Now", style = tArtist, color = textSecondary)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
