package io.techery.janet.operationsubscriber.view;

public class ComposableOperationView<T> implements OperationView<T> {
    private ProgressView<T> progressView;

    private SuccessView<T> successView;

    private ErrorView<T> errorView;

    public ComposableOperationView(ProgressView<T> progressView,
                                   SuccessView<T> successView,
                                   ErrorView<T> errorView) {
        this.progressView = progressView;
        this.successView = successView;
        this.errorView = errorView;
    }

    public void showProgress(T action) {
        if (progressView != null) {
            progressView.showProgress(action);
        }
    }

    public void hideProgress() {
        if (progressView != null) {
            progressView.hideProgress();
        }
    }

    public void showSuccess(T action) {
        if (successView != null) {
            successView.showSuccess(action);
        }
    }

    public void showError(T action, Throwable throwable) {
        if (errorView != null) {
            errorView.showError(action, throwable);
        }
    }
}