package io.livekit.android.dagger

import android.annotation.SuppressLint
import androidx.annotation.Nullable
import dagger.Module
import dagger.Provides
import io.livekit.android.LiveKitOverrides
import javax.inject.Named

@SuppressLint("KotlinNullnessAnnotation")
/**
 * @suppress
 */
@Module
class OverridesModule(private val overrides: LiveKitOverrides) {

    @Provides
    @Named(InjectionNames.OVERRIDE_OKHTTP)
    @Nullable
    fun okHttpClient() = overrides.okHttpClient

    @Provides
    @Named(InjectionNames.OVERRIDE_AUDIO_DEVICE_MODULE)
    @Nullable
    fun audioDeviceModule() = overrides.audioDeviceModule

    @Provides
    @Named(InjectionNames.OVERRIDE_JAVA_AUDIO_DEVICE_MODULE_CUSTOMIZER)
    @Nullable
    fun javaAudioDeviceModuleCustomizer() = overrides.javaAudioDeviceModuleCustomizer

    @Provides
    @Named(InjectionNames.OVERRIDE_VIDEO_ENCODER_FACTORY)
    @Nullable
    fun videoEncoderFactory() = overrides.videoEncoderFactory

    @Provides
    @Named(InjectionNames.OVERRIDE_VIDEO_DECODER_FACTORY)
    @Nullable
    fun videoDecoderFactory() = overrides.videoDecoderFactory

    @Provides
    @Named(InjectionNames.OVERRIDE_AUDIO_HANDLER)
    @Nullable
    fun audioHandler() = overrides.audioHandler

    @Provides
    @Named(InjectionNames.OVERRIDE_EGL_BASE)
    @Nullable
    fun eglBase() = overrides.eglBase

}
