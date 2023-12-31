package io.livekit.android

import io.livekit.android.audio.AudioHandler
import io.livekit.android.audio.NoAudioHandler
import io.livekit.android.room.Room
import okhttp3.OkHttpClient
import org.webrtc.EglBase
import org.webrtc.VideoDecoderFactory
import org.webrtc.VideoEncoderFactory
import org.webrtc.audio.AudioDeviceModule
import org.webrtc.audio.JavaAudioDeviceModule

/**
 * Overrides to replace LiveKit internally used component with custom implementations.
 */
data class LiveKitOverrides(
    /**
     * Override the [OkHttpClient] used by the library.
     */
    val okHttpClient: OkHttpClient? = null,

    /**
     * Override the default [AudioDeviceModule].
     */
    val audioDeviceModule: AudioDeviceModule? = null,

    /**
     * Called after default setup to allow for customizations on the [JavaAudioDeviceModule].
     *
     * Not used if [audioDeviceModule] is provided.
     */
    val javaAudioDeviceModuleCustomizer: ((builder: JavaAudioDeviceModule.Builder) -> Unit)? = null,

    /**
     * Override the [VideoEncoderFactory] used by the library.
     */
    val videoEncoderFactory: VideoEncoderFactory? = null,

    /**
     * Override the [VideoDecoderFactory] used by the library.
     */
    val videoDecoderFactory: VideoDecoderFactory? = null,

    /**
     * Override the default [AudioHandler].
     *
     * Use [NoAudioHandler] to turn off automatic audio handling.
     */

    val audioHandler: AudioHandler? = null,
    /**
     * Override the [EglBase] used by the library.
     *
     * If a non-null value is passed, the library does not
     * take ownership of the object and will not release it upon [Room.release].
     * It is the responsibility of the owner to call [EglBase.release] when finished
     * with it to prevent memory leaks.
     */
    val eglBase: EglBase? = null,
)