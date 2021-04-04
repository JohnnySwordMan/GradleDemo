package com.mars.asm_plugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created by JohnnySwordMan on 2021/3/20
 *
 * AdviceAdapter：实现了MethodVisitor接口，主要负责访问方法的信息，用来进行具体的字节码操作。
 */
class AsmMethodVisitor(
    private val methodVisitor: MethodVisitor,
    private val access: Int,
    private val name: String?,
    private val descriptor: String?
) : AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, descriptor), Opcodes {

    private var isMatch = false
    private val ANNOTATION_TRACK_METHOD = "Lcom/mars/gradlelearning/Cat"

    override fun visitCode() {
        super.visitCode()
        if (isMatch) {
            // 方法执行之前插入
            methodVisitor.apply {
                visitLdcInsn("tag")
                visitLdcInsn("onCreate start")
                visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "android/util/Log",
                    "d",
                    "(Ljava/lang/String;Ljava/lang/String;)I",
                    false
                )
                visitInsn(Opcodes.POP)
            }
        }
    }

    override fun visitInsn(opcode: Int) {
        if (isMatch) {
            // 方法执行后插入
            if (opcode == Opcodes.RETURN) {
                methodVisitor.apply {
                    visitLdcInsn("tag")
                    visitLdcInsn("onCreate end")
                    visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "android/util/Log",
                        "d",
                        "(Ljava/lang/String;Ljava/lang/String;)I",
                        false
                    )
                    visitInsn(Opcodes.POP)
                }
            }
        }
        super.visitInsn(opcode)
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        desc: String?,
        itf: Boolean
    ) {
        if (isMatch) {
            methodVisitor.apply {
                visitLdcInsn("tag")
                visitLdcInsn("onCreate 1")
                visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "android/util/Log",
                    "d",
                    "(Ljava/lang/String;Ljava/lang/String;)I",
                    false
                )
                visitInsn(Opcodes.POP)
            }
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf)
        if (isMatch) {
            methodVisitor.apply {
                visitLdcInsn("tag")
                visitLdcInsn("onCreate 2")
                visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "android/util/Log",
                    "d",
                    "(Ljava/lang/String;Ljava/lang/String;)I",
                    false
                )
                visitInsn(Opcodes.POP)
            }
        }
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        if (ANNOTATION_TRACK_METHOD == desc) {
            isMatch = true
        }
        return super.visitAnnotation(desc, visible)
    }
}