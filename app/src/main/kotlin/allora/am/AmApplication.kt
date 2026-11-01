package allora.am

import android.app.Application
import allora.am.data.MusicRepository
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache

class AmApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        MusicRepository.attach(this)
    }

    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(120L * 1024L * 1024L)
                    .build()
            }
            .crossfade(120)
            .respectCacheHeaders(false)
            .build()
}
