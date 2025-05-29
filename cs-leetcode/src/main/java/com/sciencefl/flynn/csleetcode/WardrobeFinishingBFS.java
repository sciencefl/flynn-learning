package com.sciencefl.flynn.csleetcode;



import com.sun.tools.javac.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

public class WardrobeFinishingBFS {

    int count =0;
    public int wardrobeFinishing(int m, int n, int cnt) {
        boolean[][] visited = new boolean[m][n];
        Queue<Pair<Integer,Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(0,0));
        int[][] directions = {{1,0},{0,1}};
        while(!queue.isEmpty()){
            Pair<Integer, Integer> kv = queue.poll();
            int row = kv.fst;
            int col = kv.snd;
            if(digit(row)+digit(col) > cnt){
                continue;
            }
            visited[row][col] = true;
            count++; // 当前的符合要求，那就不管了
            for(int[] direction : directions){
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                if(newRow<m && newCol<n && !visited[newRow][newCol]){
                    visited[row][col] = true;
                    queue.offer(new Pair<>(newRow,newCol));
                }
            }
        }
        return count;
    }
    private  int  digit(int n){
        int res = 0;
        while(n!=0){
            res += n%10;
            n=n/10;
        }
        return res;
    }
    public static void main(String[] args) {
        WardrobeFinishingBFS wardrobeFinishing = new WardrobeFinishingBFS();
        int result = wardrobeFinishing.wardrobeFinishing(4,7,5);
        System.out.println(result);
    }
}
