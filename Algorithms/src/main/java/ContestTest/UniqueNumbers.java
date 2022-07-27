package ContestTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UniqueNumbers {

    public void uniqueNumbers() throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(r.readLine());


        if(n>0)
        {
            int prev = Integer.parseInt(r.readLine());
            System.out.println(prev);

            while(r.readLine() != null){
                int curr = Integer.parseInt(r.readLine());
                if(curr > prev) {
                    System.out.println(curr);
                    prev = curr;
                }

            }
        }
    }

    public static void testUniqueNumbers(){

        int[] input = {2,4,8,8,8};

        int prev = input[0];
        System.out.println(prev);

        for (int i =0;i < input.length;i++) {
            int curr = input[i];
            if (curr > prev) {
                System.out.println(curr);
                prev = curr;
            }
        }
    }
}
