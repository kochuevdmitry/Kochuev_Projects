package Others;

import java.util.Collections;
import java.util.Stack;
import java.util.TreeSet;

public class GenerateCorrectBrackets {

    public static void generateCorrectBrackets(int k) {
        if (k > 0) {
            k=3;
            char[] chrs = new char[k * 2];
            kBracketsAdd(chrs, k, k, 0);
            //kBracketsAddSB(new StringBuilder(), k, k);
            //lowMemorySolution(k*2);
        }
    }

    private static void kBracketsAddSB(StringBuilder in, int lCounter, int rCounter){
        if (lCounter == 0 && rCounter == 0) {
            System.out.println(in);
            //in.setLength(0);
            //in = new StringBuilder();
            return;
        }
        if (lCounter > 0) {
            in.append("(");
            kBracketsAddSB(in, lCounter - 1, rCounter);
        }
        if (rCounter > 0 && lCounter < rCounter) {
            in.append(")");
            kBracketsAddSB(in, lCounter, rCounter - 1);
        }
    }

    private static void kBracketsAdd(char[] in, int lCounter, int rCounter, int count) {
        if (lCounter == 0 && rCounter == 0) {
            System.out.println(in);
            in = null;
            count = 0;
            return;
        }
        if (lCounter > 0) {
            in[count] = '(';
            kBracketsAdd(in, lCounter - 1, rCounter, count + 1);
        }
        if (rCounter > 0 && lCounter < rCounter) {
            in[count] = ')';
            kBracketsAdd(in, lCounter, rCounter - 1, count + 1);
        }
        count=0;
    }

    private static void lowMemorySolution(int n){
        if (n < 2) return;
        char[] chrs = new char[n];
        for (int i = 0; i < n/2; i++) {
            chrs[i] = '(';
        }
        for (int i = n/2; i < n; i++) {
            chrs[i] = ')';
        }
        System.out.println(chrs);
        do {
            int i = n - 1;
            int c = 0;
            while (i >= 0) {
                c += chrs[i] == ')' ? -1 : 1;
                if ((c < 0) && (chrs[i] == '(')) break;
                --i;
            }
            if (i < 0) break;

            chrs[i++] = ')';
            int ind = i;
            for (; i < n; i++) {
                chrs[i] = (i<= (n-ind+c)/2+ind) ? '(' : ')';
            }
            System.out.println(chrs);
        }while (true);
    }

    public static void testUniqueNumbers(){

        int[] input = {};

        /*TreeSet<Integer> res = new TreeSet<>();
        for (int i = 0; i < input.length; i++){
            res.add(input[i]);
        }
        System.out.println(res);*/

        if (input.length>0) {
            int prev = input[0];
            System.out.println(prev);

            for (int i = 0; i < input.length; i++) {
                int curr = input[i];
                if (curr > prev) {
                    System.out.println(curr);
                    prev = curr;
                }
            }
        }
    }
}
