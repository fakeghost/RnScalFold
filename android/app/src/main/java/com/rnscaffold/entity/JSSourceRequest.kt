package com.rnscaffold.entity

class JSSourceRequest {
    var env = "";
    var name = "";
    var nativeApiLevel = 10001L;
    var platform = "";
    var modules = mutableListOf<String>("main");
}