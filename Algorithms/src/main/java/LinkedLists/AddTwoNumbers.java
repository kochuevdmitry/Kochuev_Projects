package LinkedLists;

public class AddTwoNumbers {
/*
    You are given two non-empty linked lists representing two non-negative integers.
    The digits are stored in reverse order, and each of their nodes contains a single digit.
    Add the two numbers and return the sum as a linked list.
    You may assume the two numbers do not contain any leading zero, except the number 0 itself.

            Example 1:
    Input: l1 = [2,4,3], l2 = [5,6,4]
    Output: [7,0,8]
    Explanation: 342 + 465 = 807.

    Example 2:
    Input: l1 = [0], l2 = [0]
    Output: [0]

    Example 3:
    Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
    Output: [8,9,9,9,0,0,0,1]*/

    //https://leetcode.com/problems/add-two-numbers/

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    public ListNode resList = new ListNode(0);
    public ListNode head = resList;
    public int carry = 0;

    public ListNode addTwoNumbersRecursion(ListNode l1, ListNode l2) {
        int sum = l1.val + l2.val + carry;
        carry  = sum / 10;
        resList.next = new ListNode(sum % 10);
        resList = resList.next;

        if(l1.next != null && l2.next != null)
            addTwoNumbersRecursion(l1.next, l2.next);
        else if (l1.next != null)
            addTwoNumbersRecursion(l1.next, new ListNode(0));
        else if (l2.next != null)
            addTwoNumbersRecursion(new ListNode(0), l2.next);
        else if (carry > 0)
        {
            resList.next = new ListNode(1);
            resList = resList.next;
        }
        return head.next;
    }
}
