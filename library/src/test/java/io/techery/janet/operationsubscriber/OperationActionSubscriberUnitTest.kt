package io.techery.janet.operationsubscriber

import io.techery.janet.Command
import io.techery.janet.CommandActionService
import io.techery.janet.Janet
import io.techery.janet.command.annotations.CommandAction
import io.techery.janet.kotlin.janet
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import rx.observers.TestSubscriber

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class OperationActionSubscriberUnitTest {
    private val janetInstance: Janet = janet { addService(CommandActionService()) }

    private lateinit var testSubscriber: TestSubscriber<Long>

    @Before
    fun setUp() {

    }

    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @CommandAction
    private class TestValueCommand : Command<String>() {
        override fun run(callback: CommandCallback<String>?) {

        }
    }
}
