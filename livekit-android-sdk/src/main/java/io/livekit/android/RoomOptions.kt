package io.livekit.android

import io.livekit.android.e2ee.E2EEOptions
import io.livekit.android.room.Room
import io.livekit.android.room.participant.AudioTrackPublishDefaults
import io.livekit.android.room.participant.VideoTrackPublishDefaults
import io.livekit.android.room.track.LocalAudioTrackOptions
import io.livekit.android.room.track.LocalVideoTrackOptions

data class RoomOptions(
    /**
     * @see [Room.adaptiveStream]
     */
    val adaptiveStream: Boolean = false,

    /**
     * @see [Room.dynacast]
     */
    val dynacast: Boolean = false,

    /**
     * Options for end-to-end encryption.
     */
    val e2eeOptions: E2EEOptions? = null,

    val audioTrackCaptureDefaults: LocalAudioTrackOptions? = null,
    val videoTrackCaptureDefaults: LocalVideoTrackOptions? = null,
    val audioTrackPublishDefaults: AudioTrackPublishDefaults? = null,
    val videoTrackPublishDefaults: VideoTrackPublishDefaults? = null,
)