package com.sciencefl.flynn.csleetcode;

import java.util.LinkedList;

public class TrainingPlan {
    public int[] trainingPlan(int[] actions) {
        int m = 0; // 偶数存在的位置
        int n = 0;
        while(n <= actions.length-1){
            if((actions[n] & 0x1)==1){
                swap(actions,m,n);
                m = m+1;
            }
            n++;
        }
        return actions;

    }
    private void  swap(int actions[],int m,int n){
        int tmp = actions[m];
        actions[m] = actions[n];
        actions[n]= tmp;

    }
    public int[] exchange(int[] nums) {
        int slow = 0,fast = 0;
        while(fast<nums.length){
            if((nums[fast]&1)==1)
                swap(nums,slow++,fast);
            fast++;
        }
        return nums;
    }

    public ListNode trainningPlan(ListNode head) {
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode cur = head;
        while(cur!=null){
            ListNode tmp =cur;
            cur = cur.next;
            tmp.next = null;
            stack.addFirst(tmp);

        }
        while(stack.size()!=0){
            ListNode tmp = stack.removeFirst();
            if(cur == null) {
                cur = tmp;
                head = cur;
            } else {
                cur.next = tmp;
                cur = tmp;
            }
        }
        return head;
    }


    public static void main(String[] args) {
        TrainingPlan plan = new TrainingPlan();
        int[] actions = {1,2,3,4,5,6,6,6,7};
        // int[] good = plan.exchange(actions);
        actions = plan.trainingPlan(actions);
        System.out.println(actions);
    }
}
