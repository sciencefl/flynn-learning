package com.sciencefl.flynn.csleetcode;

public class TreeToDoublyList {

    Node pre,head; // 一个记录头节点，一个记录 当前节点的前置节点
    public Node treeToDoublyList(Node root) {
        if(root == null) return null;
        inorder(root);
        head.left = pre;
        pre.right = head;
        return head;
    }

    private void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        if(pre != null){
            pre.right = root;
            root.left = pre;
            pre = root;
        } else {
            head = root;
            pre = head;
        }
        inorder(root.right);
    }
}
