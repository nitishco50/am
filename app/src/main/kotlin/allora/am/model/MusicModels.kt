package allora.am.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String?,
    val artworkUrl: String?,
    val durationMs: Long,
    val genre: String?,
    val isFavorite: Boolean = false,
    val isDownloaded: Boolean = false,
    val playCount: Int = 0
) {
    val durationFormatted: String
        get() {
            val seconds = durationMs / 1000
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            return "$minutes:${remainingSeconds.toString().padStart(2, '0')}"
        }
}

data class Artist(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val monthlyListeners: Int,
    val genre: String?,
    val isFollowed: Boolean = false,
    val description: String? = null
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val artistId: String?,
    val artworkUrl: String?,
    val releaseYear: Int,
    val genre: String?,
    val trackCount: Int,
    val totalDurationMs: Long,
    val isSaved: Boolean = false
)

data class Playlist(
    val id: String,
    val title: String,
    val description: String?,
    val creator: String?,
    val artworkUrl: String?,
    val songCount: Int,
    val totalDurationMs: Long,
    val isPublic: Boolean = true,
    val isCreatedByUser: Boolean = false,
    val isFollowed: Boolean = false
)

data class User(
    val id: String,
    val name: String,
    val email: String?,
    val avatarUrl: String?,
    val username: String?,
    val bio: String?,
    val subscriptionType: String = "Free",
    val isPremium: Boolean = false
)

data class Genre(
    val id: String,
    val name: String,
    val artworkUrl: String?,
    val color: String?,
    val isSelected: Boolean = false
)

data class RadioStation(
    val id: String,
    val name: String,
    val description: String?,
    val artworkUrl: String?,
    val genre: String?,
    val isPlaying: Boolean = false
)

data class Mood(
    val id: String,
    val name: String,
    val artworkUrl: String?,
    val gradientStart: String?,
    val gradientEnd: String?
)
