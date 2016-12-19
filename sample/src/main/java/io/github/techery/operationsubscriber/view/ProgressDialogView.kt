package io.github.techery.operationsubscriber.view

import android.app.ProgressDialog
import com.github.techery.janet.operationsubscriber.view.ProgressView

class ProgressDialogView<in T>(private val dialog: ProgressDialog) : ProgressView<T> {
    override fun showProgress(action: T) {
        dialog.show()
    }

    override fun hideProgress() {
        dialog.dismiss()
    }
}
