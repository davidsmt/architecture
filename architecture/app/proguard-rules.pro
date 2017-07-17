# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/e.villegas.fernandez/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## Android architecture components: Lifecycle
# generated GenericLifecycleObserver and it's empty constructor is considered to be unused by proguard
-keepclasseswithmembers class * implements android.arch.lifecycle.GenericLifecycleObserver {
<init>(...);
}
# keep Lifecycle State and Event enums with fields
-keepclassmembers class android.arch.lifecycle.Lifecycle$* { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
<init>(...);
}

