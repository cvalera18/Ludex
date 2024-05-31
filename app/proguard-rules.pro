# Preserve generic type information for Gson
-keepattributes Signature
-keepattributes *Annotation*

# Preserve Gson specific classes that use reflection
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class com.google.gson.internal.** { *; }
-keep class com.google.gson.** { *; }

# Preserve classes with the @JsonAdapter annotation
-keep class * {
    @com.google.gson.annotations.JsonAdapter <fields>;
}

# Preserve classes with the @Expose annotation
-keep class * {
    @com.google.gson.annotations.Expose <fields>;
}

# Firebase Auth
-keep class com.google.firebase.auth.** { *; }
-dontwarn com.google.firebase.auth.**

# Firebase Database
-keep class com.google.firebase.database.** { *; }
-dontwarn com.google.firebase.database.**

# Google Sign-In
-keep class com.google.android.gms.auth.api.signin.** { *; }
-dontwarn com.google.android.gms.auth.api.signin.**

# Preserve classes needed for Google Sign-In
-keep class com.google.android.gms.common.api.internal.** { *; }
-keep class com.google.android.gms.common.internal.** { *; }
-keep class com.google.android.gms.common.** { *; }
-dontwarn com.google.android.gms.common.**

# Preserve all fields for the Game model class
-keepclassmembers class com.cvalera.ludex.domain.model.Game {
    *;
}

# Room specific rules

# Keep the TypeConverters annotated classes
-keep @androidx.room.TypeConverters class * {
    *;
}
# Exclude the Converters class from obfuscation
#-keep class com.cvalera.ludex.data.datasource.local.database.converters.Converters {
#    *;
#}

# Preserve public constructors
-keepclassmembers class com.cvalera.ludex.domain.model.Game {
    public <init>();
}

# Firebase Installations
-keep class com.google.firebase.installations.** { *; }
-dontwarn com.google.firebase.installations.**


# Keep ViewModel classes
-keep class com.cvalera.ludex.presentation.**ViewModel { *; }

# Keep Glide classes
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

-keepclassmembers class proto.** { *; }
-keepclassmembernames class com.google.protobuf.** {
    public static ** newBuilder();
}

# Suppress warnings for Groovy related classes
-dontwarn groovy.lang.Closure
-dontwarn groovy.lang.GroovyObject
-dontwarn groovy.lang.MetaClass
-dontwarn groovy.transform.Generated
-dontwarn org.codehaus.groovy.reflection.ClassInfo
-dontwarn org.codehaus.groovy.runtime.GeneratedClosure
-dontwarn org.codehaus.groovy.runtime.ScriptBytecodeAdapter
-dontwarn org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
-dontwarn org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
-dontwarn org.codehaus.groovy.transform.ImmutableASTTransformation
-dontwarn org.codehaus.plexus.component.annotations.Component
-dontwarn org.eclipse.ui.IStartup
-dontwarn org.gradle.api.Action
-dontwarn org.gradle.api.DefaultTask
-dontwarn org.gradle.api.Named
-dontwarn org.gradle.api.Plugin
-dontwarn org.gradle.api.artifacts.dsl.DependencyHandler
-dontwarn org.gradle.api.tasks.CacheableTask
-dontwarn org.gradle.util.GradleVersion
-dontwarn org.slf4j.Logger
-dontwarn org.slf4j.LoggerFactory