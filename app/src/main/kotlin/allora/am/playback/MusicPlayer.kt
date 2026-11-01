package allora.am.playback

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import allora.am.data.MockData
import allora.am.data.MusicRepository
import allora.am.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** Streams the resolved YouTube audio URL with ExoPlayer. */
object MusicPlayer {

    private var exo: ExoPlayer? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var requestId = 0

    var currentSong: Song? by mutableStateOf(null)
        private set
    var isPlaying: Boolean by mutableStateOf(false)
        private set
    var isBuffering: Boolean by mutableStateOf(false)
        private set

    private fun ensure(context: Context): ExoPlayer {
        exo?.let { return it }
        val player = ExoPlayer.Builder(context.applicationContext).build()
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                isBuffering = playbackState == Player.STATE_BUFFERING
            }
        })
        exo = player
        return player
    }

    fun play(context: Context, song: Song) {
        val player = ensure(context)
        currentSong = song
        isBuffering = true
        val id = ++requestId
        scope.launch {
            val streamUrl = try {
                MusicRepository.audioUrl(song)
            } catch (_: Exception) {
                null
            }
            val url = streamUrl ?: try {
                MusicRepository.previewUrl(song)
            } catch (_: Exception) {
                null
            }
            if (id != requestId) return@launch
            if (url.isNullOrBlank()) {
                isBuffering = false
                isPlaying = false
                openInYouTube(context, song)
                return@launch
            }
            player.setMediaItem(MediaItem.fromUri(url))
            player.prepare()
            player.playWhenReady = true
        }
    }

    private fun openInYouTube(context: Context, song: Song) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://music.youtube.com/watch?v=${song.id}")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (_: Exception) {
            // no app can handle it
        }
    }

    fun toggle() {
        val player = exo ?: return
        if (player.isPlaying) player.pause() else player.play()
    }

    fun next(context: Context) {
        val list = MockData.songs
        if (list.isEmpty()) return
        val index = list.indexOfFirst { it.id == currentSong?.id }
        play(context, list[((if (index < 0) 0 else index) + 1) % list.size])
    }

    fun previous(context: Context) {
        val list = MockData.songs
        if (list.isEmpty()) return
        val index = list.indexOfFirst { it.id == currentSong?.id }
        val previousIndex = if (index < 0) 0 else (index - 1 + list.size) % list.size
        play(context, list[previousIndex])
    }

    fun release() {
        exo?.release()
        exo = null
        isPlaying = false
        isBuffering = false
    }
}
