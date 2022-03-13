package com.bytecode.testplugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by JohnnySwordMan on 2021/4/12
 */
public class TestClassVisitor extends ClassVisitor {


    public TestClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM8, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("TestClassVisitor---visitMethod---name = " + name);
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ("<init>".equals(name)) {
            mv = new MyMethodVisitor(mv, access, name, descriptor);
        }
        return mv;
    }
}
