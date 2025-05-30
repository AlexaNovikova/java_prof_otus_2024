package ru.otus.aop.instrumentation.setter;

import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

@SuppressWarnings("java:S1172")
public class Agent {
    private static final Logger logger = LoggerFactory.getLogger(Agent.class);

    private Agent() {}

    public static void premain(String agentArgs, Instrumentation inst) {
        logger.info("premain");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(
                    ClassLoader loader,
                    String className,
                    Class<?> classBeingRedefined,
                    ProtectionDomain protectionDomain,
                    byte[] classfileBuffer) {
                if (className.equals("ru/otus/aop/instrumentation/setter/MyClass")) {
                    return addMethod(classfileBuffer);
                }
                return classfileBuffer;
            }
        });
    }

    private static byte[] addMethod(byte[] originalClass) {
        var cr = new ClassReader(originalClass);
        var cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {};
        cr.accept(cv, Opcodes.ASM5);

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "setValue", "(I)V", null, null);

        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitFieldInsn(Opcodes.PUTFIELD, "ru/otus/aop/instrumentation/setter/MyClass", "value", "I");

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        byte[] finalClass = cw.toByteArray();

        try (OutputStream fos = new FileOutputStream("setter.class")) {
            fos.write(finalClass);
        } catch (Exception e) {
            logger.error("error", e);
        }
        return finalClass;
    }
}
