package com.wzl;

/**
 * -Xss128k Stack的起始值
 * 线程栈大小Xss
 * 当这个线程栈内存调整的特别特别小的时候
 * 线程的并发数量就可以很多很多
 * 如果调整的很大的话，线程的递归调用就可以很深很深
 *
 * 一个是胖，一个是深
 */
public class T05 {
    static int count = 0;

    static void r() {
        count++;
        r();
    }

    public static void main(String[] args) {
        try {
            r();
        } catch (Throwable throwable) {
            System.out.println(count);
            throwable.printStackTrace();
            //OOM out of memmory 栈溢出
            //stackOverFlow
        }
    }
}
