package io.techery.janet.operationsubscriber.view;

public interface ErrorView<T> {
    void showError(T action, Throwable throwable);
}
