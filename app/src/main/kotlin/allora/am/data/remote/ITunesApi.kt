package allora.am.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * Keyless iTunes Search API used to resolve a playable 30s preview when a
 * YouTube audio stream cannot be extracted.
 */
class ITunesApi {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .callTimeout(20, TimeUnit.SECONDS)
        .build()

    suspend fun previewUrl(artist: String, title: String): String? =
        withContext(Dispatchers.IO) {
            val term = URLEncoder.encode("$artist $title", "UTF-8")
            val request = Request.Builder()
                .url("https://itunes.apple.com/search?term=$term&media=music&entity=song&limit=8")
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 14)")
                .build()
            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) return@withContext null
                    val body = response.body?.string() ?: return@withContext null
                    val results = JSONObject(body).optJSONArray("results") ?: return@withContext null
                    for (i in 0 until results.length()) {
                        val item = results.optJSONObject(i) ?: continue
                        val preview = item.optString("previewUrl")
                        if (preview.startsWith("http")) return@withContext preview
                    }
                }
            } catch (_: Exception) {
                // no preview
            }
            null
        }
}
