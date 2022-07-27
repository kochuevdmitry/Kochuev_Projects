package Anagrams;

import java.util.Arrays;
import java.util.HashMap;

public class Anagrams {

    //https://leetcode.com/problems/valid-anagram/
    public static boolean isAnagramCharsSorted(String s, String t) {
        char charArray1[] = s.toCharArray();
        Arrays.sort(charArray1);
        String sorted1 = new String(charArray1);
        char charArray2[] = t.toCharArray();
        Arrays.sort(charArray2);
        String sorted2 = new String(charArray2);
        return sorted1.equals(sorted2);
    }

    public static boolean isAnagramCharDict(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        if (sLen != tLen) {
            return false;
        }
        int[] letters = new int[1300];//26 если только англ низ регистр

        for (int j=0; j<sLen;j++) {
            letters[s.charAt(j) -'a']++;
        }

        for (int i = 0; i < tLen; i++) {
            if (--letters[t.charAt(i)-'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnagramMemoriest(String s, String t) {
            if (s.length() != t.length())
                return false;
            HashMap<Character, Integer> map = new HashMap<Character, Integer>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (map.containsKey(c))
                    map.put(c, map.get(c) + 1);
                else
                    map.put(c, 1);
            }
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                if (map.containsKey(c)) {
                    if (map.get(c) == 1)
                        map.remove(c);
                    else
                        map.put(c, map.get(c) - 1);
                } else
                    return false;
            }
            if (map.size() > 0)
                return false;
            return true;
        }
}
