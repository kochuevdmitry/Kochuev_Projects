package HeapAndHash;

import java.util.*;

public class TopKFreaquentElements {
    /*Given an array of strings words and an integer k, return the k most frequent strings.
Return the answer sorted by the frequency from highest to lowest.
Sort the words with the same frequency by their lexicographical order.
Example 1:
Input: words = ["i","love","leetcode","i","love","coding"], k = 2
Output: ["i","love"]
Explanation: "i" and "love" are the two most frequent words.
Note that "i" comes before "love" due to a lower alphabetical order.*/

    public static List<String> topKFrequentStrings(String[] words, int k) {
        List<String> result = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (map.containsKey(words[i]))
                map.put(words[i], map.get(words[i]) + 1);
            else
                map.put(words[i], 1);
        }
        PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (map.get(s1) == map.get(s2)) {
                    return s1.compareTo(s2);
                }
                return map.get(s2) - map.get(s1);            }        });
        pq.addAll(map.keySet());
        for (int i = 0; i < k; i++) {
            if (!pq.isEmpty()) {
                result.add(pq.poll());
            }        }
        return result;    }
}
