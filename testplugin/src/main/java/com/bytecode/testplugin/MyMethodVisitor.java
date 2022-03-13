package com.bytecode.testplugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.PrintStream;


/**
 * Created by JohnnySwordMan on 2021/4/12
 */
public class MyMethodVisitor extends AdviceAdapter {
    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected MyMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM8, methodVisitor, access, name, descriptor);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
//        sop("TestMethodVisitor: asm insert code before");
        invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
//        sop("TestMethodVisitor: asm insert code after");
    }

    private void sop(String msg) {
        mv.visitFieldInsn(Opcodes.GETSTATIC,
                Type.getInternalName(System.class),
                "out",
                Type.getDescriptor(PrintStream.class));

        mv.visitLdcInsn("Hello World!");


        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(PrintStream.class),
                "println",
                "(Ljava/lang/String;)v",
                false);
    }
}
