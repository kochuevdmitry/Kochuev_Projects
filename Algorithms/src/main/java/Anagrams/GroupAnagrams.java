package Anagrams;

import java.util.*;

public class GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map <String , List<String>> map = new HashMap<>();
        for(String s : strs){
            char charArray[] = s.toCharArray();
            Arrays.sort(charArray);
            String sorted = new String(charArray);
            if(!map.containsKey(sorted)) {
                map.put(sorted, new ArrayList<>());
            }
            map.get(sorted).add(s);
        }
        return new ArrayList<>(map.values());
    }



    public static List<List<String>> groupAnagrams1(String[] strs) {
        Map <String , List<String>> map = new HashMap<>();
        for(String s : strs){
            char charArray[] = s.toCharArray();
            Arrays.sort(charArray);
            String sorted = new String(charArray);

            if(!map.containsKey(sorted)) {
                map.put(sorted, new LinkedList<String>());
            }
            map.get(sorted).add(s);
        }
        return new LinkedList<>(map.values());
    }
}
