package com.github.techery.janet.operationsubscriber.view

interface ProgressView<in T> {
    fun showProgress(action: T)

    fun hideProgress()
}