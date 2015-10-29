Freighter
============

Generates broilerplate code for intent and bundle builders and parsers. Autogeneration of this code at compile time ensures type-safety.
Here's an example of this in Action.

```java
@Freighter
class BookDetailActivity extends Activity {
  @Cargo int id;
  @Cargo String name;
  @Cargo String author;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_detail);
    FreighterBookDetailActivity.inject(this);
    // TODO Use fields...
  }
}
```

After defining the annotating the activity a class 'FreighterBookDetailActivity' is generated. The above activity can be started as follows:

```java
  FreighterBookDetailActivity.supply()
    .id(1)
    .name("Hitchhiker's guide to galaxy")
    .author("Douglas Adams")
    .start();
```

If in future if the field id in BookDetailActivity for some reason has to be changed to type String then the class FreighterBookDetailActivity is regenerated and all the places where an int is being passed to the BookDetailActivity will throw a compile time error compared to the run time error it would have lead to in the normal scenario.
The process for annotating Fragments and service is similar, but instead of .start() method fragment's builder will have .create() method.
Here's an example for a fragment

```java
@Freighter
class BookDetailsFragment extends Fragment {
  @Cargo int id;
  @Cargo String book;
  @Cargo String author;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    FreighterBookDetailFragment.inject(this);
    // TODO inflate and return the view and use the fields
  }
}
```

The above fragment can be created as follows:

```java
BookDetailsFragment fragment = FreighterBookDetailsFragment.supply()
                                  .id(1)
                                  .name("Harry Potter")
                                  .author("J. K. Rowling")
                                  .create();
```
This would create a BookDetailsFragment that have arguments set to the above values.



Download
--------
Gradle:
```groovy
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}

apply plugin: 'com.neenbedankt.android-apt'

dependencies {
  compile 'in.workarounds.freighter:freighter-annotations:0.0.1'
  apt 'in.workarounds.freighter:freighter-compiler:0.0.1'
}
```

License
-------

    Copyright 2015 Workarounds

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


