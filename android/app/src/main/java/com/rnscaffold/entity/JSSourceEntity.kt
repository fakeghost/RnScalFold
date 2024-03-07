package com.rnscaffold.entity

class JSSourceEntity {
    /**
     * 描述
     */
    var description: String? = null

    /**
     * 是否强制更新
     */
    var forceUpdate: Boolean? = null
    var id: Long? = null

    /**
     * 模块名称
     */
    var module: String? = null

    /**
     * 应用名称
     */
    var name: String? = null

    /**
     * app版本
     */
    var nativeApiLevel: Long? = null

    /**
     *
     * 应用平台
     */
    var platform: String? = null

    /**
     * 包上传的地址
     */
    var url: String? = null

    /**
     * js包md5
     */
    var version: String? = null
}
