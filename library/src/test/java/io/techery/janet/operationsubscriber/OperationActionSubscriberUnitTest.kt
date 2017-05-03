package io.techery.janet.operationsubscriber

import com.nhaarman.mockito_kotlin.isA
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.techery.janet.ActionPipe
import io.techery.janet.Command
import io.techery.janet.CommandActionService
import io.techery.janet.Janet
import io.techery.janet.command.annotations.CommandAction
import io.techery.janet.kotlin.createPipe
import io.techery.janet.kotlin.janet
import io.techery.janet.operationsubscriber.view.ComposableOperationView
import io.techery.janet.operationsubscriber.view.ErrorView
import io.techery.janet.operationsubscriber.view.ProgressView
import io.techery.janet.operationsubscriber.view.SuccessView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import rx.schedulers.Schedulers

@RunWith(MockitoJUnitRunner::class)
class OperationActionSubscriberUnitTest {
    private val janetInstance: Janet = janet { addService(CommandActionService()) }

    private lateinit var testCommandPipe: ActionPipe<TestValueCommand>

    @Mock
    lateinit var mockedProgressView: ProgressView<TestValueCommand>
    @Mock
    lateinit var mockedSuccessView: SuccessView<TestValueCommand>
    @Mock
    lateinit var mockedErrorView: ErrorView<TestValueCommand>

    private lateinit var operationActionSubscriber: OperationActionSubscriber<TestValueCommand>

    @Before
    fun setUp() {
        operationActionSubscriber = OperationActionSubscriber.forView(ComposableOperationView(
                mockedProgressView,
                mockedSuccessView,
                mockedErrorView
        ))
        testCommandPipe = janetInstance.createPipe(Schedulers.immediate())
    }

    @Test
    @Throws(Exception::class)
    fun successViewTest() {
        performOperation { TEST_VALUE }

        verify(mockedProgressView).showProgress(isA())
        verify(mockedProgressView).onProgressChanged(0)
        verify(mockedProgressView).hideProgress()
        verify(mockedSuccessView).showSuccess(isA())
    }

    @Test
    @Throws(Exception::class)
    fun errorViewTest() {
        performOperation { throw Exception("FAIL") }

        verify(mockedProgressView).showProgress(isA())
        verify(mockedProgressView).onProgressChanged(0)
        verify(mockedProgressView).hideProgress()
        verify(mockedErrorView).showError(isA(), isA())
    }

    @Test
    @Throws(Exception::class)
    fun repeatableSuccessViewTest() {
        whenever(mockedSuccessView.isSuccessVisible).thenReturn(true)

        performOperation { TEST_VALUE } // repeat the same operation to hide previous state
        verify(mockedSuccessView).hideSuccess()
    }

    @Test
    @Throws(Exception::class)
    fun repeatableErrorViewTest() {
        whenever(mockedErrorView.isErrorVisible).thenReturn(true)

        performOperation { TEST_VALUE }
        verify(mockedErrorView).hideError()
    }

    private fun performOperation(body: () -> String) {
        testCommandPipe
                .createObservable(TestValueCommand(body))
                .subscribe(operationActionSubscriber.create())
    }

    @CommandAction open class TestValueCommand(private val body: () -> String) : Command<String>() {
        override fun run(callback: CommandCallback<String>?) {
            callback?.onProgress(0)
            Thread.sleep(100)
            callback?.onSuccess(body())
        }
    }

    companion object {
        const val TEST_VALUE = "Value"
    }
}