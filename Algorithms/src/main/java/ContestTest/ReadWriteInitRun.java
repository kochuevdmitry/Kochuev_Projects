package ContestTest;
import java.io.*;

public class ReadWriteInitRun {


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

        }

}
