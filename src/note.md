# JVM

## JVM中的垃圾收集器

https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/collectors.html

* Serial Collector 序列化收集器
    >单线程，Server一般不用
* Parallel Collector 并行收集
    >效率比单线程高，吞吐量大，并发量大，虚拟机需要停下来

两种更现代化的：Concurrent Controller 并发收集器
* CMS Collector 
    > UseConcMarkSweepGC
    > 停顿时间短，虚拟机运行的时候就能够收集
* G1（Paralle和CMS这种）
    > 不仅停顿时间短，同时并发量大(但时间不会比CMS短，有个平衡)



## Java对象的分配

- 栈上的分配
  - 线程私有小对象
  - 无逃逸
  - 支持标量替换
  - **无需调整**
- 线程本地分配TLAB（Thread Local Allocation Buffer)
  - 占用eden，默认1%
  - 多线程的时候不用竞争eden就可以申请空间，提高效率
  - 小对象
  - **无需调整**
- 老年代
  - 大对象
- eden



### 详解

此部分是虚拟机自动优化好了，我们不需要再作调整，面试会问

#### 1） new一个对象

​	new一个对象，这个对象特别小，并且你的JVM开了栈上优化（server这个版本默认是开了的），他会优先分配到栈上，他不往堆上放了

* 优点：放在栈上，即放在栈帧中，当这个方法结束之后，这个栈帧就没了，就不需要垃圾收集，相当于自己就手机了；效率高
* 缺点：栈空间本身就小，所以这个对象需要特别小。

​        不能够逃逸：如果方法外面有一个引用指向这个对象，这个对象就逃逸出去了；因为栈帧是方法结束就消失的，对象也应该跟着消失，这种对象不能在栈上分配。

​	把栈里面的对象的成员变量单独拿出来，当做普通数据类型往栈上存

#### 2） 找线程本地分配TLAB

​	线程专用内存就是TLAB   volatile

​	这部分的buffer 要去新生代（堆里面的）eden区分配内存

​	每一个线程默认申请1%的空间给自己专用，如果每个人都要用这个完整的空间的话，就需要加锁，因此这个分配方法能够提高效率。

​	Spring 里面会经常用到的一个类叫ThreadLocal类 

#### 3) 如果对象特别大 就放到老年代里面 

#### 4) 如果上面分配完了，并且这个对象不够大就放到eden里 



## 常用参数

### 堆设置

-Xms初始堆大小

-Xmx最大堆大小

-Xss线程栈大小

-XX:NewSize=n设置年轻代/新生代 大小

-XX:NewRatio=n设置新生代和年老代的比值。如3，表示新生代：年老代=1：3，新生代占整个（新生代+年老代）1/4

-XX:SurvivorRatio=n新生代中的Eden区再与两个Survivor区的比值。注意survivor有两个区。如：3，表示Eden：Survivor=3：2，一个Survivor占真个新生代区的1/5

-XX:MaxPermSize=n设置持久代的大小 



### 收集器设置

-XX:+UseSerialGC设置串行收集器

-XX:+UseParallelGC设置并行收集器

-XX:+UseConcMarkSweepGC设置并发收集器 



### 垃圾回收统计信息

-XX:+PrintGC

-XX:+PrintGCDetails-Xloggc:filename 



### 典型Tomcat优化配置

 set JAVA_OPTS=-Xms4g-Xmx4g-Xss512k

-XX:+AggressiveOpts

-XX:+UseBiasedLocking

-XX:PermSize=64M

-XX:MaxPermSize=300M

-XX:+DisableExplicitGC 

### 复杂部分

-XX:+UseConcMarkSweepGC使用CMS缩短响应时间，并发手机，低停顿

-XX:+UseParNewGC并发收集新生代的垃圾

-XX:+CMSParallelRemarkEnabled在使用UseParNewGC的情况下，尽量减少mark的时间

-XX:+UseCMSCompactAtFullCollection使用并发收集器时，开启对老年代的压缩，使碎片减少

-XX:LargePageSizeInBytes=128m内存分页大小对性能的提升

-XX:+UseFastAccessorMethodsget/set方法转成本地代码-Djava.awt.headless=true修复linux下的tomcat处理图表的时候可能会留下的一个bug 