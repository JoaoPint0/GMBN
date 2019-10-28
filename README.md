# Jetpack Samples
Small example uploading videos from GMBN channel implementing Jetpack libraries.

# TODO

Add you API KEY in [Repository][0]

# Architecture

I choose to use MVVM (Model-View-ViewModel) as my project architecture in order to provide a cleaner code, with clear separation between the view, the data and the business logic. I am also exploring a more TDD development making me more confortable at same time with writing tests and being more confortable with this way of thinking.

# Tech-stack

* [Foundation][1] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][2] - Degrade gracefully on older versions of Android.
  * [Android KTX][3] - Write more concise, idiomatic Kotlin code.
* [Architecture][4] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Data Binding][5] - Declaratively bind observable data to UI elements.
  * [Lifecycles][6] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][7] - Build data objects that notify views when the underlying database changes.
  * [Navigation][8] - Handle everything needed for in-app navigation.
  * [Room][9] - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][10] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
* [UI][11] - Details on why and how to use UI Components in your apps - together or separate
  * [Fragment][12] - A basic unit of composable UI.
* Third party
  * [Glide][13] for image loading
  * [Kotlin Coroutines][14] for managing background threads with simplified code and reducing needs for callbacks
  * [Retrofit][15] a type-safe HTTP client for Android and Java

[0]: https://github.com/JoaoPint0/GMBN/blob/master/app/src/main/java/com/endeavour/gmbn/repository/GMBNRepository.kt
[1]: https://developer.android.com/jetpack/components
[2]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[3]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/jetpack/arch/
[5]: https://developer.android.com/topic/libraries/data-binding/
[6]: https://developer.android.com/topic/libraries/architecture/lifecycle
[7]: https://developer.android.com/topic/libraries/architecture/livedata
[8]: https://developer.android.com/topic/libraries/architecture/navigation/
[9]: https://developer.android.com/topic/libraries/architecture/room
[10]: https://developer.android.com/topic/libraries/architecture/viewmodel
[11]: https://developer.android.com/guide/topics/ui
[12]: https://developer.android.com/guide/components/fragments
[13]: https://bumptech.github.io/glide/
[14]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[15]: https://square.github.io/retrofit/
