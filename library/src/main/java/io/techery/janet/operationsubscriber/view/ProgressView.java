package io.techery.janet.operationsubscriber.view;

public interface ProgressView<T> {
    void showProgress(T action);

    boolean isProgressVisible();

    void hideProgress();

    void onProgressChanged(int progress);
}