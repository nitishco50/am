package allora.am.data

import allora.am.data.remote.ITunesApi
import allora.am.data.remote.PipedApi
import allora.am.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Loads real YouTube Music data (keyless, via Piped) into the observable MockData
 * store. If the network or all instances fail, the bundled demo catalog stays.
 */
object MusicRepository {

    private val api = PipedApi()
    private val iTunes = ITunesApi()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    @Volatile
    private var started = false

    private val songQueries = listOf("top hits 2026", "trending music", "new songs 2026")
    private val artistQueries = listOf("popular artists 2026", "top singers", "trending artists")
    private val albumQueries = listOf("new albums 2026", "top albums", "popular albums")
    private val playlistQueries = listOf("top music playlists", "trending playlists", "best songs playlist")

    fun bootstrap() {
        if (started) return
        started = true
        scope.launch {
            _isLoading.value = true
            try {
                val songs = firstNonEmpty(songQueries) { api.searchSongs(it) }
                if (songs.isNotEmpty()) {
                    val favourites = songs.mapIndexed { index, song ->
                        if (index % 5 == 0) song.copy(isFavorite = true) else song
                    }
                    MockData.updateSongs(favourites)
                }

                val artists = firstNonEmpty(artistQueries) { api.searchArtists(it) }
                MockData.updateArtists(artists)

                val albums = firstNonEmpty(albumQueries) { api.searchAlbums(it) }
                MockData.updateAlbums(albums)

                val playlists = firstNonEmpty(playlistQueries) { api.searchPlaylists(it) }
                MockData.updatePlaylists(playlists)
            } catch (_: Exception) {
                // keep demo catalog
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        started = false
        bootstrap()
    }

    suspend fun audioUrl(song: Song): String? = api.audioUrl(song.id)

    /** Fallback audible stream (30s) when YouTube extraction is unavailable. */
    suspend fun previewUrl(song: Song): String? = iTunes.previewUrl(song.artist, song.title)

    suspend fun searchSongs(query: String): List<Song> = api.searchSongs(query)

    private suspend fun <T> firstNonEmpty(
        queries: List<String>,
        block: suspend (String) -> List<T>
    ): List<T> {
        for (query in queries) {
            try {
                val result = block(query)
                if (result.isNotEmpty()) return result
            } catch (_: Exception) {
                // try next query
            }
        }
        return emptyList()
    }
}
