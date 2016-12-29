package io.techery.operationsubscriber.view

import android.view.View
import android.widget.TextView
import io.techery.janet.operationsubscriber.view.ErrorView
import io.techery.operationsubscriber.ErrorMessageHandler

class ErrorViewBasedView<T>(private val errorMessageHandler: ErrorMessageHandler,
                            private val view: TextView) : ErrorView<T> {
    override fun showError(action: T, throwable: Throwable) {
        view.visibility = View.VISIBLE
        view.text = errorMessageHandler.resolve(throwable)
    }

    override fun isErrorVisible(): Boolean {
        return view.visibility == View.VISIBLE
    }

    override fun hideError() {
        view.visibility = View.GONE
    }
}