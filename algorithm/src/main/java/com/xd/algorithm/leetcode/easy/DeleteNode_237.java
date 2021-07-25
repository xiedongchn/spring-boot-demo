package com.xd.algorithm.leetcode.easy;

import com.xd.algorithm.leetcode.ListNode;

/**
 * 给定链表中一个节点，该节点不是最后的节点，删除该节点
 *
 * @author xd
 */
public class DeleteNode_237 {

    /**
     * 由于给定的非最后的节点，因此只要让当前节点的值等于下一节点的值，然后删除下一节点即可
     * @param node 要删除的节点
     */
    public void solution1(ListNode node) {
        if (node == null || node.next == null) {
            return;
        }
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
