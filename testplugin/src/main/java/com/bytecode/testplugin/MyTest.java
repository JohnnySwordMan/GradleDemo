package com.bytecode.testplugin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;

/**
 * Created by JohnnySwordMan on 2021/4/12
 */
public class MyTest {

    public static void main(String[] args) throws IOException {

        ClassReader classReader = new ClassReader("com.bytecode.testplugin.Test");
        ClassWriter classWriter = new ClassWriter(classReader, COMPUTE_FRAMES);
        ClassVisitor classVisitor = new TestClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/geyan/projects/github/GradleLearning/testplugin/src/main/java/com/bytecode/testplugin/Test.class"));
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
}
