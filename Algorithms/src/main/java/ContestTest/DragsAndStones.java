package ContestTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DragsAndStones {

    public int countDrags(String drags, String stones) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        drags = r.readLine();
        stones = r.readLine();

        int count = 0;
        for(int i = 0; i < stones.length(); i++) {
            if (drags.indexOf(stones.charAt(i))>=0){
                count++;
            }
        }
        System.out.println(count);
        return count;
    }
}
