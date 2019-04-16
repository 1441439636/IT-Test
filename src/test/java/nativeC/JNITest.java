package nativeC;

/**
 * 编译JNITest.java 为 JNITest.class
 * 命令： javac JNITest.java
 * 这时候生成了两个.class文件 [JNITest.class,NativeMethodTest.class]
 * 命令：javah NativeMethodTest
 * 生成了一个 NativeMethodTest.h 文件，这个h文件相当于我们在java里面的接口，这里声明了四个方法，然后在我们的本地方法里面实现这个方法，也就是说我们在编写C/C++程序的时候所使用的方法名必须和这里的一致。
 * 命令：gcc -c -fPIC -o NativeC.o NativeC.c
 * 命令：gcc -shared -o NativeC.so NativeC.o
 */
public class JNITest {
    public static void main(String[] args) {
        // 加载本地库 linux 下为.so     windows下为.dll
        System.loadLibrary("NativeC");
        NativeMethodTest nmt = new NativeMethodTest();

        int square = nmt.intMethod(5);
        boolean bool = nmt.booleanMethod(true);
        String text = nmt.stringMethod("java");
        int sum = nmt.intArrayMethod(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 13});

        System.out.println("intMethod: " + square);
        System.out.println("booleanMethod:" + bool);
        System.out.println("stringMethod:" + text);
        System.out.println("intArrayMethod:" + sum);
    }

}

/**
 * 把当前类编译为 NativeMethodTest.h 文件
 */
class NativeMethodTest {
    public native int intMethod(int n);

    public native boolean booleanMethod(boolean bool);

    public native String stringMethod(String text);

    public native int intArrayMethod(int[] intArray);

}