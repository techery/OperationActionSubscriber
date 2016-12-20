package io.techery.operationsubscriber.view

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import io.techery.janet.operationsubscriber.view.SuccessView

class SuccessToastView<in T>(private val context: Context,
                             private val messageProvider: (T) -> String) : SuccessView<T> {

    constructor(context: Context, @StringRes msgRes: Int) : this(context, { context.getString(msgRes) })

    constructor(context: Context, message: String) : this(context, { message })

    override fun showSuccess(action: T) {
        Toast.makeText(context.applicationContext, messageProvider(action), Toast.LENGTH_SHORT).show()
    }
}
