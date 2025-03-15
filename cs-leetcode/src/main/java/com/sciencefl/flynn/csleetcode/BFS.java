package com.sciencefl.flynn.csleetcode;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    Graph graph;
    public BFS(Graph graph){
        this.graph = graph;
    }

    /**
     * 广度优先遍历：
     * 需要一个记录路径额数组 prev[v]
     * 需要一个记录路径是否已经走过的visited[v]
     * 需要一个队列queue来记录访问顺序
     * @param s
     * @param t
     * @return
     */
    public void  bfs(int s,int t){
        int[] prev = new int[graph.v]; // 记录已经访问过的路径prev[t]表示到达t的上一个节点
        boolean[] visited = new boolean[graph.v]; // 记录哪些节点已经访问过，则不再访问 visited[t] = true,表示已经访问过了
        Queue<Integer> queue = new LinkedList<>();
        Arrays.fill(prev, -1);
        if(s == t){
            return ;
        }
        visited[s] = true;
        queue.offer(s);
        while(!queue.isEmpty()){
            int tmp = queue.poll();
            LinkedList<Integer> tmpList = graph.adj[tmp];
            for (Integer i : tmpList) {
                if(!visited[i]){
                    prev[i] = tmp;
                    if(i == t){
                        printf(prev,s,t);
                        return ;
                    }
                }
                visited[i] = true;
                queue.offer(i);
            }
        }
    }
    private void printf(int[] prev, int s,int t){
        if(prev[t]!= -1 && t != s){
            printf(prev,s,prev[t]);
        }else {
            System.out.println(s + "->");
        }
    }
}
