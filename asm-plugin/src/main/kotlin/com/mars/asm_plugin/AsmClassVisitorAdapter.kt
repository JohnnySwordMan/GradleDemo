package com.mars.asm_plugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 * Created by JohnnySwordMan on 2021/3/20
 *
 * AsmClassVisitorAdapter，是ClassVisitor的一个包装类
 *
 * ClassVisitor：主要负责访问类成员信息。其中包括标记在类上的注解、类的构造方法、类的字段、类的方法、静态代码块。
 */
class AsmClassVisitorAdapter(private val classVisitor: ClassVisitor) :
    ClassVisitor(Opcodes.ASM6, classVisitor), Opcodes {


    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor? {
        val methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        return if (methodVisitor != null) {
            AsmMethodVisitor(methodVisitor, access, name, desc)
        } else {
            null
        }
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor? {
        println(desc)
        val annotationVisitor = super.visitAnnotation(desc, visible)
        return if (annotationVisitor != null) {
            AsmAnnotationVisitor(annotationVisitor)
        } else {
            null
        }
    }

}