package io.techery.janet.operationsubscriber.view

interface SuccessView<in T> {
    fun showSuccess(action: T)
}
