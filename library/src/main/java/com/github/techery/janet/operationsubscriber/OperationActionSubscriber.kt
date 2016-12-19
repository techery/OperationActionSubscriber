package com.github.techery.janet.operationsubscriber

import com.github.techery.janet.operationsubscriber.view.OperationView
import io.techery.janet.helper.ActionStateSubscriber

class OperationActionSubscriber<T> private constructor(private val view: OperationView<T>) {

    private var onStartFunc: ((T) -> Unit)? = null

    private var onProgressFunc: ((T, Int) -> Unit)? = null

    private var onSuccessFunc: ((T) -> Unit)? = null

    private var onFailFunc: ((T, Throwable) -> Unit)? = null

    fun onStart(onStart: ((T) -> Unit)? = null): OperationActionSubscriber<T> {
        this.onStartFunc = onStart
        return this
    }

    fun onSuccess(onSuccess: ((T) -> Unit)? = null): OperationActionSubscriber<T> {
        this.onSuccessFunc = onSuccess
        return this
    }

    fun onFail(onFail: ((T, Throwable) -> Unit)? = null): OperationActionSubscriber<T> {
        onFailFunc = onFail
        return this
    }

    fun onProgress(onProgress: ((T, Int) -> Unit)?): OperationActionSubscriber<T> {
        this.onProgressFunc = onProgress
        return this
    }

    fun wrap(): ActionStateSubscriber<T> {
        return with(ActionStateSubscriber<T>()) {
            onStart { action ->
                view.showProgress(action)
                onStartFunc?.let { it(action) }
            }

            onProgress { action, integer ->
                onProgressFunc?.let { it(action, integer) }
            }

            onSuccess { action ->
                view.hideProgress()
                view.showSuccess(action)

                onSuccessFunc?.let { it(action) }
            }

            onFail { action, throwable ->
                view.hideProgress()
                view.showError(action, throwable)

                onFailFunc?.let { it(action, throwable) }
            }
        }
    }

    companion object {
        fun <T : Any> forView(view: OperationView<T>): OperationActionSubscriber<T> {
            return OperationActionSubscriber(view)
        }
    }
}
