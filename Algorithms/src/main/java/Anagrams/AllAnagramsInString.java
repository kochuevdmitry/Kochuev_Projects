package Anagrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAnagramsInString {

    //https://leetcode.com/problems/find-all-anagrams-in-a-string/
    //Не самые лучшие реализации по ресурсам, лучшая смотри SlidingWindow

    //too long timeMilis
    public static List<Integer> findAnagramsMyMap(String s, String p) {
        int time = 0;
        long startTime = System.nanoTime();
        List<Integer> res = new ArrayList<>();
        if (s.length() < p.length()) return res;

        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Integer> mapClone = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            time++;
        }
        mapClone = (HashMap<Character, Integer>) map.clone();

        int counter = map.size();

        for (int i = 0; i <= s.length()-p.length(); i++) {
            for(int e = i; e < i+p.length();e++) {
                time++;
                char check = s.charAt(e);
                if (map.containsKey(check)) {
                    if (map.get(check) - 1 == 0) {
                        map.remove(check);
                    } else map.put(check, map.get(check) - 1);
                    if (map.size() == 0) {
                        res.add(i);
                        map = (HashMap<Character, Integer>) mapClone.clone();
                    }
                } else {
                    i = e;
                    map = (HashMap<Character, Integer>) mapClone.clone();
                    break;
                }
            }
        }
        long endTime = System.nanoTime();
        long durationInNano = (endTime - startTime);
        System.out.println(time + " " + durationInNano);
        return res;
    }

    //Almost best dict Map, better look for dict with int[] char
    public static List<Integer> findAnagramsBest(String s, String p) {
        int time = 0;
        long startTime = System.nanoTime();
        List<Integer> res = new ArrayList<>();
        if (p.length() > s.length()) {
            return res;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            time++;
        }

        int begin = 0, end = 0;
        int counter = map.size();

        while (end < s.length()) {
            time++;
            char ec = s.charAt(end);
            if (map.containsKey(ec)) {
                map.put(ec, map.get(ec) - 1);
                if (map.get(ec) == 0) counter--;
            }
            end++;

            while (counter == 0) {
                time++;
                char bc = s.charAt(begin);
                if (map.containsKey(bc)) {
                    map.put(bc, map.get(bc) + 1);
                    if (map.get(bc) > 0) counter++;
                }
                if (end - begin == p.length()) {
                    res.add(begin);
                }
                begin++;
            }
        }
        long endTime = System.nanoTime();
        long durationInNano = (endTime - startTime);
        System.out.println(time + " " + durationInNano);
        return res;
    }

    public static List<Integer> findAnagramsLong(String s, String p) {
        long startTime = System.nanoTime();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.length() - i >= p.length()) {
                if (isAnagramFaster(s.substring(i, i + p.length()), p)) res.add(i);
            }
        }
        long endTime = System.nanoTime();
        long durationInNano = (endTime - startTime);
        System.out.println(durationInNano);
        return res;
    }

    public static boolean isAnagramFaster(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }
        int[] letters = new int[26];//26 если только англ низ регистр

        for (int j=0; j<s.length();j++) {
            letters[s.charAt(j) -'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            if (--letters[t.charAt(i)-'a'] < 0) {
                return false;
            }
        }

        return true;
    }
}
