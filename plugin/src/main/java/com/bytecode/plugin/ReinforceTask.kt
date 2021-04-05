package com.bytecode.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

/**
 * Created by JohnnySwordMan on 2021/4/5
 *
 * 需要加@Inject注解，这样才能接住执行project.tasks.create方法时传入的参数
 *
 * 用Kotlin写，一直报这个错误，好像是没有无参构造函数
 * A problem occurred configuring project ':app'.
    > Could not create task ':app:reinforcedebug'.
    > Could not create task of type 'ReinforceTask'.
    > Class ReinforceTask is final.
 */
class ReinforceTask @Inject constructor(val apk: File, val reinforceExt: ReinforceExt) :
    DefaultTask() {


    // 点击右侧gradle面板中的任务，就会执行@TaskAction标识的方法
    @TaskAction
    fun doReinforceAction() {
        // TODO： 执行加固逻辑
        println("ReinforceTask---doReinforceAction---apk = $apk, reinforceExt = $reinforceExt")
    }
}