# easyargs

[ ![Download](https://api.bintray.com/packages/udy18rus/maven/easyargs/images/download.svg) ](https://bintray.com/udy18rus/maven/easyargs/_latestVersion)

With this small library you can easy navigate to activity or fragment.
Convenient API allows you to put arguments (into Intent or Bundle) and to get them later.

```kotlin
class MasterActivity: AppCompatActivity {

  fun onItemChosen(item: Model) {
    start<DetailsActivity>(item.asArg())
  }
}

class DetailsActivity: AppCompatActivity {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val item: Model = getArg()
  }
}
```

## Download
Project ```build.gradle```
```groovy
buildscript {
  repositories {
    jcenter()
  }
}
```
Module ```build.gradle```
```groovy
dependencies {
  implementation 'com.github.udy18rus:easyargs:$easyargs_version'
}
```

## Usage
```kotlin
// Start activity. Allowed from context (activity, for example) or fragment
start<ExampleActivity>()

// With an argument
start<ExampleActivity>(item.asArg())
// Get the argument later
val item = getArg<YourModel>()

// Arguments can be the same type, but in this case you have to provide custom key for each one
start<ExampleActivity>(item.asArg("first"), anotherItem.asArg("second"))
// ..later
val anotherItem: YourModel = getArg("second")
val item: YourModel = getArg("first")

// Also you able to modify the Intent
start<ExampleActivity>() {
  type = ...
  data = ...
  ...
}

// If you want to get result from activity, you have to provide a request code
startForResult<ExampleActivity>(123, item.asArg())
// To wrap argument as result just use "asIntent" extension
val result = item.asArg().asIntent()

// Create a fragment
newInstance<ExampleFragment>(item.asArg())
```
### Important
__Only primitives, Serializable or Parcelable arguments allowed.__


## Licence

The MIT License

Copyright (c) 2010-2019 Google, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
