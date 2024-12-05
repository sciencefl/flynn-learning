package singleton;

/**
 *
 */
public class Singleton {
    /**
     * WHAT: 一个类只允许创建一个对象（或者实例），那这个类就是一个单例类，这种设计模式就叫作单例设计模式，简称单例模式。
     * WHY: 单例模式解决的问题：1. 处理共享资源访问冲突，1. 表示全局唯一类
     * HOW:
     *  构造函数需要是 private 访问权限的，这样才能避免外部通过 new 创建实例；
     *  考虑对象创建时的线程安全问题；
     *  考虑是否支持延迟加载；
     *  考虑 getInstance() 性能是否高（是否加锁）。
     * 单例模式三要素：一个私有化的构造方法，一个私有化的全局静态变量，一个公共的获取实例的静态方法
     *      饿汉式，懒汉式，双重检测
     * FAQ:
     *
     */
    // 饿汉式 。不支持延迟加载   易扩展性、易复用、易测试、易读性
//    private  Singleton instance = new Singleton();
//    public static Singleton getInstance() {
//        return instance;
//    }
    private Singleton() {

    }
    // 懒汉式
    private static Singleton instance;
    public static synchronized Singleton getInstance1(){
        if(instance == null ){
            instance = new Singleton();
        }
        return instance;
    }
    // 双重检测
    public static Singleton getInstance2(){
        if(instance == null){
            synchronized (Singleton.class){ // 此处是类级别的锁
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // 静态内部类，它有点类似饿汉式，但又能做到了延迟加载

    /**
     * 涉及到JVM类加载的知识了。
     * SingletonHolder 是一个静态内部类，当外部类 Singleton 被加载的时候，并不会创建 SingletonHolder 实例对象。
     * 只有当调用 getInstance() 方法时，SingletonHolder 才会被加载，这个时候才会创建 instance。
     * instance 的唯一性、创建过程的线程安全性，都由 JVM 来保证。所以，这种实现方法既保证了线程安全，又能做到延迟加载。
     */
    private static class SingletonHolder{
        private static final Singleton instance = new Singleton();
    }
    public static Singleton getInstance3(){
        return SingletonHolder.instance;
    }
    /**
     * 基于枚举类型的单例实现。这种实现方式通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和实例的唯一性。
     * public enum IdGenerator { INSTANCE; private AtomicLong id = new AtomicLong(0); public long getId() { return id.incrementAndGet(); }}
     */

}
