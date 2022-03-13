package com.bytecode.plugin

/**
 * Created by JohnnySwordMan on 2021/4/5
 */
//data class ReinforceExtension(
//    val username: String?,
//    val password: String?,
//    val reinforceToolPath: String?
//)

// Extension用kotlin写一直编译失败
// 需要无参构造函数
open class ReinforceExtension {
     var username: String = ""
     var password: String = ""
     var reinforceToolPath: String = ""

    override fun toString(): String {
        return "username: $username, password: $password, reinforceToolPath: $reinforceToolPath"
    }
}