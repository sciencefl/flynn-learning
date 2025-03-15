package com.sciencefl.flynn.csleetcode;

import java.util.Arrays;

public class DFS {

    Graph graph ;
    public DFS(Graph graph){
        this.graph = graph;
    }
    boolean found = false;
    public void dfs(int s,int t){
        int[] prev  = new int[graph.v];
        boolean[] visited = new boolean[graph.v];
        Arrays.fill(prev,-1);
        recurDfs(s,t,visited,prev);

    }
    private void recurDfs(int s, int t,boolean[] visited,int[] prev){
       if(found){
           return ;
       }
       visited[s] = true;
       if(s == t){
           found = true;
           return;
       }
        for (Integer i : graph.adj[s]) {
            if(!visited[i]){
                prev[i] = s;
                recurDfs(i,s,visited,prev);
            }
        }
    }
    private void printf(int[] prev,int s,int t){
        if(prev[t]!=-1 && s!=t){
            printf(prev,s,prev[t]);
        }else {
            System.out.println(t + " ");
        }
    }
}
