package com.rnscaffold.rnbridge

import android.content.Intent
import android.net.Uri
import com.facebook.react.bridge.*
import com.rnscaffold.MainApplication

class AppModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule() {
    private var reactContextIn: ReactApplicationContext = reactContext


    override fun getName(): String {
        return "APPUtils"
    }

    override fun getConstants(): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()

        val packageName = reactContextIn.packageName

        map["environment"] = ""
        map["AndroidPackageName"] = packageName

        return  map
    }

    @ReactMethod
    fun jumpToPhonePage(obj: ReadableMap, promise: Promise) {
        val passObj = Arguments.createMap()
        passObj.putString("message", "200");
        val intent = Intent()
        intent.setAction(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:" + obj.getString("phone")))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainApplication.getInstance().startActivity(intent);
        promise.resolve(passObj);
    }
}