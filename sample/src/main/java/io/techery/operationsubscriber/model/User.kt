package io.techery.operationsubscriber.model

import com.google.gson.annotations.SerializedName


class User(var id: Long,
           var login: String,
           @SerializedName("avatar_url")
           var avatar: String,
           @SerializedName("html_url")
           var url: String)