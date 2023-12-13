package io.livekit.android.room.track

import io.livekit.android.webrtc.peerconnection.executeBlockingOnRTCThread
import org.webrtc.AudioTrack
import org.webrtc.AudioTrackSink
import org.webrtc.RtpReceiver

class RemoteAudioTrack(
    name: String,
    rtcTrack: AudioTrack,
    internal val receiver: RtpReceiver,
) : io.livekit.android.room.track.AudioTrack(name, rtcTrack) {

    /**
     * Adds a sink that receives the audio bytes and related information
     * for this audio track. Repeated calls using the same sink will
     * only add the sink once.
     *
     * Implementations should copy the audio data into a local copy if they wish
     * to use the data after this function returns.
     */
    fun addSink(sink: AudioTrackSink) {
        executeBlockingOnRTCThread {
            rtcTrack.addSink(sink)
        }
    }

    /**
     * Removes a previously added sink.
     */
    fun removeSink(sink: AudioTrackSink) {
        executeBlockingOnRTCThread {
            rtcTrack.removeSink(sink)
        }
    }
}
