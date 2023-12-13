package io.livekit.android

import com.google.common.util.concurrent.MoreExecutors
import io.livekit.android.coroutines.TestCoroutineRule
import io.livekit.android.util.LoggingRule
import io.livekit.android.webrtc.peerconnection.overrideExecutorAndDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {
    // Uncomment to enable logging in tests.
    @get:Rule
    var loggingRule = LoggingRule()

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var coroutineRule = TestCoroutineRule()

    @Before
    fun setupRTCThread() {
        overrideExecutorAndDispatcher(
            executorService = MoreExecutors.newDirectExecutorService(),
            dispatcher = coroutineRule.dispatcher,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun runTest(testBody: suspend TestScope.() -> Unit) = coroutineRule.scope.runTest(testBody = testBody)
}
