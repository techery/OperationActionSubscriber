package io.github.techery.operationsubscriber

import io.techery.janet.Command
import io.techery.janet.command.annotations.CommandAction

@CommandAction
class LongRunningValueCommand<T>(private val value: T) : Command<T>() {
    override fun run(callback: CommandCallback<T>?) {
        Thread.sleep(2000)
        callback?.onSuccess(value)
    }
}
