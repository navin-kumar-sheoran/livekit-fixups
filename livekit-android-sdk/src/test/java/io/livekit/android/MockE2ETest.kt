package io.livekit.android

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.protobuf.MessageLite
import io.livekit.android.mock.MockPeerConnection
import io.livekit.android.mock.MockWebSocketFactory
import io.livekit.android.mock.dagger.DaggerTestLiveKitComponent
import io.livekit.android.mock.dagger.TestCoroutinesModule
import io.livekit.android.mock.dagger.TestLiveKitComponent
import io.livekit.android.room.Room
import io.livekit.android.room.SignalClientTest
import io.livekit.android.util.toOkioByteString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import livekit.LivekitRtc
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okio.ByteString
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.webrtc.PeerConnection

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
abstract class MockE2ETest : BaseTest() {

    internal lateinit var component: TestLiveKitComponent
    internal lateinit var context: Context
    internal lateinit var room: Room
    internal lateinit var wsFactory: MockWebSocketFactory

    @Before
    fun mocksSetup() {
        context = ApplicationProvider.getApplicationContext()
        component = DaggerTestLiveKitComponent
            .factory()
            .create(context, TestCoroutinesModule(coroutineRule.dispatcher))

        room = component.roomFactory()
            .create(context)
        wsFactory = component.websocketFactory()
    }

    suspend fun connect(joinResponse: LivekitRtc.SignalResponse = SignalClientTest.JOIN) {
        connectSignal(joinResponse)
        connectPeerConnection()
    }

    suspend fun connectSignal(joinResponse: LivekitRtc.SignalResponse) {
        val job = coroutineRule.scope.launch {
            room.connect(
                url = SignalClientTest.EXAMPLE_URL,
                token = "",
            )
        }
        wsFactory.listener.onOpen(wsFactory.ws, createOpenResponse(wsFactory.request))
        simulateMessageFromServer(joinResponse)

        job.join()
    }

    suspend fun getSubscriberPeerConnection() =
        component
            .rtcEngine()
            .getSubscriberPeerConnection() as MockPeerConnection

    suspend fun getPublisherPeerConnection() =
        component
            .rtcEngine()
            .getPublisherPeerConnection() as MockPeerConnection

    suspend fun connectPeerConnection() {
        simulateMessageFromServer(SignalClientTest.OFFER)
        val subPeerConnection = getSubscriberPeerConnection()
        subPeerConnection.moveToIceConnectionState(PeerConnection.IceConnectionState.CONNECTED)
    }

    suspend fun disconnectPeerConnection() {
        val subPeerConnection = component
            .rtcEngine()
            .getSubscriberPeerConnection() as MockPeerConnection
        subPeerConnection.moveToIceConnectionState(PeerConnection.IceConnectionState.FAILED)
    }

    fun createOpenResponse(request: Request): Response {
        return Response.Builder()
            .request(request)
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message("")
            .build()
    }

    /**
     * Simulates receiving [message] from the server
     */
    fun simulateMessageFromServer(message: MessageLite) {
        simulateMessageFromServer(message.toOkioByteString())
    }

    /**
     * Simulates receiving [message] from the server
     */
    fun simulateMessageFromServer(message: ByteString) {
        wsFactory.listener.onMessage(wsFactory.ws, message)
    }
}