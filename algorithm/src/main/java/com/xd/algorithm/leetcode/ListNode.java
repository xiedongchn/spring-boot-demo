package com.xd.algorithm.leetcode;

import lombok.EqualsAndHashCode;

/**
 * 链表节点
 *
 * @author xd
 */
@EqualsAndHashCode
public class ListNode {

    public int num;

    public ListNode next;

    /**
     * 根据传入的size构造相应个数的链表
     *
     * @param size 链表大小
     * @return 链表头节点
     */
    public static ListNode getListNode(int size) {
        ListNode head = new ListNode();
        head.num = 1;
        ListNode cur = head;
        for (int i = 2; i <= size; i++) {
            cur.next = new ListNode();
            cur = cur.next;
            cur.num = i;
        }
        return head;
    }

    /**
     * 打印链表num属性
     *
     * @param head 链表头节点
     */
    public static void printListNode(ListNode head) {
        StringBuilder numStr = new StringBuilder().append(head.num);
        ListNode cur = head;
        while (cur.next != null) {
            numStr.append(",").append(cur.next.num);
            cur = cur.next;
        }
        System.out.println(numStr);
    }

}
