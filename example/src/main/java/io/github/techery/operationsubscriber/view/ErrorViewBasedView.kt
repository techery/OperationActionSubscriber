package io.github.techery.operationsubscriber.view

import android.view.View
import android.widget.TextView
import com.github.techery.janet.operationsubscriber.view.ErrorView
import io.github.techery.operationsubscriber.ErrorMessageHandler

class ErrorViewBasedView<in T>(private val errorMessageHandler: ErrorMessageHandler,
                               private val view: TextView) : ErrorView<T> {
    override fun showError(action: T, throwable: Throwable) {
        view.visibility = View.VISIBLE
        view.text = errorMessageHandler.resolve(throwable)
    }
}