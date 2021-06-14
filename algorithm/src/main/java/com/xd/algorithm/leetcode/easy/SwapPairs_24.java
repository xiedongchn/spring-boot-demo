package com.xd.algorithm.leetcode.easy;

import com.xd.algorithm.leetcode.ListNode;
import org.junit.Test;

/**
 * 题目：两两交换链表中的节点
 * 给定头节点，将链表两两反转，例如：1->2->3->4，2->1->4->3
 *
 * @author xd
 */
public class SwapPairs_24 {

    /**
     * 题目：给定头节点，将链表两两反转，例如：1->2->3->4，2->1->4->3
     * 解题思路：
     * 1、首先需要遍历链表，且是成对的遍历，需要两个指针cur和next，分别指向当前节点和当前节点的前一个节点
     * 2、
     *
     * @param head 头节点
     * @return 新的链表
     */
    public ListNode swapPairs(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;// 1
        ListNode newHead = cur.next;
        while (cur != null && cur.next != null) {// 3
            if (pre != null) {// 1
                pre.next = cur.next;
            }
            ListNode next = cur.next;// 2
            ListNode nNext = next.next;// 3
            cur.next = nNext;//1 -> 3
            next.next = cur;// 2 -> 1
            pre = cur;
            cur = nNext;// 3
        }
        return newHead;
    }

    @Test
    public void test() {
        ListNode.printListNode(swapPairs(ListNode.getListNode(16)));
    }
}
