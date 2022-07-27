package ContestTest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class GenerateCorrectBrackets {
    static final String input = "input.txt";
    static final String output = "output.txt";
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new FileReader(input));
        bw = new BufferedWriter(new FileWriter(output));
        int k = Integer.valueOf(br.readLine());
        if (k > 0) {
            char[] chars = new char[k * 2];
            kBracketsAdd(chars, k, k, 0); }
        br.close();
        bw.close();
    }
    private static void kBracketsAdd(char[] in, int lCounter, int rCounter, int count)  throws Exception  {
        if (lCounter == 0 && rCounter == 0) {
            bw.write(in);
            bw.write('\n');
            return; }
        if (lCounter > 0) {
            in[count] = '(';
            kBracketsAdd(in, lCounter - 1, rCounter, count + 1);       }
        if (rCounter > 0 && lCounter < rCounter) {
            in[count] = ')';
            kBracketsAdd(in, lCounter, rCounter - 1, count + 1);        }
    } }