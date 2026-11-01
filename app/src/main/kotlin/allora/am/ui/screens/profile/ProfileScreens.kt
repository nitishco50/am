package allora.am.ui.screens.profile

import allora.am.data.MockData
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Screen 29: Profile ---
@Composable
fun ProfileScreen(
    onNavigate: (Screen) -> Unit,
    onEditProfile: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    val user = MockData.currentUser

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onNavigate(Screen.Settings) }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = textSecondary)
                }
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(100.dp).clip(CircleShape).background(surfaceElevated),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Avatar", tint = textSecondary, modifier = Modifier.size(48.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = user.name, style = tHeadline, color = textMain)
                Text(text = "@${user.username}", style = tArtist, color = textSecondary)
                if (user.isPremium) {
                    Spacer(modifier = Modifier.height(8.dp))
                    PremiumBadge()
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = onEditProfile,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = textMain)
                ) {
                    Text("Edit Profile")
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("124", "Playlists", Modifier.weight(1f))
                StatCard("8.5K", "Minutes", Modifier.weight(1f))
                StatCard("42", "Following", Modifier.weight(1f))
            }
        }

        item { SectionHeader(title = "Recently Played", onSeeAll = { onNavigate(Screen.ListeningHistory) }) }
        items(MockData.recentSongs) { song ->
            SongItem(song, onClick = { onSongClick(song) }, modifier = Modifier.padding(horizontal = 16.dp))
        }

        item { SectionHeader(title = "Favorite Artists") }
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

        item { SectionHeader(title = "My Playlists", onSeeAll = { onNavigate(Screen.Library) }) }
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
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(surfaceElevated)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, style = tSection, color = textMain)
        Text(text = label, style = tNavLabel, color = textSecondary)
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
        modifier = modifier.fillMaxSize().background(bgPrimary).padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = textMain)
            }
            Text(text = "Edit Profile", style = tSection, color = textMain)
            TextButton(onClick = onSave) {
                Text("Save", style = tArtist, color = accentPrimary)
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(100.dp).clip(CircleShape).background(surfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Avatar", tint = textSecondary, modifier = Modifier.size(48.dp))
            }
            TextButton(onClick = {}) {
                Text("Change photo", style = tArtist, color = accentSecondary)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ProfileField(label = "Name", value = name, onValueChange = { name = it })
        Spacer(modifier = Modifier.height(16.dp))
        ProfileField(label = "Username", value = username, onValueChange = { username = it })
        Spacer(modifier = Modifier.height(16.dp))
        ProfileField(label = "Bio", value = bio, onValueChange = { bio = it }, height = 96.dp)
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    height: androidx.compose.ui.unit.Dp = 52.dp
) {
    Column {
        Text(text = label, style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(surfaceElevated, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = textMain, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// --- Screen 31: Settings ---
@Composable
fun SettingsScreen(
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 40.dp, bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigate(Screen.Profile) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textMain)
                }
                Text(text = "Settings", style = tSection, color = textMain)
            }
        }

        item { SectionHeader(title = "Account") }
        item {
            SettingsRow("Profile", Icons.Default.Person) { onNavigate(Screen.EditProfile) }
            SettingsRow("Subscription", Icons.Default.Star) { onNavigate(Screen.Subscription) }
            SettingsRow("Privacy", Icons.Default.Lock) {}
        }

        item { SectionHeader(title = "Playback") }
        item {
            SettingsRow("Audio Quality", Icons.Default.SurroundSound) { onNavigate(Screen.AudioQuality) }
            SettingsRow("Equalizer", Icons.Default.Equalizer) { onNavigate(Screen.Equalizer) }
            SettingsRow("Crossfade", Icons.Default.Tune) {}
            SettingsRow("Gapless Playback", Icons.Default.SkipNext) {}
            SettingsRow("Autoplay", Icons.Default.Repeat) {}
        }

        item { SectionHeader(title = "Appearance") }
        item {
            SettingsRow("Dark Theme", Icons.Default.DarkMode) {}
            SettingsRow("Accent Color", Icons.Default.ColorLens) {}
            SettingsRow("Dynamic Artwork", Icons.Default.Image) {}
        }

        item { SectionHeader(title = "Storage") }
        item {
            SettingsRow("Download Quality", Icons.Default.Download) {}
            SettingsRow("Storage Usage", Icons.Default.Storage) {}
            SettingsRow("Clear Cache", Icons.Default.Delete) {}
        }

        item { SectionHeader(title = "Other") }
        item {
            SettingsRow("Notifications", Icons.Default.Notifications) { onNavigate(Screen.Notifications) }
            SettingsRow("Language", Icons.Default.Language) {}
            SettingsRow("About", Icons.Default.Info) {}
            SettingsRow("Help", Icons.Default.Help) { onNavigate(Screen.HelpSupport) }
            SettingsRow("Privacy Policy", Icons.Default.Description) {}
            SettingsRow("Terms of Service", Icons.Default.Description) {}
        }
    }
}

@Composable
private fun SettingsRow(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = textSecondary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = tArtist, color = textMain, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = textMuted, modifier = Modifier.size(18.dp))
    }
}

// --- Screen 32: Audio Quality ---
@Composable
fun AudioQualityScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val qualities = listOf("Low", "Normal", "High", "Very High")
    var streaming by remember { mutableStateOf("High") }
    var download by remember { mutableStateOf("High") }
    var dataSaver by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize().background(bgPrimary).padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textMain)
            }
            Text(text = "Audio Quality", style = tSection, color = textMain)
        }

        Text(text = "Streaming Quality", style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        qualities.forEach { q ->
            OptionRow(text = q, selected = q == streaming, onClick = { streaming = q })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Download Quality", style = tArtist, color = textSecondary)
        Spacer(modifier = Modifier.height(8.dp))
        qualities.forEach { q ->
            OptionRow(text = q, selected = q == download, onClick = { download = q })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = dataSaver,
                onCheckedChange = { dataSaver = it },
                colors = SwitchDefaults.colors(checkedTrackColor = accentPrimary)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Data Saver", style = tSongTitle, color = textMain)
                Text(text = "Reduce audio quality to save data", style = tArtist, color = textSecondary)
            }
        }
    }
}

@Composable
private fun OptionRow(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = tSongTitle, color = if (selected) accentPrimary else textMain, modifier = Modifier.weight(1f))
        if (selected) {
            Icon(Icons.Default.Check, contentDescription = null, tint = accentPrimary, modifier = Modifier.size(20.dp))
        }
    }
}

// --- Screen 33: Equalizer ---
@Composable
fun EqualizerScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val presets = listOf("Flat", "Bass Boost", "Rock", "Pop", "Jazz", "Classical", "Vocal")
    var selectedPreset by remember { mutableStateOf("Flat") }
    val bands = listOf("60Hz", "150Hz", "400Hz", "1kHz", "2.4kHz", "6kHz", "15kHz")
    val levels = remember { mutableStateListOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f) }

    Column(
        modifier = modifier.fillMaxSize().background(bgPrimary).padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textMain)
            }
            Text(text = "Equalizer", style = tSection, color = textMain)
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(presets) { preset ->
                FilterChip(
                    selected = preset == selectedPreset,
                    onClick = { selectedPreset = preset },
                    label = { Text(preset, style = tArtist, color = if (preset == selectedPreset) Color.White else textSecondary) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (preset == selectedPreset) accentPrimary else surfaceElevated
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceElevated)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                levels.forEach { level ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(level)
                            .clip(RoundedCornerShape(4.dp))
                            .background(accentPrimary)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        bands.forEachIndexed { index, band ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = band, style = tNavLabel, color = textSecondary)
                    Text(text = "${((levels[index] - 0.5f) * 24).toInt()} dB", style = tNavLabel, color = textSecondary)
                }
                Slider(
                    value = levels[index],
                    onValueChange = { levels[index] = it },
                    colors = SliderDefaults.colors(
                        thumbColor = accentPrimary,
                        activeTrackColor = accentPrimary,
                        inactiveTrackColor = divider
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(contentColor = textMain),
                modifier = Modifier.weight(1f)
            ) { Text("Bass Boost") }
            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(contentColor = textMain),
                modifier = Modifier.weight(1f)
            ) { Text("Virtualizer") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { for (i in levels.indices) levels[i] = 0.5f },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = textMain),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Reset") }
    }
}

// --- Screen 34: Subscription / Premium ---
@Composable
fun SubscriptionScreen(
    onClose: () -> Unit,
    onUpgrade: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(accentPrimary, accentSecondary)))
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Go Premium", style = tHeadline, color = Color.White)
                    Text(text = "Unlock the full music experience", style = tArtist, color = Color.White.copy(alpha = 0.9f))
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = success, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = benefit, style = tArtist, color = textMain)
                    }
                }
            }
        }

        item { SectionHeader(title = "Choose a Plan") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { PlanCard("Individual", "$9.99", "/mo", false) }
                item { PlanCard("Premium", "$14.99", "/mo", true) }
                item { PlanCard("Yearly", "$99.99", "/yr", false) }
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onUpgrade,
                    colors = ButtonDefaults.buttonColors(containerColor = accentPrimary),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Text("Upgrade Now")
                }
                TextButton(onClick = {}) {
                    Text("Restore Purchase", style = tArtist, color = textSecondary)
                }
            }
        }
    }
}

@Composable
private fun PlanCard(title: String, price: String, period: String, popular: Boolean) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (popular) surfaceElevated else surfaceCard)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (popular) {
            Box(
                modifier = Modifier
                    .background(accentPrimary, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("Popular", style = tNavLabel, color = Color.White)
            }
        } else {
            Spacer(modifier = Modifier.height(18.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, style = tSection, color = textMain)
        Text(
            text = "$price$period",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textMain)
        )
        Spacer(modifier = Modifier.height(12.dp))
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
    val dates = listOf("Today", "Yesterday", "Earlier This Week")

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textMain)
                    }
                    Text(text = "Listening History", style = tSection, color = textMain)
                }
                TextButton(onClick = onClear) {
                    Text("Clear", style = tArtist, color = textMuted)
                }
            }
        }

        dates.forEach { date ->
            item { SectionHeader(title = date) }
            items(MockData.recentSongs) { song ->
                SongItem(song = song, onClick = {}, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

// --- Screen 36: Notifications ---
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(bgPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textMain)
            }
            Text(text = "Notifications", style = tSection, color = textMain)
        }

        LazyColumn {
            item { NotificationItem("New Release", "Luna Vale released a new album 'Nightfall'", "2h ago", Icons.Default.Album) }
            item { NotificationItem("Playlist Updated", "Chill Vibes has 3 new songs", "5h ago", Icons.Default.LibraryMusic) }
            item { NotificationItem("Artist Update", "New music from Sub Zero", "1d ago", Icons.Default.Person) }
        }
    }
}

@Composable
private fun NotificationItem(title: String, subtitle: String, time: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = accentPrimary, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = tSongTitle, color = textMain)
            Text(text = subtitle, style = tArtist, color = textSecondary, maxLines = 1)
        }
        Text(text = time, style = tNavLabel, color = textMuted)
    }
}

// --- Screen 37: Help & Support ---
@Composable
fun HelpSupportScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(bgPrimary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textMain)
            }
            Text(text = "Help & Support", style = tSection, color = textMain)
        }

        HelpItem("FAQ", "Frequently asked questions", Icons.Default.Help)
        HelpItem("Contact Support", "Email our support team", Icons.Default.Email)
        HelpItem("Report a Problem", "Report bugs or issues", Icons.Default.BugReport)
        HelpItem("App Feedback", "Send us your feedback", Icons.Default.Feedback)
        HelpItem("About App", "Version 1.0.0", Icons.Default.Info)
    }
}

@Composable
private fun HelpItem(title: String, subtitle: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable {}
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(surfaceElevated),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = accentPrimary, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = tSongTitle, color = textMain)
            Text(text = subtitle, style = tArtist, color = textSecondary)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = textMuted, modifier = Modifier.size(18.dp))
    }
}
