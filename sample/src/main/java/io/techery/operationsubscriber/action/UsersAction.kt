package io.techery.operationsubscriber.action

import io.techery.janet.http.annotations.HttpAction
import io.techery.janet.http.annotations.Query
import io.techery.janet.http.annotations.Response
import io.techery.operationsubscriber.model.User
import java.util.*

@HttpAction("/users")
class UsersAction : BaseAction() {
    @JvmField @Query("since")
    val since = 0

    @JvmField @Response
    var response: ArrayList<User>? = null
}