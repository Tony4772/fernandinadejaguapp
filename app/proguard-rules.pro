# ProGuard / R8 Rules for Fernandina de Jagua Chocolates

# Credential Manager & Google Identity
-keep class androidx.credentials.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }
-keepclassmembers class * implements androidx.credentials.Credential { *; }

# Firebase Auth & Google Sign-In
-keep class com.google.firebase.auth.** { *; }
-keep class com.google.android.gms.auth.api.** { *; }
-keep class com.google.android.gms.common.** { *; }

# Gson / Moshi models if used
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
