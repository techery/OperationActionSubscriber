package io.techery.operationsubscriber

import android.app.Application
import com.google.gson.Gson
import io.techery.janet.ActionPipe
import io.techery.janet.CommandActionService
import io.techery.janet.HttpActionService
import io.techery.janet.gson.GsonConverter
import io.techery.janet.kotlin.janet
import io.techery.janet.okhttp3.OkClient
import io.techery.operationsubscriber.action.UsersAction
import okhttp3.OkHttpClient

class App : Application() {
    private lateinit var usersPipe: ActionPipe<UsersAction>

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private val API_URL = "https://api.github.com"

        val janet = janet {
            addService(CommandActionService())
            addService(HttpActionService("", OkClient(OkHttpClient()), GsonConverter(Gson())))
        }

    }
}