package com.mars.asm_plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Created by JohnnySwordMan on 2021/3/20
 */
class AsmTransform : Transform() {

    /**
     * 获取Transform的名字
     */
    override fun getName(): String {
        return AsmTransform::class.java.name
    }

    // 消费的文件类型，一般是CONTENT_CLASS
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    // 是否支持增量编译，注意增量编译需要额外的支持
    override fun isIncremental(): Boolean {
        return true
    }

    // 消费的Scope
    /**
     * 作用范围：
     * 1. PROJECT: 只处理当前项目
     * 2. SUB_PROJECTS: 只处理子项目
     * 3. PROJECT_LOCAL_DEPS: 只处理当前项目的本地依赖，例如 jar和aar等
     * 4. EXTERNAL_LIBRARIES: 只处理外部的依赖库
     * 5. PROVIDED_ONLY: 只处理本地或远程以provided形式引入的依赖库
     * 6. TESTED_CODE: 只处理测试代码
     */
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    // transform只是用来封装一个Transform TASK，用以顺序输入输出，具体的字节码操作逻辑在transform中实现
    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)

        // OutputProvider管理输出路径，如果消费型输入为空，OutputProvider等于null
        val outputProvider = transformInvocation.outputProvider

        // 消费型输入，可以从中获取jar包和class文件夹路径
        transformInvocation.inputs.forEach { input ->
            input.jarInputs.forEach {
                val dest = outputProvider.getContentLocation(
                    it.file.absolutePath,
                    it.contentTypes,
                    it.scopes,
                    Format.JAR
                )
                // 将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                FileUtils.copyFile(it.file, dest)
            }
            input.directoryInputs.forEach {
                val dest = outputProvider
                    .getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                // 将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                transformDir(it.file, dest)
            }
        }
    }

    private fun transformDir(input: File, dest: File) {
        if (dest.exists()) {
            FileUtils.forceDelete(dest)
        }
        FileUtils.forceMkdir(dest)
        val srcDirPath = input.absolutePath
        val destDirPath = dest.absolutePath
        input.listFiles().forEach { file ->
            val destFilePath = file.absolutePath.replace(srcDirPath, destDirPath)
            val destFile = File(destFilePath)
            if (file.isDirectory) {
                transformDir(file, destFile)
            } else if(file.isFile){
                FileUtils.touch(destFile)
                weave(file.absolutePath, destFile.absolutePath)
            }
        }
    }

    private fun weave(inputPath: String, outputPath: String) {
        val inputStream = FileInputStream(inputPath)
        // 该类用来解析编译过的class字节码文件
        val classReader = ClassReader(inputStream)
        // 该类用来重新构建编译后的类，比如说修改类名、属性以及方法，甚至可以生成新的类的字节码文件
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES)
        val adapter = AsmClassVisitorAdapter(classWriter)
        classReader.accept(adapter, 0)
        val outputStream = FileOutputStream(outputPath)
        outputStream.write(classWriter.toByteArray())
        outputStream.close()
    }

}