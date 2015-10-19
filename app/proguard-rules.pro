# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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
-optimizationpasses 7
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-verbose
-ignorewarnings
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-dontwarn com.j256.ormlite.**
-dontwarn cn.mama.android.sdk.**
-dontwarn cn.mama.adnroid.service.**
-dontwarn org.json.**
-dontwarn com.google.**
-dontwarn org.apache.**
-dontwarn weibo4android.**
-dontwarn com.umeng.**
-dontwarn com.baidu.**
-dontwarn com.tencent.**
-dontwarn com.iflytek.**
-dontwarn cn.mama.framework.**
-dontwarn cn.mama.activity.web.**
-dontwarn com.igexin.**


-keep class cn.mama.framework.** {*; }
-keep class com.j256.ormlite.** {*; }
-keep class cn.mama.android.sdk.** {*; }
-keep class com.umeng.** {*; }
-keep class com.iflytek.** {*; }
-keep class com.google.** {*; }
-keep class org.json.** {*; }
-keep class demo.Pinyin4jAppletDemo.** {*; }
-keep class oauth.signpost.** {*; }
-keep class com.baidu.** {*; }
-keep class com.tencent.** {*; }
-keep class org.apache.**{*;}
-keep class cn.mama.activity.web.**{*;}
-keep class com.igexin.**{*;}

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*


-keep class * implements java.io.Serializable{*; }

-keep class com.qq.e.** {
public protected *;
}
-keep class com.tencent.gdt.**{
public protected *;
}


-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}

-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

-keep class android.os.SystemProperties { *; }
-keep class android.app.Instrumentation { *; }
-keep class com.lecloud.skin.** { *; }
-keep class com.letvcloud.sdk.** { *; }

-keep class com.google.gson.** {*;}

-keepclassmembers class ** {
    public void on*Event(...);
}
-keepclasseswithmembernames class * {
  native <methods>;
}
-dontwarn  org.eclipse.jdt.annotation.**
-dontwarn android.location.Location

#阿里广告混淆
-dontwarn android.taobao.**
-dontwarn com.taobao.**
-dontwarn com.alimama.mobile.**
-dontwarn android.app.**
-dontwarn android.support.v4.**
-keep class com.taobao.** {*; }
-keep class com.alimama.mobile.**{*; }
-keep class android.taobao.** {*; }

-keep class android.app.**{*;}

#AndroidEven
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}

#测试是用，正式打包可以注释掉
-keepattributes SourceFile,LineNumberTable