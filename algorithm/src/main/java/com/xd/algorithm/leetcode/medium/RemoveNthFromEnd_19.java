package com.xd.algorithm.leetcode.medium;

import com.xd.algorithm.leetcode.ListNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 删除链表倒数第n个节点
 *
 * @author xd
 */
public class RemoveNthFromEnd_19 {

    /**
     * 双指针解法，第一个指针先走n步，第二个指针再出发，并记录第二个指针的前后节点
     * 然后两个指针同步往前一直走，此时也不断更新第二个指针的前后节点
     * 当走到末尾时，删除第二个指针指向的节点，将第二个指针的前后节点连接起来即可
     *
     * @param head 头节点
     * @param n    第n个
     * @return 头节点
     */
    public ListNode solution1(ListNode head, int n) {
        if (head == null || n < 0) {
            return head;
        }
        // 当前指针率先移动n位
        ListNode cur = head;
        // pre指针等cur指针移动n位之后再移动，并且初始值为null
        // 让pre节点永远指向要删除的节点的前一个节点
        // 删除节点即是让pre指针指向的节点其next指针等于pre.next.next
        ListNode pre = null;
        while (cur != null) {
            // cur指针不停往后走
            cur = cur.next;
            // n一直减1直至为0
            if (n > 0) {
                n--;
            } else {
                // n为0后，pre指针与cur指针相隔n个元素
                pre = pre == null ? head : pre.next;
            }
        }
        // 若pre指针为null，说明要删除的元素是头节点
        if (pre == null) {
            head = head.next;
        } else {
            // 否则如下，删除pre.next节点，因为只要链表不是空链表，则pre.next永远不为空
            // 所以不必担心pre.next为空报空指针异常
            pre.next = pre.next.next;
        }
        return head;
    }

    /**
     * 删除链表倒数第n个节点
     *
     * @param head 链表头节点
     * @param n    第n个节点
     */
    public ListNode solution2(ListNode head, int n) {
        if (head == null || n < 0) {
            return head;
        }
        // 通过map的方式，将每个链表元素存起来，全部存起来之后取出指定的元素删除掉，并把前后元素链接起来即可
        // 性能和速度均不如双指针
        Map<Integer, ListNode> map = new HashMap<>();
        ListNode cur = head;
        // map的key从0开始依次递增1
        int i = 0;
        while (cur != null) {
            map.put(i, cur);
            i++;
            cur = cur.next;
        }
        if (n > i) {
            return head;
        }
        ListNode pre = map.get(i - n - 1);
        if (pre == null) {
            head = head.next;
            return head;
        }
        cur = map.get(i - n);
        pre.next = cur.next;
        return head;
    }

    @Test
    public void testSolution1() {
        ListNode head = ListNode.getListNode(10);
        int n = 1;
        head = solution2(head, n);
        ListNode.printListNode(head);
    }
}
