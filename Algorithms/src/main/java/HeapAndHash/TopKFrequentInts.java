package HeapAndHash;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TopKFrequentInts {

    /*Algorithm Steps:

Create a frequency table
Create a Max Heap and add all the distinct elements
Poll top k frequent elements off the Heap
Time & Space Complexity Analysis:

N = # of elements in the input array
D = # of distinct (unique) elements in the input array

Building the HashMap: O(N) time

Add all the N elements into the HashMap and add thier frequency
Building the Heap: O(D) time

https://www.geeksforgeeks.org/time-complexity-of-building-a-heap/
^Here is a proof that shows that building a heap of N elements will take O(N) time
In our case we are building a heap of D elements = O(D) time
Poll K distinct elements from the Heap: O(K log D) time

There are D elements in the Heap and we call poll() K times = O(K log D) time
Total Time Complexity = O(K log D)
Total Space Complexity = O(D), this is the size of the heap.*/
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Queue<Integer> heap = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
        for(int key : map.keySet()){ heap.add(key); }
        int[] res = new int[k];
        for(int i = 0; i < k; i++){
            res[i] = heap.poll();
        }
        return res ;
    }
}
