package allora.am.data.remote

import allora.am.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * Keyless YouTube Music access through public Piped API instances.
 * Instances are tried in order and the last working one is cached.
 */
class PipedApi {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    private val instances = listOf(
        "https://pipedapi.kavin.rocks",
        "https://pipedapi.adminforge.de",
        "https://api.piped.private.coffee",
        "https://pipedapi.reallyaweso.me",
        "https://pipedapi.drgns.space",
        "https://pipedapi.leptons.xyz",
        "https://pipedapi.ducks.party"
    )

    @Volatile
    private var workingBase: String? = null

    private fun bases(): List<String> = buildList {
        workingBase?.let { add(it) }
        instances.forEach { if (it != workingBase) add(it) }
    }

    private fun request(base: String, path: String): JSONObject? {
        return try {
            val request = Request.Builder()
                .url(base + path)
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36")
                .header("Accept", "application/json")
                .build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    if (!body.isNullOrBlank()) JSONObject(body) else null
                } else {
                    null
                }
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun get(path: String): JSONObject? {
        for (base in bases()) {
            val json = request(base, path) ?: continue
            workingBase = base
            return json
        }
        return null
    }

    suspend fun searchSongs(query: String, limit: Int = 30): List<Song> =
        withContext(Dispatchers.IO) {
            val json = get("/search?q=${enc(query)}&filter=music_songs") ?: return@withContext emptyList()
            val items = json.optJSONArray("items") ?: return@withContext emptyList()
            buildList {
                for (i in 0 until items.length()) {
                    val o = items.optJSONObject(i) ?: continue
                    val id = videoId(o.optString("url")) ?: continue
                    add(
                        Song(
                            id = id,
                            title = o.optString("title").ifBlank { "Unknown title" },
                            artist = o.optString("uploaderName").ifBlank { "Unknown artist" },
                            album = null,
                            artworkUrl = absolute(o.optString("thumbnail")),
                            durationMs = o.optLong("duration") * 1000L,
                            genre = "Music"
                        )
                    )
                }
            }.take(limit)
        }

    suspend fun searchArtists(query: String, limit: Int = 15): List<Artist> =
        withContext(Dispatchers.IO) {
            val json = get("/search?q=${enc(query)}&filter=channels") ?: return@withContext emptyList()
            val items = json.optJSONArray("items") ?: return@withContext emptyList()
            buildList {
                for (i in 0 until items.length()) {
                    val o = items.optJSONObject(i) ?: continue
                    val name = o.optString("name").ifBlank { o.optString("title") }
                    if (name.isBlank()) continue
                    add(
                        Artist(
                            id = o.optString("url").substringAfterLast("/").ifBlank { "artist_$i" },
                            name = name,
                            imageUrl = absolute(o.optString("thumbnail")),
                            monthlyListeners = o.optInt("subscribers", 0),
                            genre = "Music",
                            description = o.optString("description").ifBlank { null }
                        )
                    )
                }
            }.take(limit)
        }

    suspend fun searchAlbums(query: String, limit: Int = 15): List<Album> =
        withContext(Dispatchers.IO) {
            val json = get("/search?q=${enc(query)}&filter=music_albums") ?: return@withContext emptyList()
            val items = json.optJSONArray("items") ?: return@withContext emptyList()
            buildList {
                for (i in 0 until items.length()) {
                    val o = items.optJSONObject(i) ?: continue
                    val title = o.optString("title").ifBlank { o.optString("name") }
                    if (title.isBlank()) continue
                    add(
                        Album(
                            id = listId(o.optString("url")) ?: "album_$i",
                            title = title,
                            artist = o.optString("uploaderName").ifBlank { "Various Artists" },
                            artistId = null,
                            artworkUrl = absolute(o.optString("thumbnail")),
                            releaseYear = 0,
                            genre = "Album",
                            trackCount = o.optInt("videos", 0),
                            totalDurationMs = 0L
                        )
                    )
                }
            }.take(limit)
        }

    suspend fun searchPlaylists(query: String, limit: Int = 15): List<Playlist> =
        withContext(Dispatchers.IO) {
            val json = get("/search?q=${enc(query)}&filter=music_playlists") ?: return@withContext emptyList()
            val items = json.optJSONArray("items") ?: return@withContext emptyList()
            buildList {
                for (i in 0 until items.length()) {
                    val o = items.optJSONObject(i) ?: continue
                    val title = o.optString("title").ifBlank { o.optString("name") }
                    if (title.isBlank()) continue
                    add(
                        Playlist(
                            id = listId(o.optString("url")) ?: "playlist_$i",
                            title = title,
                            description = o.optString("uploaderName").ifBlank { null },
                            creator = o.optString("uploaderName").ifBlank { "YouTube Music" },
                            artworkUrl = absolute(o.optString("thumbnail")),
                            songCount = o.optInt("videos", 0),
                            totalDurationMs = 0L,
                            isPublic = true
                        )
                    )
                }
            }.take(limit)
        }

    /** Resolves a playable audio stream URL for a YouTube video id. */
    suspend fun audioUrl(videoId: String): String? = withContext(Dispatchers.IO) {
        if (videoId.isBlank()) return@withContext null
        for (base in bases()) {
            val json = request(base, "/streams/$videoId") ?: continue
            val streams = json.optJSONArray("audioStreams") ?: continue
            val candidates = buildList {
                for (i in 0 until streams.length()) {
                    val o = streams.optJSONObject(i) ?: continue
                    val url = o.optString("url")
                    if (url.isBlank()) continue
                    add(Triple(url, o.optString("mimeType"), o.optInt("bitrate")))
                }
            }
            if (candidates.isEmpty()) continue
            workingBase = base
            val mp4 = candidates.filter { it.second.contains("mp4", ignoreCase = true) }
            return@withContext (if (mp4.isNotEmpty()) mp4 else candidates)
                .maxByOrNull { it.third }?.first
        }
        null
    }

    // --- helpers ---
    private fun enc(value: String): String = URLEncoder.encode(value, "UTF-8")

    private val videoIdRegex = Regex("[?&]v=([A-Za-z0-9_-]{11})")
    private val listIdRegex = Regex("[?&]list=([A-Za-z0-9_-]+)")

    private fun videoId(url: String?): String? {
        if (url.isNullOrBlank()) return null
        videoIdRegex.find(url)?.groupValues?.getOrNull(1)?.let { return it }
        if (url.length == 11 && !url.contains('/')) return url
        return null
    }

    private fun listId(url: String?): String? {
        if (url.isNullOrBlank()) return null
        return listIdRegex.find(url)?.groupValues?.getOrNull(1)
    }

    private fun absolute(url: String?): String? {
        if (url.isNullOrBlank()) return null
        if (url.startsWith("http://") || url.startsWith("https://")) return url
        val base = workingBase ?: instances.first()
        return base + (if (url.startsWith("/")) url else "/$url")
    }
}
