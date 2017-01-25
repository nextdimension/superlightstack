# superlightstack
It is a lightweight library which helps to create a view stack to replace fragments.

-Provides persistent data between views and device rotations.

-Animated transitions between views.

**The library is 100% reflection-free.**

## Usage


## Example

### 1. Create a XML layout and specifiy the view class you wish to use.

```XML
<com.sls.superlight.slates.viewone.ViewOne xmlns:android="http://schemas.android.com/apk/res/android"
                                          android:orientation="vertical"
                                          android:layout_width="match_parent"
                                          android:layout_height="match_parent">
//Add your required layout type here  
</com.sls.superlight.slates.viewone.ViewOne>

```
### 3. Create a new instance of transitioner and provide the base view and view container and pass the bundle;

```java
 public Transitioner transitioner = new Transitioner(this, viewContainer, R.layout.view_one, R.id.ViewOne);
        transitioner.setup(bundle);
```

### 4. Pass the bundle to save the transitioner state

```java
@Override
    protected void onSaveInstanceState(Bundle bundle) {
        Bundle state = transitioner.saveState(bundle);
        super.onSaveInstanceState(state);
    }
```

### 5. When you create your own view Extend from BaseView
 
```java
 public class ViewOne extends BaseView 
```

### 6. Call goTo() to change views, use an animation type and pass any bundle data
 
```java
transitioner.goTo(layoutResID,viewId,bundle,AnimationHandler);
```

### 7. Make sure to create layout file with view ids
 
```XML
 <resources>
    <item name="ViewOne" type="id"/>
    <item name="ViewTwo" type="id"/>
    <item name="ViewThree" type="id"/>
</resources>
```

### 8. Make sure to set the view id in the View
 
```java
 @Override
    protected void onFinishInflate() {
        this.setId(R.id.ViewOne);
        super.onFinishInflate();
    }
```
## Project - build.gradle
```java
	repositories {
 
        maven { url 'https://dl.bintray.com/nextdimension/maven' }
    }
}
```

## App - build.gradle
```java
 compile 'com.github.nextdimension:superorg:superlightstack:0.0.1'
```

## Note

- This is a work in progess.

## Licence

    Copyright 2017 Chris M.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
