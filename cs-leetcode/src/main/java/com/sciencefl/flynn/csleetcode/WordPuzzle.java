package com.sciencefl.flynn.csleetcode;

public class WordPuzzle {

    public boolean wordPuzzle(char[][] grid, String target) {
        // 深度优先遍历+回溯剪枝
        int row = grid.length;
        int col = grid[0].length;
        boolean[][] visited = new boolean[row][col];
        for(int i = 0;i<row;i++){
            for(int j = 0; j< col;j++){
                if(existDfs(i,j,grid,target,visited,0)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean existDfs(int m,int n,char[][] grid,String target,boolean[][] visited,int k){
        if(grid[m][n] != target.charAt(k)){
            return false;
        } else if(k == target.length()-1) {  // 全部字符都已经找到了，可以返回了
            return true;
        }
        boolean flag = false;
        // 当前的字符已经找到了，去找剩下的字符
        visited[m][n] = true;
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}}; //  方向键
        for(int[] direction : directions){
            int newRow = m+direction[0];
            int newCol = n+direction[1];
            if(newCol>=0 && newCol< grid[0].length && newRow>=0 && newRow< grid.length && !visited[newRow][newCol]){
                flag = existDfs(newRow,newCol,grid,target,visited,k+1);
                if(flag){
                    break;
                }
            }
        }
        visited[m][n] = false; // 这个题解的精髓所在！！！
        return flag;
    }

    public static void main(String[] args) {
        WordPuzzle wordPuzzle = new WordPuzzle();
        char[][] grid = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String target = "ABCCED";
        boolean b = wordPuzzle.wordPuzzle(grid, target);
        System.out.println(b);
    }
}
