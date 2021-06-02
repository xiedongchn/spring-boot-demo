package com.xd.algorithm.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 题解
 *
 * @author xd
 */
public class SolutionTest {

    /**
     * 题目：给定头节点，反转链表
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
    public ListNode reverseList(ListNode head) {
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
        printListNode(reverseList(getListNode(5)));
    }

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
    public void testSwapPairs() {
        printListNode(swapPairs(getListNode(16)));
    }

    /**
     * 检测链表是否有环，快慢指针解法
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

    /**
     * 是否合法的括号，例如，输入：
     * "()"，返回true
     * "()[]{}"，返回true
     * "(]"，返回false
     * "([)]"，返回false
     * "{[]}"，返回true
     * "))"，返回false
     * "(("，返回false
     *
     * @param s 需要校验的字符串
     * @return boolean
     */
    public boolean isValidBrackets(String s) {
        if (s == null || s.length() < 2) {
            return false;
        }
        char[] charArray = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : charArray) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                continue;
            }
            // 如果是空栈，说明没有与之匹配的左括号
            if (stack.size() <= 0) {
                return false;
            }
            char p = stack.pop();
            if (p == '(' && c != ')' || p == '[' && c != ']' || p == '{' && c != '}') {
                return false;
            }
        }
        //return pushCount == popCount;// 记录入栈和出栈次数是否一直，如果不一致，说明不匹配
        // 不用通过计数器记录出入栈次数，直接判断栈是否是空的，如果是空的说明全匹配上了
        return stack.isEmpty();
    }

    /**
     * 是否合法的括号，例如，输入：
     * "()"，返回true
     * "()[]{}"，返回true
     * "(]"，返回false
     * "([)]"，返回false
     * "{[]}"，返回true
     * "))"，返回false
     * "(("，返回false
     *
     * @param s 需要校验的字符串
     * @return boolean
     */
    public boolean isValidBrackets1(String s) {
        int len;
        do {
            len = s.length();
            // 逻辑上没错，代码也很简洁，但是replace本身的操作是 O(n) 的，导致算法的最坏时间复杂度是 O(n^2)
            s = s.replace("()", "").replace("[]", "").replace("{}", "");
        } while (len != s.length());
        return s.length() == 0;
    }

    @Test
    public void testIsValidBrackets() {
        System.out.println(isValidBrackets("()"));
        System.out.println(isValidBrackets("()[]{}"));
        System.out.println(isValidBrackets("(]"));
        System.out.println(isValidBrackets("([)]"));
        System.out.println(isValidBrackets("{[]}"));
    }

    @Test
    public void test1() {
        System.out.println(3 / 2);
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

    /**
     * 根据传入的size构造相应个数的链表
     *
     * @param size 链表大小
     * @return 链表头节点
     */
    public ListNode getListNode(int size) {
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
    public void printListNode(ListNode head) {
        StringBuilder numStr = new StringBuilder().append(head.num);
        ListNode cur = head;
        while (cur.next != null) {
            numStr.append(",").append(cur.next.num);
            cur = cur.next;
        }
        System.out.println(numStr);
    }

}
