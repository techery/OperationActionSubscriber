package io.techery.operationsubscriber

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.techery.janet.operationsubscriber.view.ComposableOperationView
import io.techery.janet.operationsubscriber.view.OperationView
import io.techery.operationsubscriber.view.ErrorViewBasedView
import io.techery.operationsubscriber.view.ProgressDialogView
import io.techery.operationsubscriber.view.SuccessToastView
import kotlinx.android.synthetic.main.activity_main.*

class ExampleActivity : AppCompatActivity(), ExamplePresenter.View {
    private val presenter = ExamplePresenter()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()

        presenter.attachView(this)
        presenter.performOperation()
    }

    override fun onStop() {
        super.onStop()

        presenter.detachView()
    }

    override val operationView: OperationView<LongRunningValueCommand<String>>
        get() = ComposableOperationView(
                ProgressDialogView(progressDialog),
                SuccessToastView(this, { "Test result with: " + it.result }),
                ErrorViewBasedView(ErrorMessageHandler, errorView)
        )
}
