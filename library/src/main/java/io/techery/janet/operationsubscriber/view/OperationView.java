package io.techery.janet.operationsubscriber.view;

public interface OperationView<Action> extends ProgressView<Action>,
        SuccessView<Action>, ErrorView<Action> {
}
