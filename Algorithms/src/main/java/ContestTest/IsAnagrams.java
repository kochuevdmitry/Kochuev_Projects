package ContestTest;
import java.io.*;

public class IsAnagrams {

    private static final String FILE_INPUT = "input.txt";
    private static final String FILE_OUTPUT = "output.txt";
    private static BufferedReader bufferedReader = null;
    private static BufferedWriter bufferedWriter = null;

    private static void init() throws IOException {
        bufferedReader = new BufferedReader(new FileReader(FILE_INPUT));
        bufferedWriter = new BufferedWriter(new FileWriter(FILE_OUTPUT));
    }

    private static void close() throws IOException {
        bufferedWriter.close();
        bufferedReader.close();
    }

    private static String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    private static void writeLine(char ch) throws IOException {
        bufferedWriter.write(ch);
        bufferedWriter.newLine();
    }

    public static void main(String[] args) throws Exception {
        init();
        run();
        close();

    }

    private static void run() throws Exception{
        String str1 = readLine();
        String str2 = readLine();

        int count = 1;

        if(str1.equals(str2)){
            writeLine('1');
            return;
        }

        if(str1.length() != str2.length() || str1.length() == 0){
            writeLine('0');
            return;
        } else {

            int[] dict = new int[26];

            for (int i = 0; i < str1.length(); i++){
                dict[str1.charAt(i) -'a']++;
            }

            count = str1.length();
            for(int j = 0; j< str2.length(); j++){
                if(dict[str2.charAt(j)-'a'] > 0 ){
                    dict[str2.charAt(j)-'a']--;
                    count--;
                } else {
                    writeLine('0');
                    return;
                }
            }

        }

        if(count == 0) {
            writeLine('1');
        }

    }
}