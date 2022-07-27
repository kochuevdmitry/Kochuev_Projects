package Others;

import java.util.ArrayList;

public class BracketsSequences {

    public static ArrayList<String> cur = new ArrayList<>();
    public static int time = 0;

    public static void bracketsSequences(String res, int n, int opened, int closed) {
        time++;
        if (res.length() == 2 * n) {
            if (opened == closed) {
                cur.add(res);
            }
        } else {
            if (opened < n) bracketsSequences(res + "(", n, opened + 1, closed);
            if (opened > closed){
                bracketsSequences(res + ")", n, opened, closed + 1);
            }
        }
    }
}
