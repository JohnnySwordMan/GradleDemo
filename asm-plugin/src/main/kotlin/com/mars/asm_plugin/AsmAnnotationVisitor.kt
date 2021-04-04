package com.mars.asm_plugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by JohnnySwordMan on 2021/3/20
 */
class AsmAnnotationVisitor(private val annotationVisitor: AnnotationVisitor) :
    AnnotationVisitor(Opcodes.ASM6, annotationVisitor), Opcodes {

    override fun visitAnnotation(name: String?, desc: String?): AnnotationVisitor {
        println("$name $desc")
        return annotationVisitor
    }
}