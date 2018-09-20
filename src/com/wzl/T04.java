package com.wzl;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存溢出
 * -XX:+HeadDumpOnOutOfMemoryError -XX:HeapDumpPath=c:\tmp\jvm.dump -XX:+PrintGCDetails
 * -Xms10M 程序起始时分配的内存大小
 * -Xmx10M 程序最大分配大小
 * 内存文件的查看工具VisualVM
 */
public class T04 {

    public static void main(String[] args) {
        List<Object> lists = new ArrayList<>();
        for(int i=0; i<100000000; i++) {
            lists.add(new byte[1024 * 1024]);
        }
    }
}
