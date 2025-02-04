package com.sciencefl.flynn.concurrence;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 管程模型
 * @param <T>
 */
public class BlockQueue<T> {
    List<T> queue = new LinkedList<T>();
    Lock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();

    void enqueue(T element) throws InterruptedException {
        Executors.newFixedThreadPool(3);
        CompletionService completionService = new ExecutorCompletionService();
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {})>
        try{
            lock.lock();
            // 对于 MESA 管程来说，有一个编程范式
            while(queue.size()==10){
                notFull.await();
            }
            queue.add(element);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }

    }

    void dequeue() throws InterruptedException {
        try{
            lock.lock();
            while(queue.size()==0){
                notEmpty.await();
            }
            queue.remove(queue.size()-1);
            notFull.signal();
        } finally {
            lock.unlock();
        }

    }

}
