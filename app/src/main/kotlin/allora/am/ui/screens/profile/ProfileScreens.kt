package allora.am.ui.screens.profile

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

// --- Screen 29: Profile ---
@Composable
fun ProfileScreen(
    onNavigate: (Screen) -> Unit,
    onEditProfile: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val user = MockData.currentUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        // Profile header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(100.dp).clip(CircleShape).background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = user.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Person,
                            contentDescription = null,
                            tint = textSecondary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = user.name, style = tHeadline, color = textMain)
            Text(text = "@${user.username}", style = tArtist, color = textSecondary)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onEditProfile,
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.height(40.dp)
            ) {
                Text("Edit Profile", color = textMain)
            }
        }

        // Stats
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard("124", "Playlists")
            StatCard("8.5K", "Minutes Listened")
            StatCard("42", "Artists Followed")
        }

        // Recently played
        SectionHeader(title = "Recently Played", onSeeAll = { onNavigate(Screen.ListeningHistory) })

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            items(MockData.recentSongs.indexed) { (index, song) ->
                SongItem(song, index = index, onClick = { onSongClick(song) })
                if (index < MockData.recentSongs.size - 1) ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Favorite artists
        SectionHeader(title = "Favorite Artists", onSeeAll = { onNavigate(Screen.Library) })

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.artists.take(4)) { artist ->
                ArtistCard(artist, onClick = { onNavigate(Screen.ArtistDetails) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Public playlists
        SectionHeader(title = "Public Playlists", onSeeAll = { onNavigate(Screen.Library) })

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.playlists.take(3)) { playlist ->
                PlaylistCard(playlist, onClick = { onNavigate(Screen.PlaylistDetails) })
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun StatCard(value: String, label: String) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(16.dp)
            .background(surfaceElevated, shape = RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, style = tSection, color = textMain)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = tArtist, color = textSecondary)
    }
}

// --- Screen 30: Edit Profile ---
@Composable
fun EditProfileScreen(
    onClose: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Alex Rivera") }
    var username by remember { mutableStateOf("alex_rivera") }
    var bio by remember { mutableStateOf("Music lover & explorer") }

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
            Text(text = "Edit Profile", style = tSection, color = textMain)
            TextButton(onClick = onSave) {
                Text("Save", style = tArtist, color = accentPrimary)
            }
        }

        // Profile image
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(100.dp).clip(CircleShape).background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = MockData.currentUser.avatarUrl,
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material3.Icons.Default.Person,
                            contentDescription = null,
                            tint = textSecondary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = {}) {
                Text("Change photo", style = tArtist, color = textSecondary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Name", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = name,
                onValueChange = { name = it },
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
            Text(text = "Username", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = username,
                onValueChange = { username = it },
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
            Text(text = "Bio", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = bio,
                onValueChange = { bio = it },
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth().height(96.dp),
                decorationBox = { inner ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                        inner(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 31: Settings ---
@Composable
fun SettingsScreen(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Account section
            item {
                SectionHeader(title = "Account", modifier = Modifier.padding(horizontal = 16.dp, top = 16.dp, bottom = 8.dp))
            }
            item {
                SettingsRow("Profile", Icons.Default.Person, { onNavigate(Screen.EditProfile) })
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Subscription", Icons.Default.AttachMoney, { onNavigate(Screen.Subscription) })
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Privacy", Icons.Default.Lock, {})
            }

            // Playback section
            item {
                SectionHeader(title = "Playback", modifier = Modifier.padding(horizontal = 16.dp, top = 24.dp, bottom = 8.dp))
            }
            item {
                SettingsRow("Audio Quality", Icons.Default.SurroundSound, { onNavigate(Screen.AudioQuality) })
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Equalizer", Icons.Default.Equalizer, { onNavigate(Screen.Equalizer) })
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Crossfade", Icons.Default.Tune, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Gapless Playback", Icons.Default.SkipNext, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Autoplay", Icons.Default.Repeat, {})
            }

            // Appearance section
            item {
                SectionHeader(title = "Appearance", modifier = Modifier.padding(horizontal = 16.dp, top = 24.dp, bottom = 8.dp))
            }
            item {
                SettingsRow("Dark Theme", Icons.Default.DarkMode, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Accent Color", Icons.Default.ColorLens, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Dynamic Artwork", Icons.Default.Image, {})
            }

            // Storage section
            item {
                SectionHeader(title = "Storage", modifier = Modifier.padding(horizontal = 16.dp, top = 24.dp, bottom = 8.dp))
            }
            item {
                SettingsRow("Download Quality", Icons.Default.Download, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Storage Usage", Icons.Default.Storage, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Clear Cache", Icons.Default.Delete, {})
            }

            // Other section
            item {
                SectionHeader(title = "Other", modifier = Modifier.padding(horizontal = 16.dp, top = 24.dp, bottom = 8.dp))
            }
            item {
                SettingsRow("Notifications", Icons.Default.Notifications, { onNavigate(Screen.Notifications) })
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Language", Icons.Default.Language, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("About", Icons.Default.Info, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Help", Icons.Default.Help, { onNavigate(Screen.HelpSupport) })
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Privacy Policy", Icons.Default.Description, {})
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
                SettingsRow("Terms of Service", Icons.Default.Description, {})
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun SettingsRow(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
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
        Text(text = title, style = tArtist, color = textMain)
        Spacer(modifier = Modifier.weight(1f))
        androidx.compose.material3.Icon(
            imageVector = androidx.compose.material3.Icons.Default.ChevronRight,
            contentDescription = null,
            tint = textMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}

// --- Screen 32: Audio Quality ---
@Composable
fun AudioQualityScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var streamingQuality by remember { mutableStateOf("High") }
    var downloadQuality by remember { mutableStateOf("High") }
    var dataSaver by remember { mutableStateOf(false) }

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
            Text(text = "Audio Quality", style = tSection, color = textMain)
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Streaming Quality", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenu(
                expanded = true,
                onDismissRequest = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("Low", "Normal", "High", "Very High").forEach { quality ->
                    DropdownMenuItem(
                        text = { Text(text = quality, style = tArtist) },
                        onClick = { streamingQuality = quality }
                    )
                }
            }
            Text(
                text = streamingQuality,
                style = tSongTitle,
                color = textMain,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Download Quality", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenu(
                expanded = true,
                onDismissRequest = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("Low", "Normal", "High", "Very High").forEach { quality ->
                    DropdownMenuItem(
                        text = { Text(text = quality, style = tArtist) },
                        onClick = { downloadQuality = quality }
                    )
                }
            }
            Text(
                text = downloadQuality,
                style = tSongTitle,
                color = textMain,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = dataSaver,
                onCheckedChange = { dataSaver = it },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = surfaceElevated,
                    checkedColor = accentPrimary
                )
            )
            Column {
                Text(text = "Data Saver", style = tSongTitle, color = textMain)
                Text(text = "Reduces audio quality to save data", style = tArtist, color = textSecondary)
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 33: Equalizer ---
@Composable
fun EqualizerScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPreset by remember { mutableStateOf("Flat") }
    val presets = listOf("Flat", "Bass Boost", "Rock", "Pop", "Jazz", "Classical", "Vocal")

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
            Text(text = "Equalizer", style = tSection, color = textMain)
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Preset", style = tArtist, color = textSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenu(
                expanded = true,
                onDismissRequest = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                presets.forEach { preset ->
                    DropdownMenuItem(
                        text = { Text(text = preset, style = tArtist) },
                        onClick = { selectedPreset = preset }
                    )
                }
            }
            Text(
                text = selectedPreset,
                style = tSongTitle,
                color = textMain,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Equalizer visualization
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(surfaceElevated, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.BottomCenter
            ) {
                repeat(8) { i ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height((40 + (i * 15)).dp)
                            .background(accentPrimary, shape = RoundedCornerShape(4.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Frequency sliders
        listOf("60 Hz", "150 Hz", "400 Hz", "1K Hz", "2.4K Hz", "6K Hz", "15K Hz").forEachIndexed { index, freq ->
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = freq, style = tNavLabel, color = textSecondary)
                    Text(text = "0 dB", style = tNavLabel, color = textSecondary)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Slider(
                    value = 0.5f,
                    onValueChange = {},
                    valueRange = 0f..1f,
                    colors = SliderDefaults.colors(
                        activeTrackColor = accentPrimary,
                        inactiveTrackColor = divider,
                        thumbColor = accentPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(40.dp)
            ) {
                Text("Bass Boost", color = textMain)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
                modifier = Modifier.weight(1f).height(40.dp)
            ) {
                Text("Virtualizer", color = textMain)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = surfaceElevated),
            modifier = Modifier.fillMaxWidth().height(40.dp)
        ) {
            Text("Reset", color = textMain)
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 34: Subscription / Premium ---
@Composable
fun SubscriptionScreen(
    onClose: () -> Unit,
    onUpgrade: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Premium hero banner
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
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.Brightness7,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Go Premium", style = tHeadline, color = Color.White)
                Text(text = "Unlock the full music experience", style = tArtist, color = textSecondary.copy(alpha = 0.9f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Premium Benefits", style = tSection, color = textMain)
            Spacer(modifier = Modifier.height(12.dp))

            listOf(
                "Ad-free listening",
                "High-quality audio",
                "Offline downloads",
                "Unlimited skips",
                "Exclusive themes"
            ).forEach { benefit ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = success,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = benefit, style = tArtist, color = textMain)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Choose a Plan", modifier = Modifier.padding(horizontal = 16.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SubscriptionPlanCard(
                title = "Individual",
                price = "$9.99",
                period = "/month",
                isPopular = false,
                onSelect = {}
            )
            SubscriptionPlanCard(
                title = "Premium",
                price = "$14.99",
                period = "/month",
                isPopular = true,
                onSelect = {}
            )
            SubscriptionPlanCard(
                title = "Yearly",
                price = "$99.99",
                period = "/year",
                isPopular = false,
                onSelect = {}
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onUpgrade,
            colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
            modifier = Modifier.fillMaxWidth(0.8f).height(52.dp)
        ) {
            Text("Upgrade Now")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {}) {
            Text("Restore Purchase", style = tArtist, color = textSecondary)
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun SubscriptionPlanCard(
    title: String,
    price: String,
    period: String,
    isPopular: Boolean,
    onSelect: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onSelect() }
            .background(
                if (isPopular) surfaceElevated else surfaceCard,
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPopular) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(accentPrimary, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(text = "Popular", style = tNavLabel, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = title, style = tSection, color = textMain)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$price$period",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textMain)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = divider).let { Divider(modifier = Modifier.padding(horizontal = 12.dp), color = divider) }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Ad-free", style = tArtist, color = textSecondary)
        Text(text = "Offline mode", style = tArtist, color = textSecondary)
        Text(text = "High quality", style = tArtist, color = textSecondary)
    }
}

// --- Screen 35: Listening History ---
@Composable
fun ListeningHistoryScreen(
    onClear: () -> Unit,
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
            IconButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = textMain
                )
            }
            Text(text = "Listening History", style = tSection, color = textMain)
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {}) {
                Text("Search History", style = tArtist, color = accentPrimary)
            }
            TextButton(onClick = onClear) {
                Text("Clear History", style = tArtist, color = textMuted)
            }
        }

        ListDivider()

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            items(listOf("Today", "Yesterday", "This Week")) { date ->
                Text(text = date, style = tSection, color = textMain)
                Spacer(modifier = Modifier.height(8.dp))
                items(MockData.recentSongs.take(3).indexed) { (index, song) ->
                    SongItem(song, index = index, onClick = {})
                }
                if (date != "This Week") ListDivider()
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- Screen 36: Notifications ---
@Composable
fun NotificationsScreen(
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
            IconButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = textMain
                )
            }
            Text(text = "Notifications", style = tSection, color = textMain)
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            NotificationItem(
                title = "New Release",
                subtitle = "Luna Vale released a new album 'Nightfall'",
                time = "2h ago",
                icon = Icons.Default.Album
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)

            NotificationItem(
                title = "Playlist Updated",
                subtitle = "Chill Vibes has 3 new songs",
                time = "5h ago",
                icon = Icons.Default.LibraryMusic
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)

            NotificationItem(
                title = "Artist Update",
                subtitle = "New music from Sub Zero",
                time = "1d ago",
                icon = Icons.Default.Person
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun NotificationItem(
    title: String,
    subtitle: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, style = tSongTitle, color = textMain)
            Text(text = subtitle, style = tArtist, color = textSecondary, maxLines = 1)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = time, style = tNavLabel, color = textMuted)
    }
}

// --- Screen 37: Help & Support ---
@Composable
fun HelpSupportScreen(
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
            IconButton(onClick = {}) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material3.Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = textMain
                )
            }
            Text(text = "Help & Support", style = tSection, color = textMain)
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            HelpItem("FAQ", "Frequently asked questions", Icons.Default.Help, {})
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
            HelpItem("Contact Support", "Email our support team", Icons.Default.Email, {})
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
            HelpItem("Report a Problem", "Report bugs or issues", Icons.Default.BugReport, {})
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
            HelpItem("App Feedback", "Send us your feedback", Icons.Default.Feedback, {})
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = divider)
            HelpItem("About App", "Version 1.0.0", Icons.Default.Info, {})
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun HelpItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, style = tSongTitle, color = textMain)
            Text(text = subtitle, style = tArtist, color = textSecondary)
        }
        Spacer(modifier = Modifier.weight(1f))
        androidx.compose.material3.Icon(
            imageVector = androidx.compose.material3.Icons.Default.ChevronRight,
            contentDescription = null,
            tint = textMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}
