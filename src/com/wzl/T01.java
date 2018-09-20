package com.wzl;

/**
 * +表示做，-表示不做
 *  -XX:+DoEscapeAnalysis 表示做逃逸分析（默认是做的）
 *  -XX:-EliminatieAllocations 不做标量替换
 *  上面两项-的话，就不会往栈上分配了
 *
 *  -XX:+UseTLAB 使用线程本地缓存
 *  -XX:+PrintGC 打印GC过程
 *
 *  -XX:+DoEscapeAnalysis -XX:+EliminatieAllocations -XX:+UseTLAB -XX:+PrintGC
 *
 */
public class T01 {
    class User{
        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i) {
        new User(i, "name" + i);
    }

    public static void main(String[] args) {
        T01 t = new T01();
        long s1 = System.currentTimeMillis();
        //每调一次allocation就要new一个User出来，所以这里new了一千万次User
        for (int i = 0; i < 10000000; i++) t.alloc(i);
        long s2 = System.currentTimeMillis();

        System.out.println(s2 - s1);
    }
}
