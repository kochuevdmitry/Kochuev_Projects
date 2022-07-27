package ContestTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LenghtOfOnes {

    public int lenghtOfOnes() throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(r.readLine());

        int maxCount = 0;
        int maxCountCurr = 0;
        if(n !=1){
            for(int i = 0; i < n; i++){
                int curr = Integer.parseInt(r.readLine());
                if(curr == 1){
                    maxCountCurr++;
                    if(maxCount < maxCountCurr){
                        maxCount = maxCountCurr;
                    }
                } else {
                    maxCountCurr = 0;
                }
            }
        } else {
            if(Integer.parseInt(r.readLine()) == 1){
                maxCount++;
            }
        }


        System.out.println(maxCount);
        return maxCount;
    }
}
