package allora.am.data

import allora.am.model.*

object MockData {

    val currentUser = User(
        id = "user_1",
        name = "Alex Rivera",
        email = "alex@example.com",
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1272c8e047?w=200&h=200&fit=crop",
        username = "alex_rivera",
        bio = "Music lover & explorer",
        subscriptionType = "Premium",
        isPremium = true
    )

    val songs = listOf(
        Song("s1", "Midnight Echoes", "Luna Vale", "Nightfall", "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=400&h=400&fit=crop", 218000, "Pop", isFavorite = true),
        Song("s2", "Neon Dreams", "Cyber Pulse", "Synthwave", "https://images.unsplash.com/photo-1470225620777-4b13a9ed7424?w=400&h=400&fit=crop", 195000, "Electronic"),
        Song("s3", "Golden Hour", "Sunset Rider", "Horizon", "https://images.unsplash.com/photo-1469344131830-2a0f171e1fca?w=400&h=400&fit=crop", 245000, "Indie", isFavorite = true),
        Song("s4", "Deep Bass", "Sub Zero", "Underground", "https://images.unsplash.com/photo-1459749411475-058432b09496?w=400&h=400&fit=crop", 178000, "Hip-Hop"),
        Song("s5", "Classical Reverie", "Orchestra Nova", "Symphony", "https://images.unsplash.com/photo-1470229722917-78c7917d471d?w=400&h=400&fit=crop", 312000, "Classical"),
        Song("s6", "Jazz Nights", "Miles Blue", "Blue Note", "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=400&h=400&fit=crop", 267000, "Jazz", isFavorite = true),
        Song("s7", "Lo-Fi Beats", "Chill Study", "Focus", "https://images.unsplash.com/photo-1493225283274-8331972d2ab6?w=400&h=400&fit=crop", 234000, "Lo-Fi"),
        Song("s8", "Bollywood Nights", "Bolly Star", "Cinema", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=400&h=400&fit=crop", 256000, "Bollywood"),
        Song("s9", "Punjabi Rhythm", "Dhol Beats", "Punjab", "https://images.unsplash.com/photo-1516428318717-8b1398265619?w=400&h=400&fit=crop", 201000, "Punjabi"),
        Song("s10", "Devotional Chant", "Spirit Voice", "Divine", "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=400&fit=crop", 289000, "Devotional", isFavorite = true),
        Song("s11", "Rock Anthem", "Electric Riot", "Thunder", "https://images.unsplash.com/photo-1493225348671-6a49e073b3a1?w=400&h=400&fit=crop", 198000, "Rock"),
        Song("s12", "Viral Hit", "TikTok Star", "Trending", "https://images.unsplash.com/photo-1511379938447-d1eab9700a2f?w=400&h=400&fit=crop", 156000, "Pop"),
    )

    val artists = listOf(
        Artist("a1", "Luna Vale", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&h=200&fit=crop", 2450000, "Pop", isFollowed = true, description = "International pop sensation known for dreamy vocals."),
        Artist("a2", "Cyber Pulse", "https://images.unsplash.com/photo-1507003211169-0a1272c8e047?w=200&h=200&fit=crop", 1890000, "Electronic"),
        Artist("a3", "Sunset Rider", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&h=200&fit=crop", 1230000, "Indie"),
        Artist("a4", "Sub Zero", "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200&h=200&fit=crop", 3100000, "Hip-Hop", isFollowed = true),
        Artist("a5", "Orchestra Nova", "https://images.unsplash.com/photo-1531123897727-8f129e1688ce?w=200&h=200&fit=crop", 567000, "Classical"),
        Artist("a6", "Miles Blue", "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=200&h=200&fit=crop", 890000, "Jazz"),
        Artist("a7", "Bolly Star", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=200&h=200&fit=crop", 5400000, "Bollywood"),
        Artist("a8", "Dhol Beats", "https://images.unsplash.com/photo-1516428318717-8b1398265619?w=200&h=200&fit=crop", 2100000, "Punjabi"),
    )

    val albums = listOf(
        Album("al1", "Nightfall", "Luna Vale", "a1", "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=400&h=400&fit=crop", 2023, "Pop", 12, 2616000, isSaved = true),
        Album("al2", "Synthwave", "Cyber Pulse", "a2", "https://images.unsplash.com/photo-1470225620777-4b13a9ed7424?w=400&h=400&fit=crop", 2024, "Electronic", 10, 1950000),
        Album("al3", "Horizon", "Sunset Rider", "a3", "https://images.unsplash.com/photo-1469344131830-2a0f171e1fca?w=400&h=400&fit=crop", 2023, "Indie", 11, 2695000, isSaved = true),
        Album("al4", "Underground", "Sub Zero", "a4", "https://images.unsplash.com/photo-1459749411475-058432b09496?w=400&h=400&fit=crop", 2024, "Hip-Hop", 14, 2492000),
        Album("al5", "Symphony", "Orchestra Nova", "a5", "https://images.unsplash.com/photo-1470229722917-78c7917d471d?w=400&h=400&fit=crop", 2022, "Classical", 8, 2496000),
    )

    val playlists = listOf(
        Playlist("p1", "Chill Vibes", "Relax and unwind", currentUser.name, "https://images.unsplash.com/photo-1470225620780-8ba9450e2bb9?w=400&h=400&fit=crop", 24, 5280000, isCreatedByUser = true, isFollowed = true),
        Playlist("p2", "Workout Pump", "High energy training", "Music Curators", "https://images.unsplash.com/photo-1526484027748-c2a88386a2aa?w=400&h=400&fit=crop", 30, 6450000),
        Playlist("p3", "Focus Flow", "Deep concentration", currentUser.name, "https://images.unsplash.com/photo-1493225283274-8331972d2ab6?w=400&h=400&fit=crop", 18, 4212000, isCreatedByUser = true),
        Playlist("p4", "Party Starters", "Celebration hits", "Music Curators", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=400&h=400&fit=crop", 28, 6120000),
        Playlist("p5", "Late Night", "Midnight thoughts", currentUser.name, "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=400&h=400&fit=crop", 15, 3270000, isCreatedByUser = true, isFollowed = true),
        Playlist("p6", "Bollywood Gold", "Bollywood classics", "Music Curators", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=400&h=400&fit=crop", 40, 8640000),
    )

    val genres = listOf(
        Genre("g1", "Pop", "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=200&h=200&fit=crop", "#FF6B6B"),
        Genre("g2", "Hip-Hop", "https://images.unsplash.com/photo-1459749411475-058432b09496?w=200&h=200&fit=crop", "#4ECDC4"),
        Genre("g3", "Rock", "https://images.unsplash.com/photo-1493225348671-6a49e073b3a1?w=200&h=200&fit=crop", "#45B7D1"),
        Genre("g4", "Electronic", "https://images.unsplash.com/photo-1470225620777-4b13a9ed7424?w=200&h=200&fit=crop", "#96CEB4"),
        Genre("g5", "Classical", "https://images.unsplash.com/photo-1470229722917-78c7917d471d?w=200&h=200&fit=crop", "#FFEAA7"),
        Genre("g6", "Jazz", "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=200&h=200&fit=crop", "#DDA0DD"),
        Genre("g7", "Indie", "https://images.unsplash.com/photo-1469344131830-2a0f171e1fca?w=200&h=200&fit=crop", "#98D8C8"),
        Genre("g8", "Bollywood", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=200&h=200&fit=crop", "#F7DC6F"),
        Genre("g9", "Punjabi", "https://images.unsplash.com/photo-1516428318717-8b1398265619?w=200&h=200&fit=crop", "#F1948A"),
        Genre("g10", "Bhojpuri", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=200&h=200&fit=crop", "#BB8FCE"),
        Genre("g11", "Lo-Fi", "https://images.unsplash.com/photo-1493225283274-8331972d2ab6?w=200&h=200&fit=crop", "#85C1E9"),
        Genre("g12", "Devotional", "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=200&h=200&fit=crop", "#F8C471"),
    )

    val moods = listOf(
        Mood("m1", "Focus", "https://images.unsplash.com/photo-1493225283274-8331972d2ab6?w=200&h=200&fit=crop", "#4CAF50", "#2E7D32"),
        Mood("m2", "Chill", "https://images.unsplash.com/photo-1470225620780-8ba9450e2bb9?w=200&h=200&fit=crop", "#2196F3", "#1565C0"),
        Mood("m3", "Workout", "https://images.unsplash.com/photo-1526484027748-c2a88386a2aa?w=200&h=200&fit=crop", "#F44336", "#B71C1C"),
        Mood("m4", "Party", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=200&h=200&fit=crop", "#9C27B0", "#6A1B9A"),
        Mood("m5", "Sleep", "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=200&h=200&fit=crop", "#3F51B5", "#1A237E"),
        Mood("m6", "Travel", "https://images.unsplash.com/photo-1469344131830-2a0f171e1fca?w=200&h=200&fit=crop", "#FF9800", "#E65100"),
        Mood("m7", "Romantic", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=200&h=200&fit=crop", "#E91E63", "#880E4F"),
        Mood("m8", "Sad", "https://images.unsplash.com/photo-1459749411475-058432b09496?w=200&h=200&fit=crop", "#795548", "#3E2723"),
        Mood("m9", "Happy", "https://images.unsplash.com/photo-1470225620777-4b13a9ed7424?w=200&h=200&fit=crop", "#FFEB3B", "#F57F17"),
        Mood("m10", "Relaxing", "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=200&h=200&fit=crop", "#00BCD4", "#00838F"),
    )

    val radioStations = listOf(
        RadioStation("r1", "Today's Hits", "The biggest pop hits right now", "https://images.unsplash.com/photo-1493225542352-4ef99b3247d4?w=200&h=200&fit=crop", "Pop"),
        RadioStation("r2", "Electronic Waves", "Pure electronic energy", "https://images.unsplash.com/photo-1470225620777-4b13a9ed7424?w=200&h=200&fit=crop", "Electronic"),
        RadioStation("r3", "Rock Classics", "Legendary rock anthems", "https://images.unsplash.com/photo-1493225348671-6a49e073b3a1?w=200&h=200&fit=crop", "Rock"),
        RadioStation("r4", "Jazz Lounge", "Smooth jazz vibes", "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=200&h=200&fit=crop", "Jazz"),
        RadioStation("r5", "Bollywood Beats", "Bollywood's finest", "https://images.unsplash.com/photo-1514552964923-389fd801b7b2?w=200&h=200&fit=crop", "Bollywood"),
    )

    val favoriteSongs = songs.filter { it.isFavorite }
    val recentSongs = songs.take(5)
    val trendingSongs = songs.take(4)
    val newReleases = songs.take(3)
}
