package io.techery.operationsubscriber.view

import android.app.ProgressDialog
import io.techery.janet.operationsubscriber.view.ProgressView

class ProgressDialogView<T>(private val dialog: ProgressDialog) : ProgressView<T> {
    override fun showProgress(action: T) {
        dialog.show()
    }

    override fun isProgressVisible(): Boolean {
        return dialog.isShowing
    }

    override fun hideProgress() {
        dialog.dismiss()
    }
}
