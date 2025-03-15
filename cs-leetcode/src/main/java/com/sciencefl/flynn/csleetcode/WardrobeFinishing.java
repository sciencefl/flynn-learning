package com.sciencefl.flynn.csleetcode;

public class WardrobeFinishing {
    int count =0;
    public int wardrobeFinishing(int m, int n, int cnt) {
        boolean[][] visited = new boolean[m][n];
        recurDfs(0,0,visited,cnt);
        return count;
    }
    private void recurDfs(int m,int n, boolean[][] visited,int cnt){
        if (digit(m) + digit(n) > cnt) {
            return ;
        }
        count++;
        int[][] directions = {{1,0},{0,1}};
        for(int[] direction : directions){
            int newM = m+ direction[0];
            int newN = n+ direction[1];
            if(newM< visited.length&& newN<visited[0].length && !visited[newM][newN]){
                visited[newM][newN]=true;
                recurDfs(newM,newN,visited,cnt);
            }
        }
    }
    private  int  digit(int n){
        int res = 0;
        while(n!=0){
            res += n%10;
            n=n/10;
        }
        return res;
    }
    public int cuttingRope(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for(int i = 3; i < n + 1; i++){
            for(int j = 2; j < i; j++){
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        WardrobeFinishing wardrobeFinishing = new WardrobeFinishing();
        int result = wardrobeFinishing.digit(15);
        System.out.println(result);
    }
}
