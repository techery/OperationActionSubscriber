package com.github.techery.janet.operationsubscriber.view

interface ErrorView<in T> {
    fun showError(action: T, throwable: Throwable)
}
