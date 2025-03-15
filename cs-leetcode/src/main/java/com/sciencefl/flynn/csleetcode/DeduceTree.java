package com.sciencefl.flynn.csleetcode;

public class DeduceTree {
    TreeNode  head = new TreeNode();
    public TreeNode deduceTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null || preorder.length == 0){
            return head;
        }
        head = rece(preorder,0,preorder.length-1,inorder,0,preorder.length-1);
        return head;
    }
    TreeNode rece(int[] preorder,int preStart,int preEnd,int[] inorder,int inorderStart ,int inorderEnd){ //子树的开端与结尾
        TreeNode node = new TreeNode();
        node.val = preorder[preStart];
        if(preEnd != preStart){
            int i=0;
            while(inorder[inorderStart+i]!=preorder[preStart]){
                i++;
            }
            if(i!=0){
                node.left = rece(preorder,preStart+1,preStart+i,inorder,inorderStart,inorderStart+i-1);
            }
            if(inorderStart+i != inorderEnd ){
                node.right = rece(preorder,preStart+i+1, preEnd,inorder,inorderStart+i+1,inorderEnd);
            }
        }
        return node;
    }

    public static void main(String[] args) {
        DeduceTree tree = new DeduceTree();
        int[] a = {1,2};
        int[] b = {2,1};
        TreeNode treeNode = tree.deduceTree(a, b);
        System.out.println(treeNode.val);
    }
}
