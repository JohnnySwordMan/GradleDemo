package com.mars.asm_plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by JohnnySwordMan on 2021/3/20
 */
class AsmPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("======== asm-plugin doing ==========")
        project.extensions.findByType(AppExtension::class.java)?.registerTransform(AsmTransform())
    }

}