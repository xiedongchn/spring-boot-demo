package com.xd.algorithm.leetcode.easy;

import com.xd.algorithm.leetcode.ListNode;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 检测链表是否有环
 * [面试题 02.08]环路检测
 *
 * @author xd
 */
public class DetectCycle {

    /**
     * 快慢指针解法
     * <p>
     * 为什么快慢指针一定会相遇？
     * 当快慢指针都进入环路，我们用反证法证明两者一定相遇。
     * 假设快指针总是刚好跨过慢指针，则当慢指针在位置i，快指针在位置i+1。
     * 那么上一步慢指针就在位置i-1，快指针就在(i+1)-2=i-1，两者必定相遇。
     * 再假设慢指针在i，快指针在i+2，则上两步，慢指针在i-2，快指针也在i-2，仍然会相遇。
     *
     * @param head 链表头节点
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;// 快慢指针都从头开始走
        while (fast != null && fast.next != null) {
            //快慢指针，快指针每次走两步，慢指针每次走一步
            fast = fast.next.next;
            slow = slow.next;
            //先判断是否有环，
            if (slow == fast) {
                //确定有环之后才能找环的入口
                while (head != slow) {
                    //两相遇指针，一个从头结点开始，
                    //一个从相遇点开始每次走一步，直到
                    //再次相遇为止
                    head = head.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }

    /**
     * 通过Set判断是否存在环
     *
     * @param head 链表头节点
     * @return ListNode
     */
    public ListNode detectCycleBySet(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            //如果重复出现说明有环
            if (!set.add(head)) {
                return head;
            }
            //否则就把当前节点加入到集合中
            head = head.next;
        }
        return null;
    }

    @Test
    public void testDetectCycle() {
        ListNode head = new ListNode();
        ListNode cycle = new ListNode();
        head.next = new ListNode();
        head.next.next = new ListNode();
        head.next.next.next = new ListNode();
        head.next.next.next = cycle;
        head.next.next.next.next = new ListNode();
        head.next.next.next.next.next = new ListNode();
        head.next.next.next.next.next.next = new ListNode();
        head.next.next.next.next.next.next.next = cycle;
        System.out.println(detectCycle(head));
    }
}
