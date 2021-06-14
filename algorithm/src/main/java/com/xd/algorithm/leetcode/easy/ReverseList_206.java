package com.xd.algorithm.leetcode.easy;

import com.xd.algorithm.leetcode.ListNode;
import org.junit.Test;

/**
 * 给定头节点，反转链表
 *
 * @author xd
 */
public class ReverseList_206 {

    /**
     * 解题思路：
     * 1、首先，因为必须要遍历链表才能使链表反转，所以定义两个变量pre和cur用于遍历链表，从head开始，cur = head，
     * 且head的上一个节点不存在，所以pre = null
     * 2、循环中，重点在于让节点的next指针指向上一个节点，同时为了保证不丢失节点的next指针指向的节点，需要一个变量保存，
     * 所以循环中首先令temp = cur.next，然后让当前节点的next指向上一个节点，所以cur.next = pre
     * 3、分别移动pre和cur到下一个节点，也就是令pre = cur，cur = temp
     * <p>
     * 时间复杂度：O(n)，假设 n 是列表的长度，时间复杂度是 O(n)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return ListNode
     */
    public ListNode solution1(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    @Test
    public void testReverseLinkedList() {
        ListNode.printListNode(solution1(ListNode.getListNode(5)));
    }

}
