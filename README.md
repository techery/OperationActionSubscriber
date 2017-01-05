[![](https://www.jitpack.io/v/techery/OperationActionSubscriber.svg)](https://www.jitpack.io/#techery/OperationActionSubscriber)

# Motivation
   Almost every application contains operations that are executed during the its work, such as access to server, getting info from server, working with databases. To resolve such operations, we show the user notifications regarding the progress of this operation and its result. If the application is big, we show such kind of notifications many times, the code that is responsible for showing these notifications is replicated. Our library reduces the amount of code and simplifies it by replacing such basic actions as operations. 

# Introduction
This library is a helper to show operation execution steps in User Inteface of your choice, using `Janet` lib
  
In accordance to the structure of `Janet` library execution, it has 3 common interfaces to handle operation states: `ActionState.Progress, ActionState.Success, ActionState.Fail`
    
It includes states of operation execution they are: in progress - supported by the `ProgressView` inteface, success supported by the `SuccessView` interface and error state - supported by the `ErrorView` interface. The library helps to handle all of the above states and show them on the UI impelementation of your choice. These interfaces tell you what contract you must perform for operation states to be handled properly.

# Lets walk through the general steps to set it all up: 
    
1. Create your own implementation of interfaces mentioned above:
  - `ProgreeView`: 
  ```kotlin
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
  ```
  - `SuccessView`:
 ```kotlin
 class SuccessToastView<T>(private val context: Context,
                          private val messageProvider: (T) -> String) : SuccessView<T> {
    private var toast: Toast? = null

    constructor(context: Context, @StringRes msgRes: Int) : this(context, { context.getString(msgRes) })

    constructor(context: Context, message: String) : this(context, { message })

    override fun showSuccess(action: T) {
        toast = Toast.makeText(context.applicationContext, messageProvider(action), Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun isSuccessVisible(): Boolean {
        return toast?.view?.visibility == View.VISIBLE
    }

    override fun hideSuccess() {
        toast?.cancel()
    }
}
 ```
  - `ErrorView`:
 ```kotlin
 class ErrorViewBasedView<T>(private val errorMessageHandler: ErrorMessageHandler,
                            private val view: TextView) : ErrorView<T> {
    override fun showError(action: T, throwable: Throwable) {
        view.visibility = View.VISIBLE
        view.text = errorMessageHandler.resolve(throwable)
    }

    override fun isErrorVisible(): Boolean {
        return view.visibility == View.VISIBLE
    }

    override fun hideError() {
        view.visibility = View.GONE
    }
}
 ```

2. Build `CompositeOperationView` object using these interface impementations, like:
  
  `UserListActivity.kt`
  
  ```kotlin
  override val operationView: OperationView<UsersAction>
        get() = ComposableOperationView(
                ProgressDialogView(progressDialog),
                SuccessToastView(this, { "Fetched ${it.response?.size} users" }),
                ErrorViewBasedView(ErrorMessageHandler, errorView)
        )
  ```
3. To subscribe to `Janet`'s action states - `Janet` uses `ActionStateSubscriber` class, the library has it's on wrapper around this class - `OperationActionSubscriber`, so we have to attach OperationView object to this class, usually it happends in `Presenter`, `ViewModel`, etc:
  
  `UserListPresenter.kt`
  
  ```kotlin
usersPipe.observe()
         .subscribe(OperationActionSubscriber.forView(view.operationView)
                        .onStart {
                            Log.d(TAG, "onStart " + it.javaClass.name)
                        }
                        .onSuccess {
                            Log.d(TAG, "onSuccess " + it.javaClass.name + " result: " + it.response)
                            view.onDataReceived(it.response!!)
                        }
                        .onFail { usersAction, throwable ->
                            Log.d(TAG, "onFail " + usersAction.javaClass.name)
                        }
                        .create())
  ```
4. That's it. Enjoy the execution commands using `Janet`;

# Benefit:
The main benefits that are provided by this library - avoid "copy-paste" issue in UI, you can implement your UI states for progress/success/error once and you can use it everywhere.

# Download

Grab via Maven
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
        <url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
    <groupId>com.github.techery</groupId>
    <artifactId>OperationActionSubscriber</artifactId>
    <version>{latestVersion}</version>
</dependency>
```
or Gradle:
```groovy
repositories {
    ...
    maven { url "https://jitpack.io" }
}
dependencies {
    compile 'com.github.techery:OperationActionSubscriber:{latestVersion}'
}
```

## License

    Copyright (c) 2016 Techery

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
