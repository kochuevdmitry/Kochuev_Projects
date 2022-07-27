package LinkedLists;

import LinkedLists.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeKSortedLists {

    //https://leetcode.com/problems/merge-k-sorted-lists/solution/
    //You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
    //Merge all the linked-lists into one sorted linked-list and return it.

    /*Time complexity : O(N\log N) where N is the total number of nodes.
    Collecting all the values costs O(N) time.
    A stable sorting algorithm costs O(N\log N) time.
    Iterating for creating the linked list costs O(N) time.

    Space complexity : O(N).
    Sorting cost O(N) space (depends on the algorithm you choose).
    Creating a new linked list costs O(N) space.*/
    public ListNode mergeKListsSolutionBrute(ListNode[] lists) {
        List<Integer> l = new ArrayList<Integer>();

        for (ListNode ln : lists) {
            while (ln != null) {
                l.add(ln.val);
                ln = ln.next;
            }
        }

        Collections.sort(l);

        ListNode head = new ListNode(0);
        ListNode h = head;
        for (int i : l) {
            ListNode t = new ListNode(i);
            h.next = t;
            h = h.next;
        }
        h.next = null;
        return head.next;
    }

    /*Complexity Analysis
    Time complexity : O(kN) where k is the number of linked lists.

    Almost every selection of node in final linked costs O(k) (k-1 times comparison).
    There are NN nodes in the final linked list.
    Space complexity :
    O(n) Creating a new linked list costs O(n) space.
    O(1) It's not hard to apply in-place method - connect selected nodes instead of creating new nodes to fill the new linked list.*/
    public ListNode mergeKListsCompareOneByOneSpacen(ListNode[] lists) {
        int min_index = 0;
        ListNode head = new ListNode(0);
        ListNode h = head;
        while (true) {
            boolean isBreak = true;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    if (lists[i].val < min) {
                        min_index = i;
                        min = lists[i].val;
                    }
                    isBreak = false;
                }

            }
            if (isBreak) {
                break;
            }
            ListNode a = new ListNode(lists[min_index].val);
            h.next = a;
            h = h.next;
            lists[min_index] = lists[min_index].next;
        }
        h.next = null;
        return head.next;
    }

    /*Complexity Analysis
    Time complexity : O(kN) where k is the number of linked lists.

    Almost every selection of node in final linked costs O(k) (k-1 times comparison).
    There are NN nodes in the final linked list.
    Space complexity :
    O(1) It's not hard to apply in-place method - connect selected nodes instead of creating new nodes to fill the new linked list.*/
    public ListNode mergeKListsCompareOneByOneSpace1(ListNode[] lists) {
        int min_index = 0;
        ListNode head = new ListNode();
        ListNode h = head;
        int time = 0;
        while (true) {
            boolean isBreak = true;
            int min = Integer.MAX_VALUE;

                for (int i = 0; i < lists.length; i++) {
                    if (lists[i] != null) {
                        if (lists[i].val < min) {
                            min_index = i;
                            min = lists[i].val;
                        }
                        isBreak = false;
                    }
                }

            if (isBreak) {
                break;
            }

            h.next = lists[min_index];
            h = h.next;
            lists[min_index] = lists[min_index].next;
        }
        h.next = null;
        return head.next;
    }
}
