package com.sciencefl.flynn.csleetcode;

import java.util.LinkedList;

public class CQueue {
    LinkedList<Integer> stackPush;
    LinkedList<Integer> stackPop;
    public CQueue() {
        stackPush = new LinkedList<>();
        stackPop = new LinkedList<>();
    }

    public void appendTail(int value) {
        stackPush.addLast(value);

    }

    public int deleteHead() {
        if(stackPop.size()==0){
            if(stackPush.size()>0){
                while(stackPush.size()>0){
                    stackPop.addFirst(stackPush.removeFirst());
                }
            }
            return -1;
        }
        return stackPop.removeFirst();
    }
    public int fib(int n) {
        if(n<2){
            return n;
        }
        int a =0;
        int b =1;
        for(int i=2;i<=n;i++){
            int tmp = b;
            b=a+b;
            a=tmp;
        }
        return b;
    }

    public static void main(String[] args) {
         CQueue cQueue = new CQueue();
        int fib = cQueue.fib(45);
        System.out.println(fib);
    }
}
