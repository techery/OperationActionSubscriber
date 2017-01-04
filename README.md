# OperationActionSubscriber

## Introduction
This library is a helper to execute action(command) using Janet lib
  
  * Library has 3 common interfaces to achive progress/success/error states during execution:
    `ProgressView, SuccessView, ErrorView`
  To have a single entry point library has general interface `java OperationView` (you can implement it directly if you need)

Lets walk through common steps to set all up: 
    
1. Create you own implementation of interfaces mentioned above (ProgressView, SuccessView, ErrorView);
2. Build `java CompositeOperationView` object like:

  ```java
  CompositeOperationView view = new CompositeOperationView(ProgressViewImpl, SuccessViewImpl, ErrorViewImpl);
  ```

3. To subscribe on Janet's action states - janet uses `ActionStateSubscriber` class, library has it's on wrapper around this class - `OperationActionSubscriber`, so we have to attach view to this class:
  
  ```java
  someOperationActionPipe.observe()
      .subscribe(OperationActionSubscriber.forView(operationView())
          .onStart(...)
          .onProgress(...)
          .onFail(...)
        .create()
      );
      
      ...
      
  public OperationView<SomeOperation> operationView() {
      return CompositeOperationView(...);
  }
  ```
4. That's it. Enjoy execution commands using Janet;

##Benefits: the main benefits which is provided is to avoiding "copy-paste" issue in UI, you can implement your UI states for progress/success/error once and you can use it everywhere.

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
