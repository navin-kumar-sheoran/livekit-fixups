package io.livekit.android.dagger

import dagger.Module
import dagger.Provides
import io.livekit.android.memory.CloseableManager
import javax.inject.Singleton

/**
 * @suppress
 */
@Module
object MemoryModule {

    @Singleton
    @Provides
    fun closeableManager() = CloseableManager()
}