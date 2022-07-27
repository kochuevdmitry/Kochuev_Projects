
import Anagrams.AllAnagramsInString;
import BinarySearch.BinarySearch;
import HashTable.FourSumHashSet;
import HeapAndHash.TopKFreaquentElements;
import HeapAndHash.TopKFrequentInts;
import LinkedLists.ReverseLinkedList;
import Others.GenerateCorrectBrackets;
import ContestTest.*;
import SlidingWindow.LongestRepeatingCharacterReplacement;
import SlidingWindow.MedianSlidingWindow;
import SlidingWindow.SlidingWindowMaximum;
import Sorting.MergeIntervals;
import Stack.WrongBrackets;
import LinkedLists.LinkedListCycle;
import LinkedLists.ListNode;
import LinkedLists.MergeKSortedLists;
import Tree.PathSumTree;
import TwoPointers.MaxArea;
import TwoPointers.PartitionLabels;

public class Main {

    public static void main(String[] args) {

        /*ListNode[] lists = new ListNode[1];
        lists[0] = new ListNode();
        mergeKSortedLists(lists);*/

        /*ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1, new ListNode(4, new ListNode(5)));
        lists[1] = new ListNode(1, new ListNode(3, new ListNode(4)));
        lists[2] = new ListNode(2, new ListNode(6));
        mergeKSortedLists(lists);*/

        /*ListNode listTo = new ListNode(2);
        ListNode listc = new ListNode(0, new ListNode(-4, new ListNode(5, new ListNode(6, listTo))));
        listTo.next = listc;
        ListNode list = new ListNode(3, listTo);
        linkedCycle(list);*/

        /*ListNode list = new ListNode(1, new ListNode(2));
        list.next.next = list;
        linkedCycle(list);*/

        /*ListNode list1 = new ListNode(9, new ListNode(9, new ListNode(9)));
        ListNode list2 = new ListNode(9, new ListNode(9));
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        ListNode res = addTwoNumbers.addTwoNumbersRecursion(list1, list2);
        while (true){
            System.out.println(res.val);
            if (res.next != null)
                res = res.next;
            else break;
        }*/

        /*ListNode list = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        //ListNode list = new ListNode();
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
        printResult(list);
        printResult(reverseLinkedList.reverseListK(list));
*/

        //System.out.println(BinarySearch.binarySearchIntArray(new int[]{ 1, 2, 3, 4, 5, 6}, 1));

        /*GuessNumber.pic = 11;
        System.out.println(GuessNumber.guessNumber(10));*/
        //System.out.println(Math.log(10) + " " + Math.log(10)/Math.log(3));

        /*System.out.println(SearchInRotatedArray.findMin(new int[]{ 2, 3, 4, 0, 1 }));
        System.out.println(SearchInRotatedArray.findMin(new int[]{ 4, 0, 1, 2, 3, }));
        System.out.println(SearchInRotatedArray.findMin(new int[]{ 1, 0 }));
        System.out.println(SearchInRotatedArray.findMin(new int[]{ 0, 1 }));
        System.out.println(SearchInRotatedArray.findMin(new int[]{ 2, 0 , 1}));
        System.out.println(SearchInRotatedArray.findMin(new int[]{ 4,5,6,7,0,1,2 }));
        System.out.println(SearchInRotatedArray.findMin(new int[]{ 11,13,15,17  }));
*/

        //System.out.println(SingleNumber.singleNumber(new int[]{ 1,1,2,3,2,3,4 }));

       /* System.out.println(TwoSumHashSet.twoSum(new int[]{ 2,2,2,15 }, 4, 0)[0]
                + " " + TwoSumHashSet.twoSum(new int[]{ 2,2,2,15  }, 4, 0)[1]);*/

        //System.out.println(FourSumHashSet.fourSum(new int[]{ 1,2,3,4,5,6 }, 15, 3));

        /*BracketsSequences.bracketsSequences("", 6,0,0);
        System.out.println(BracketsSequences.time);*/

        //System.out.println(Anagrams.isAnagramCharsSorted(" taк", "taк  "));

        /*System.out.println(GroupAnagrams.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println(GroupAnagrams.groupAnagrams(new String[]{"", ""}));*/



        //System.out.println(AllAnagramsInString.findAnagramsBest("absaadabaaabsaaasdfaaaasdfaaefwefefefaaaaaaa","asa"));
       /* System.out.println(AllAnagramsInString.findAnagramsLong("aaabaabaaasaaasdfaaaasdfaaefwefefefaaaaaaa","aa"));
        System.out.println(AllAnagramsInString.findAnagramsMyMap("aaabaabaaasaaasdfaaaasdfaaefwefefefaaaaaaa","aa"));
*/
        //System.out.println(BracketsCheckHashStack.isValidBracketsSequence("( () )"));

        /*char[][] islands =
                {{'1','1','0','1','0'},
                {'1','1','1','1','0'},
                {'1','1','1','0','0'},
                {'0','0','1','0','1'}};
        System.out.println(IslandsDFS.numIslands(islands));*/

        //System.out.println(WrongBrackets.wrongBracketsInSequence(")(((a)))())()("));

        //System.out.println(MergeIntervals.merge(new int[][]{{1,3},{3,4}}));

        System.out.println(TopKFreaquentElements.topKFrequent(
                new String[]{"the","day","is","sunny","the","the","the","sunny","is","is"}
                ,4));
        System.out.println(TopKFreaquentElements.topKFrequent(
                new String[]{"i","love","leetcode","i","love","coding"}
                ,2));

        //printMassive(TopKFrequentInts.topKFrequent(new int[]{1,1,1,2,2,3},2));

        //System.out.println(PartitionLabels.partitionLabels("ababcbacadefegdehijhklij"));

        //System.out.println(MaxArea.maxArea(new int[]{1,8,25,3,55,4,8,3,7}));

        //printMassive(SlidingWindowMaximum.slidingWindowMax(new int[]{1,8,-1,-3,5,3,6,7},3));

        //GenerateCorrectBrackets.generateCorrectBrackets(3);

        //GenerateCorrectBrackets.testUniqueNumbers();

        //InterestingTrip.interestingTrip(); // не срабатывает 15 тест в контексте яндекса

        //printDoubleMassive(MedianSlidingWindow.medianSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},5));
        //printDoubleMassive(MedianSlidingWindow.medianSlidingWindowArray(new int[]{1,3,-1,-3,5,3,6,7},5));

        //System.out.println(LongestRepeatingCharacterReplacement.characterReplacement("ABAB",2));

        //PathSumTree.runInit();
    }

    public static void printDoubleMassive(double[] nums){
        for(int i=0; i < nums.length; i++){
            System.out.println(nums[i]);
        }
    }

    public static void printMassive(int[] nums){
        for(int i=0; i < nums.length; i++){
            System.out.println(nums[i]);
        }
    }

    public static void printResult(ListNode res) {
        while (true) {
            System.out.println(res.val);
            if (res.next != null)
                res = res.next;
            else break;
        }
    }

    public static void linkedCycle(ListNode list) {
        LinkedListCycle linkedListCycle = new LinkedListCycle();
        System.out.println(linkedListCycle.hasLinkedListCycle(list));
    }

    public static void mergeKSortedLists(ListNode[] lists) {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        ListNode ln = mergeKSortedLists.mergeKListsCompareOneByOneSpace1(lists);
        while (ln != null) {
            System.out.println(ln.val);
            ln = ln.next;
        }
    }


}
