package com.sciencefl.flynn.csleetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathTarget {
    // 二叉树的深度优先遍历
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> pathTarget(TreeNode root, int target) {
        if(root == null){
            return res;
        }
        List<Integer> list = new ArrayList<>();
        recurBFS(root,0,target,list);
        return res;
    }
    private void recurBFS(TreeNode root,int sum,int target,List<Integer> list){
        list.add(root.val);
        sum += root.val;
        if(sum == target){
            List<Integer> list2 = new ArrayList<>();
            Collections.copy(list2,list);
            res.add(list2);
        }
        if(root.left != null){
            recurBFS(root.left,sum,target,list);
        }
        if(root.right != null){
            recurBFS(root.right,sum,target,list);
        }
        list.remove(list.size()-1);
    }
}
