package com.sciencefl.flynn.datastruct;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 实现一个支持动态扩容的数组
 */
public class DynamicGrowArrayList {
    String[] elementData = new String[2] ;
    int size=0;
    public void addLast(String element){
        ensureCap(size+1);
        elementData[size++]=element;
    }
    private void ensureCap(int minCapacity){
        if(minCapacity>elementData.length){
            grow(minCapacity);
        }
    }
    private void grow(int minCapacity){
        if(minCapacity<0){
            throw new OutOfMemoryError();
        }
        int oldCapacity = elementData.length;
        int newCapacity= oldCapacity+(oldCapacity>>1);
        if (newCapacity-minCapacity<0){
           newCapacity = minCapacity;
        }
        if(newCapacity>Integer.MAX_VALUE){
            newCapacity=Integer.MAX_VALUE;
        }
        elementData = Arrays.copyOf(elementData,newCapacity);
    }
    public void removeLast(){
        if(size==0){
            throw new NoSuchElementException();
        }
        elementData[size--] = null;
    }
    public int size(){
        return size;
    }
    public Object indexOf(int i){
        if(i<0 || i>=size){
            throw new IndexOutOfBoundsException();
        }
        return elementData[i];
    }

    public static void main(String[] args) {
        DynamicGrowArrayList list = new DynamicGrowArrayList();
        list.addLast("a");
        list.addLast("b");
        list.addLast("c");
        list.addLast("d");
        list.addLast("e");
        list.addLast("f");
        list.addLast("g");
    }
}
