## Motivation


## Introduction
This library is a helper to show operation execution steps using `Janet` lib
  
  * In accordance to the structure of `Janet` library execution, it has 3 common interfaces to handle operation states: `ActionState.Progress, ActionState.Success, ActionState.Error`
    It includes states of operation execution they are: in progress - supported by the `ProgressView` inteface, success supported by the `SuccessView` interface and error state - supported by the `ErrorView` interface. The library helps to handle all of the above states and show them on the UI impelementation of your choice. These interfaces tell you what contract you must perform for operation states to be handled properly.
  
  * These 3 interface describe concrete parts of progress of operations and combined, these 3 interfaces make up operation view. The developer can make his own realisation as this is an interface, but the library offers it's own common realisation (`CompositeOperationView`) if you wish to you use it.

Lets walk through common steps to set all up: 
    
1. Create your own implementation of interfaces mentioned above `(ProgressView, SuccessView, ErrorView)`;
2. Build `CompositeOperationView` object like:

  ```java
  CompositeOperationView view = new CompositeOperationView(ProgressViewImpl, SuccessViewImpl, ErrorViewImpl);
  ```

3. To subscribe on Janet's action states - janet uses `ActionStateSubscriber` class, library has it's on wrapper around this class - `OperationActionSubscriber`, so we have to attach OperationView object to this class:
  
  ```java
  someOperationActionPipe.observe()
      .subscribe(OperationActionSubscriber.forView(operationView())
          .onStart(...)
          .onProgress(...)
	      .onSuccess(...)
          .onFail(...)
        .create()
      );
      
      ...
      
  public OperationView<SomeOperation> operationView() {
      return new CompositeOperationView(...);
  }
  ```
4. That's it. Enjoy execution commands using Janet;

##Benefit:
The main benefits which is provided by this library - avoid "copy-paste" issue in UI, you can implement your UI states for progress/success/error once and you can use it everywhere.

##Download

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

[![](https://www.jitpack.io/v/techery/OperationActionSubscriber.svg)](https://www.jitpack.io/#techery/OperationActionSubscriber)
