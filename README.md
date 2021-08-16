# NestedSpinner-Android

[![Maven Central](https://img.shields.io/maven-central/v/net.wwwhackcom/nested-spinner-android)](https://search.maven.org/artifact/net.wwwhackcom/nested-spinner-android/1.0.0/aar)
[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)
[![CI Status](https://travis-ci.com/wwwhackcom/NestedSpinner-Android.svg?branch=master)](https://travis-ci.com/wwwhackcom/NestedSpinner-Android)

## Overview

**NestedSpinner-Android** is a spinner control written in kotlin for Android, which allows two levels nested spinner: `groupItem` which can individually be expanded | collapsed to show | hide its children `subItems`. The key classes include:

- `NestedSpinnerView`
- `NestedSpinnerPopupView`
- `AbstractNestedSpinnerAdapter`
- `NestedSpinnerAdapter`

## Installation

### Gradle
To install NestedSpinner-Android into your Android project, simply add the dependency:
```groovy
implementation 'net.wwwhackcom:nested-spinner-android:1.0.1'
```

### Maven

```xml
<dependency>
  <groupId>net.wwwhackcom</groupId>
  <artifactId>nested-spinner-android</artifactId>
  <version>1.0.1</version>
  <type>aar</type>
</dependency>
```

## Usage
### Code Implementation

*For a working implementation of this project see the example code.*

```kotlin
    val nestedSpinnerAdapter = BaseNestedSpinnerAdapter(this, createDataSource())
        nestedSpinner.setNestedAdapter(nestedSpinnerAdapter)
        nestedSpinner.onItemSelectedListener = { subItem ->
            run {
                if (subItem is NestedSpinnerData) {
                    Log.d("MainActivity", "subItem: " + subItem.data)
                }
            }
        }
```

The function `createDataSource` is just an example for populating data, you can use your own data to fill into the NestedSpinner adapter.

### Style
Set the style property of the view, please see the custom attributes of the class `NestedSpinnerStyle`(https://github.com/wwwhackcom/NestedSpinner-Android/blob/master/NestedSpinner/src/main/java/com/nickwang/nestedspinner/NestedSpinnerStyle.kt) for more details,

### Customization
You can also customise your own groupItem or subItem cell, just extend the class `AbstractNestedSpinnerAdapter` and override functions, pls see the example code.

## Contribution
### Pull requests are welcome!
Feel free to contribute to NestedSpinner-Android.

## Author

Nick Wang, wwwhackcom@qq.com

## License

NestedSpinner is available under the Apache License, Version 2.0 (the "License");


    Copyright 2021 Nick Wang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.