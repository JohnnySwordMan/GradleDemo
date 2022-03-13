package com.bytecode.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 加固插件，主要是为了学习Gradle插件的创建、开发流程
 * Created by JohnnySwordMan on 2021/4/5
 */
class ReinforcePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("ReinforcePlugin---apply")
        // 创建Extension
//         val reinforceExtension =
//            project.extensions.create("jiagu2", ReinforceExt::class.java)

//        project.extensions.add("foo", Foo())
        project.extensions.create("foo", Foo::class.java)
//        project.extensions.add("byteReinforce", ReinforceExt())
        project.extensions.add("byteReinforce", ReinforceExtension())


        // 在gradle config之后，可以拿到上述配置的ReinforceExtension信息
        project.afterEvaluate {
            val foo =
                project.extensions.getByType(Foo::class.java)
            println("ReinforcePlugin---foo msg = ${foo.msg}")

            val reinforceExt =
                project.extensions.getByType(ReinforceExtension::class.java)
            println("ReinforcePlugin---ReinforceExtension = $reinforceExt")

            // 拿到android插件扩展
            val appExtension = project.extensions.getByName("android") as AppExtension

            // 变体，默认有两个变体，debug和release
            appExtension.applicationVariants.all {
                // 遍历，这里的name是debug和release
                val name = it.name
                println("ReinforcePlugin---name = $name")

                it.outputs.all { output ->
                    val file = output.outputFile
                    println("ReinforcePlugin---file = $file")
                    project.tasks.create(
                        "reinforce$name",
                        ReinforceTaskJava::class.java,
                        name,
                        file,
                        reinforceExt
                    )
                }
            }
        }
    }
}