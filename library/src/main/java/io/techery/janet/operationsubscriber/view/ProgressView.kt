package io.techery.janet.operationsubscriber.view

interface ProgressView<in T> {
    fun showProgress(action: T)

    fun hideProgress()
}