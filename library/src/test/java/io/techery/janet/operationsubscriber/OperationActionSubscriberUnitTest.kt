package io.techery.janet.operationsubscriber

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
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

class OperationActionSubscriberUnitTest {
    private val janetInstance: Janet = janet { addService(CommandActionService()) }

    private lateinit var testCommandPipe: ActionPipe<TestValueCommand>

    private lateinit var mockedProgressView: ProgressView<TestValueCommand>
    private lateinit var mockedSuccessView: SuccessView<TestValueCommand>
    private lateinit var mockedErrorView: ErrorView<TestValueCommand>

    private lateinit var operationActionSubscriber: OperationActionSubscriber<TestValueCommand>

    @Before
    fun setUp() {
        mockedProgressView = mock()
        mockedSuccessView = mock()
        mockedErrorView = mock()

        operationActionSubscriber = OperationActionSubscriber.forView(ComposableOperationView(
                mockedProgressView,
                mockedSuccessView,
                mockedErrorView
        ))
        testCommandPipe = janetInstance.createPipe()
    }

    @Test
    @Throws(Exception::class)
    fun successViewTest() {
        performOperation { TEST_VALUE }

        verify(mockedProgressView).showProgress(any())
        verify(mockedProgressView).hideProgress()
        verify(mockedSuccessView).showSuccess(any())
    }

    @Test
    @Throws(Exception::class)
    fun errorViewTest() {
        performOperation { throw Exception("FAIL") }

        verify(mockedProgressView).showProgress(any())
        verify(mockedProgressView).hideProgress()
        verify(mockedErrorView).showError(any(), any())
    }

    @Test
    @Throws(Exception::class)
    fun repeatableSuccessViewTest() {
        performOperation { TEST_VALUE }

    }

    private fun performOperation(body: () -> String) {
        testCommandPipe
                .createObservable(TestValueCommand(body))
                .subscribe(operationActionSubscriber.create())
    }

    @CommandAction
    private class TestValueCommand(private val body: () -> String) : Command<String>() {
        override fun run(callback: CommandCallback<String>?) {
            Thread.sleep(100)
            callback?.onSuccess(body())
        }
    }

    companion object {
        const val TEST_VALUE = "Value"
    }
}
