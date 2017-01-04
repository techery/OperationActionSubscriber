package io.techery.operationsubscriber.ui

import android.util.Log
import io.techery.janet.ActionPipe
import io.techery.janet.kotlin.createPipe
import io.techery.janet.operationsubscriber.OperationActionSubscriber
import io.techery.janet.operationsubscriber.view.OperationView
import io.techery.operationsubscriber.App
import io.techery.operationsubscriber.action.UsersAction
import io.techery.operationsubscriber.model.User
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class UserListPresenter {
    private lateinit var subscription: Subscription

    private val usersPipe: ActionPipe<UsersAction> = App.janet.createPipe(Schedulers.io())

    private var view: View? = null

    fun attachView(view: View) {
        this.view = view

        subscription = usersPipe
                .observe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(OperationActionSubscriber.forView(view.operationView)
                        .onStart {
                            Log.d(TAG, "onStart " + it.javaClass.name)
                        }
                        .onSuccess {
                            Log.d(TAG, "onSuccess " + it.javaClass.name + " result: " + it.response)
                            view.onDataReceived(it.response!!)
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
        usersPipe.send(UsersAction())
    }

    interface View {
        fun onDataReceived(list: List<User>)

        val operationView: OperationView<UsersAction>
    }

    companion object {
        val TAG = UserListPresenter::class.simpleName
    }
}
