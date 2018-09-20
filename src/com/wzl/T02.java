package com.wzl;

/**
 * -XX:-DoEscapeAnalysis
 * -XX:+EliminateAllocations
 * -XX:+UseTLAB
 * -XX:+PrintGCDetails 打印GC详细信息
 *
 */
public class T02 {

    public static void main(String[] args) {
        byte[] b = new byte[1024];
    }
}

/**

 线程本地缓存，使用了eden区

 Heap
 def new generation   total 4928K, used 2324K [0x04a00000, 0x04f50000, 0x09f50000)
 eden space 4416K,  52% used [0x04a00000, 0x04c450b0, 0x04e50000)
 from space 512K,   0% used [0x04e50000, 0x04e50000, 0x04ed0000)
 to   space 512K,   0% used [0x04ed0000, 0x04ed0000, 0x04f50000)
 tenured generation   total 10944K, used 0K [0x09f50000, 0x0aa00000, 0x14a00000)
 the space 10944K,   0% used [0x09f50000, 0x09f50000, 0x09f50200, 0x0aa00000)
 Metaspace       used 2243K, capacity 2248K, committed 2368K, reserved 4480K

 **/