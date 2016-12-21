package io.techery.operationsubscriber

import android.util.Log
import io.techery.janet.ActionPipe
import io.techery.janet.CommandActionService
import io.techery.janet.kotlin.createPipe
import io.techery.janet.kotlin.janet
import io.techery.janet.operationsubscriber.OperationActionSubscriber
import io.techery.janet.operationsubscriber.view.OperationView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ExamplePresenter {
    private val longRunningValueCommandPipe: ActionPipe<LongRunningValueCommand<String>> = janet.createPipe(Schedulers.io())
    private lateinit var subscription: Subscription

    private var view: View? = null

    fun attachView(view: View) {
        this.view = view

        subscription = longRunningValueCommandPipe
                .observe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OperationActionSubscriber.forView(view.operationView)
                        .onStart {
                            Log.d(TAG, "onStart " + it.javaClass.name)
                        }
                        .onSuccess {
                            Log.d(TAG, "onSuccess " + it.javaClass.name + " result: " + it.result)
                        }
                        .onFail { longRunningValueCommand, throwable ->
                            Log.d(TAG, "onFail " + longRunningValueCommand.javaClass.name)
                        }
                        .create())
    }

    fun detachView() {
        if (!subscription.isUnsubscribed) subscription.unsubscribe()
        view = null
    }

    fun performOperation() {
        longRunningValueCommandPipe.send(LongRunningValueCommand("Example string"))
    }

    interface View {
        val operationView: OperationView<LongRunningValueCommand<String>>
    }

    companion object {
        val TAG = ExamplePresenter::class.simpleName

        val janet = janet { addService(CommandActionService()) }
    }
}
