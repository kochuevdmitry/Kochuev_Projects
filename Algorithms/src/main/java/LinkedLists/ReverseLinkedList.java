package LinkedLists;

public class ReverseLinkedList {

    //https://leetcode.com/problems/reverse-linked-list/

    public ListNode reverseListK(ListNode head) {
        if(head==null || head.next==null)
            return head;
        ListNode nextNode=head.next;
        ListNode newHead=reverseList(nextNode);
        nextNode.next=head;
        head.next=null;
        return newHead;
    }

    public ListNode reverseList(ListNode head) {

        if (head == null || head.next == null)
            return head;

        ListNode tmp1 = head;
        ListNode tmp2 = head.next;
        head.next = null;
        ListNode tmp3;
        ListNode newHead = head;

        if (tmp2.next != null) {
            tmp3 = tmp2.next;
        } else {
            tmp2.next = tmp1;
            return tmp2;
        }

        if (tmp3.next == null){
            tmp2.next = tmp1;
            tmp3.next = tmp2;
            return tmp3;
        }

        while(true){
            if (tmp3.next != null){
                newHead = tmp3.next;
                tmp2.next = tmp1;
                tmp1 = tmp2;
                tmp3.next = tmp2;
                tmp2 = tmp3;
                tmp3 = newHead;
                if (newHead.next == null){
                    newHead.next =tmp2;
                    break;
                }
            } else {
                break;
            }
        }
        return newHead;
    }
}
