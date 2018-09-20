package com.wzl;

/**
 * 也可以通过Runtime类大致计算内存情况
 */
public class T03 {
    public static void main(String[] args) {
        printMemoryInfo();
        byte[] b = new byte[1024 * 1024];
        System.out.println("----------");
        printMemoryInfo();
    }

    static void printMemoryInfo() {
        System.out.println("total: " + Runtime.getRuntime().totalMemory());
        System.out.println("free: " + Runtime.getRuntime().freeMemory());
    }
}

/**
 total: 16252928
 free: 13963776
 ----------
 total: 16252928
 free: 12915184
 */
