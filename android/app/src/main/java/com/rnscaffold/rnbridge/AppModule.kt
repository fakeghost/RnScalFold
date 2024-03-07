package com.rnscaffold.rnbridge

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

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
}