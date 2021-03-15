package com.mars.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by JohnnySwordMan on 2021/3/15
 */
class FirstPlugin : Plugin<Project> {

    override fun apply(target: Project) {
       println("FirstPlugin apply, project name = ${target.name}")
    }

}