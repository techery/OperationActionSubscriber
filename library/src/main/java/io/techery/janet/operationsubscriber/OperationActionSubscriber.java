package io.techery.janet.operationsubscriber;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.techery.janet.helper.ActionStateSubscriber;
import io.techery.janet.operationsubscriber.view.OperationView;
import rx.functions.Action1;
import rx.functions.Action2;

public class OperationActionSubscriber<T> {
    @NotNull
    private OperationView<T> operationView;

    private boolean repeatable;

    @Nullable
    private Action1<T> onStartAction;
    @Nullable
    private Action2<T, Integer> onProgressAction;
    @Nullable
    private Action1<T> onSuccessAction;

    @Nullable
    private Action2<T, Throwable> onFailAction;

    public static <T> OperationActionSubscriber<T> forView(OperationView<T> operationView,
                                                           boolean repeatable) {
        return new OperationActionSubscriber<>(operationView, repeatable);
    }

    public static <T> OperationActionSubscriber<T> forView(OperationView<T> operationView) {
        return forView(operationView, true);
    }

    private OperationActionSubscriber(@NotNull OperationView<T> operationView,
                                      boolean repeatable) {
        this.operationView = operationView;
        this.repeatable = repeatable;
    }

    public OperationActionSubscriber<T> onStart(@Nullable Action1<T> action) {
        this.onStartAction = action;
        return this;
    }

    public OperationActionSubscriber<T> onProgress(@Nullable Action2<T, Integer> action) {
        this.onProgressAction = action;
        return this;
    }

    public OperationActionSubscriber<T> onSuccess(@Nullable Action1<T> action) {
        this.onSuccessAction = action;
        return this;
    }

    public OperationActionSubscriber<T> onFail(@Nullable Action2<T, Throwable> action) {
        this.onFailAction = action;
        return this;
    }

    public ActionStateSubscriber<T> create() {
        return new ActionStateSubscriber<T>()
                .onStart(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (repeatable) {
                            hideNotifications();
                        }

                        if (onStartAction != null) {
                            onStartAction.call(t);
                        }
                        if (!operationView.isProgressVisible()) {
                            operationView.showProgress(t);
                        }
                        if (onProgressAction != null) {
                            onProgressAction.call(t, 0);
                        }
                    }
                })
                .onProgress(new Action2<T, Integer>() {
                    @Override
                    public void call(T t, Integer integer) {
                        if (!operationView.isProgressVisible()) {
                            operationView.showProgress(t);
                        }
                        operationView.onProgressChanged(integer);

                        if (onProgressAction != null) {
                            onProgressAction.call(t, integer);
                        }
                    }
                })
                .onSuccess(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        operationView.hideProgress();
                        operationView.showSuccess(t);

                        if (onSuccessAction != null) {
                            onSuccessAction.call(t);
                        }
                    }
                })
                .onFail(new Action2<T, Throwable>() {
                    @Override
                    public void call(T t, Throwable throwable) {
                        operationView.hideProgress();
                        operationView.showError(t, throwable);

                        if (onFailAction != null) {
                            onFailAction.call(t, throwable);
                        }
                    }
                });
    }

    private void hideNotifications() {
        if (operationView.isSuccessVisible()) {
            operationView.hideSuccess();
        }
        if (operationView.isErrorVisible()) {
            operationView.hideError();
        }
    }
}
