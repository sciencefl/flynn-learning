package com.sciencefl.flynn.datastruct;

import java.util.Arrays;

/**
 * 实现一个大小固定的有序数组，支持动态增删改操作
 */
public class FixedSizeSortedArray {

    private int[] elementData;
    private int size=0;

    public FixedSizeSortedArray(int dataSize){
        if(dataSize<=0){
            throw new IllegalArgumentException("dataSize must be greater than 0");
        }
        elementData = new int[dataSize];
    }
    public void add(int data){
        // 达到上限，添加不了了
        if(size==elementData.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        elementData[size++] = data;
        sortArray();
    }
    public void remove(int index){
        if(size==0){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(index<0 ||index>=size){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(index<size-1){
            System.arraycopy(elementData,index+1,elementData,index,size-index-1);
        }
        elementData[size-1]=0;
        size--;
    }

    public void set(int index,int data){
        if(index<0 ||index>=size){
            throw new ArrayIndexOutOfBoundsException();
        }
        elementData[index]=data;
        sortArray();
    }
    public int get(int index){
        if(index<0 ||index>=size){
            throw new ArrayIndexOutOfBoundsException();
        }
        return elementData[index];
    }
    private void sortArray(){
        // 只排序到有用得元素, toIndex中是不包含的
        Arrays.sort(elementData,0,size);
    }

    public static void main(String[] args) {
        FixedSizeSortedArray array = new FixedSizeSortedArray(10);
        array.add(10);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        array.remove(3);
        array.get(3);
    }
}
