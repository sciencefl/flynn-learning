package com.sciencefl.flynn.csleetcode;

import java.util.LinkedList;

public class Graph { // 无向图
    int v; // 无向图中顶点的个数
    LinkedList<Integer>[] adj; // 邻接表
    public Graph(int v){
        adj = new LinkedList[v];
        for(int i=0;i<v;i++){
            adj[i] = new LinkedList<>();
        }
    }
    public void addEdge(int s,int t){
        adj[s].add(t);
        adj[t].add(s); // 无向图一条边存两次
    }
}
