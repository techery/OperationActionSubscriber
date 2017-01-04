package io.techery.operationsubscriber

import android.app.Application
import com.google.gson.Gson
import io.techery.janet.HttpActionService
import io.techery.janet.gson.GsonConverter
import io.techery.janet.kotlin.janet
import io.techery.janet.okhttp3.OkClient
import okhttp3.OkHttpClient

class App : Application() {
    companion object {
        private val API_URL = "https://api.github.com"

        val janet = janet {
            addService(HttpActionService(API_URL, OkClient(OkHttpClient()), GsonConverter(Gson())))
        }
    }
}