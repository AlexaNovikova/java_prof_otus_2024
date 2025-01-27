package ru.otus;

public class MyTest {

    static int global;
    static Integer globalInteger;

    public static void main(String[] args) {
        //        var hash = -12231;
        //        System.out.println(Integer.toBinaryString(hash));
        //        System.out.println(hash);
        //        System.out.println(Integer.toBinaryString(hash & 0x7fffffff));
        //        System.out.println(hash & 0x7fffffff);
        //        System.out.println();
        //
        //        int positiveInt = ~hash + 1;
        //        System.out.println(Integer.toBinaryString(positiveInt));
        //        System.out.println(positiveInt);
        //
        //        for (int i = 0; i < 20; i++) {
        //            System.out.println(Integer.toBinaryString(1));
        //            System.out.println(Integer.toBinaryString(i & 1));
        //        }

        //        System.out.println(5 << 2);
        //        System.out.println(8 >> 2);
        printMass();
    }

    public static void printMass() {

        System.out.println(globalInteger);
        System.out.println(global);
        int[] ints = new int[10];
        Integer[] integers = new Integer[10];
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
            System.out.println(integers[i]);
        }
    }
}
