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

    public ComposableOperationView(ProgressView<T> progressView) {
        this(progressView, null, null);
    }

    public ComposableOperationView(SuccessView<T> successView) {
        this(null, successView, null);
    }

    public ComposableOperationView(ErrorView<T> errorView) {
        this(null, null, errorView);
    }

    public ComposableOperationView(ProgressView<T> progressView, SuccessView<T> successView) {
        this(progressView, successView, null);
    }

    public ComposableOperationView(SuccessView<T> successView, ErrorView<T> errorView) {
        this(null, successView, errorView);
    }

    public ComposableOperationView(ProgressView<T> progressView, ErrorView<T> errorView) {
        this(progressView, null, errorView);
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

    @Override
    public void onProgressChanged(int progress) {

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