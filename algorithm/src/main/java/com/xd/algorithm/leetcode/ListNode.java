package com.xd.algorithm.leetcode;

/**
 * 链表节点
 *
 * @author xd
 */
public class ListNode {

    public int val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * 根据传入的size构造相应个数的链表
     *
     * @param size 链表大小
     * @return 链表头节点
     */
    public static ListNode getListNode(int size) {
        ListNode head = new ListNode();
        head.val = 1;
        ListNode cur = head;
        for (int i = 2; i <= size; i++) {
            cur.next = new ListNode();
            cur = cur.next;
            cur.val = i;
        }
        return head;
    }

    /**
     * 打印链表num属性
     *
     * @param head 链表头节点
     */
    public static void printListNode(ListNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        StringBuilder numStr = new StringBuilder().append(head.val);
        ListNode cur = head;
        while (cur.next != null) {
            numStr.append(",").append(cur.next.val);
            cur = cur.next;
        }
        System.out.println(numStr);
    }

}
