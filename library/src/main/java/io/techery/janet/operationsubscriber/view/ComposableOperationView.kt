package io.techery.janet.operationsubscriber.view

class ComposableOperationView<in T> @JvmOverloads constructor(private val progressView: ProgressView<T>? = null,
                                                              private val successView: SuccessView<T>? = null,
                                                              private val errorView: ErrorView<T>? = null) : OperationView<T> {
    override fun showProgress(action: T) {
        progressView?.showProgress(action)
    }

    override fun hideProgress() {
        progressView?.hideProgress()
    }

    override fun showSuccess(action: T) {
        successView?.showSuccess(action)
    }

    override fun showError(action: T, throwable: Throwable) {
        errorView?.showError(action, throwable)
    }
}