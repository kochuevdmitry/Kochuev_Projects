package LinkedLists;

public class LinkedListCycle {

    /*Given head, the head of a linked list, determine if the linked list has a cycle in it.
    There is a cycle in a linked list if there is some node in the list that can be reached
    again by continuously following the next pointer. Internally, pos is used to denote the
    index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
    Return true if there is a cycle in the linked list. Otherwise, return false.

    Example 1:
    Input: head = [3,2,0,-4], pos = 1
    Output: true
    Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).*/

    //time 0(N+(N-1)+(N-2)...+(N-N))
    //https://leetcode.com/problems/linked-list-cycle/
    public boolean hasLinkedListCycle(ListNode head) {
        if (head == null) return false;
        ListNode walker = head;
        ListNode runner = head;
        while (runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if (walker == runner) return true;
        }
        return false;
    }
}

