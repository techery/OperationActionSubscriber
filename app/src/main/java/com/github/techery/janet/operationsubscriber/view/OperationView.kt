package com.github.techery.janet.operationsubscriber.view

interface OperationView<in Action> {
    fun showProgress(action: Action)

    fun hideProgress()

    fun showSuccess(action: Action)

    fun showError(action: Action, throwable: Throwable)
}
