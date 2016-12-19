package com.github.techery.janet.operationsubscriber.view

interface OperationView<in Action> : ProgressView<Action>, SuccessView<Action>, ErrorView<Action>
