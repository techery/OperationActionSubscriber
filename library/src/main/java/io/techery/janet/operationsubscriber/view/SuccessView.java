package io.techery.janet.operationsubscriber.view;

public interface SuccessView<T> {
    void showSuccess(T action);

    boolean isSuccessVisible();

    void hideSuccess();
}