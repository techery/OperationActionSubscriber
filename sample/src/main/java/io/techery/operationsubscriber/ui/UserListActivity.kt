package io.techery.operationsubscriber.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.techery.janet.operationsubscriber.view.ComposableOperationView
import io.techery.janet.operationsubscriber.view.OperationView
import io.techery.operationsubscriber.R
import io.techery.operationsubscriber.action.UsersAction
import io.techery.operationsubscriber.model.User
import io.techery.operationsubscriber.view.ErrorViewBasedView
import io.techery.operationsubscriber.view.ProgressDialogView
import io.techery.operationsubscriber.view.SuccessToastView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class UserListActivity : AppCompatActivity(), UserListPresenter.View {
    override fun onDataReceived(list: List<User>) {
        adapter.userList = list
    }

    private val presenter = UserListPresenter()

    private lateinit var progressDialog: ProgressDialog
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        list.layoutManager = LinearLayoutManager(this)
        list.setHasFixedSize(true)
        list.adapter = adapter
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

    override val operationView: OperationView<UsersAction>
        get() = ComposableOperationView(
                ProgressDialogView(progressDialog),
                SuccessToastView(this, { "Fetched ${it.response?.size} users" }),
                ErrorViewBasedView(ErrorMessageHandler, errorView)
        )

    private class Adapter : RecyclerView.Adapter<Holder>() {
        var userList: List<User> by Delegates.observable(ArrayList()) {
            kProperty: KProperty<*>,
            list: List<User>,
            list1: List<User> ->
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: Holder?, position: Int) {
            holder?.titleTv?.text = userList[position].login
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
            return Holder(LayoutInflater.from(parent?.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false))
        }

        override fun getItemCount(): Int {
            return userList.size
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTv: TextView = view as TextView
    }
}
