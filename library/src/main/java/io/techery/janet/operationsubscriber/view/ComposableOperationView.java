package io.techery.janet.operationsubscriber.view;

import org.jetbrains.annotations.Nullable;

public class ComposableOperationView<T> implements OperationView<T> {
    @Nullable
    private ProgressView<T> progressView;

    @Nullable
    private SuccessView<T> successView;

    @Nullable
    private ErrorView<T> errorView;

    public ComposableOperationView(@Nullable ProgressView<T> progressView,
                                   @Nullable SuccessView<T> successView,
                                   @Nullable ErrorView<T> errorView) {
        this.progressView = progressView;
        this.successView = successView;
        this.errorView = errorView;
    }

    public void showProgress(T action) {
        if (progressView != null) {
            progressView.showProgress(action);
        }
    }

    @Override
    public boolean isProgressVisible() {
        return progressView != null && progressView.isProgressVisible();
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

    @Override
    public boolean isSuccessVisible() {
        return successView != null && successView.isSuccessVisible();
    }

    @Override
    public void hideSuccess() {
        if (successView != null) {
            successView.hideSuccess();
        }
    }

    public void showError(T action, Throwable throwable) {
        if (errorView != null) {
            errorView.showError(action, throwable);
        }
    }

    @Override
    public boolean isErrorVisible() {
        return errorView != null && errorView.isErrorVisible();
    }

    @Override
    public void hideError() {
        if (errorView != null) {
            errorView.hideError();
        }
    }
}