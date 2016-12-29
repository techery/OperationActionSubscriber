package io.techery.operationsubscriber.action

import io.techery.janet.http.annotations.Status

open class BaseAction {
    @JvmField @Status
    var isSuccess: Boolean = false
}