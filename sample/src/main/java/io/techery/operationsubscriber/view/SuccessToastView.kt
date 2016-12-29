package io.techery.operationsubscriber.view

import android.content.Context
import android.support.annotation.StringRes
import android.view.View
import android.widget.Toast
import io.techery.janet.operationsubscriber.view.SuccessView

class SuccessToastView<T>(private val context: Context,
                          private val messageProvider: (T) -> String) : SuccessView<T> {
    private lateinit var toast: Toast

    constructor(context: Context, @StringRes msgRes: Int) : this(context, { context.getString(msgRes) })

    constructor(context: Context, message: String) : this(context, { message })

    override fun showSuccess(action: T) {
        toast = Toast.makeText(context.applicationContext, messageProvider(action), Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun isSuccessVisible(): Boolean {
        return toast.view.visibility == View.VISIBLE
    }

    override fun hideSuccess() {
        toast.cancel()
    }
}
