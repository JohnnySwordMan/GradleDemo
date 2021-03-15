package com.mars.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by JohnnySwordMan on 2021/3/16
 */
class BytePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("invoke apply method of BytePlugin")
    }

}