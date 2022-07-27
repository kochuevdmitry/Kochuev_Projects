package Stack;

import java.util.HashMap;
import java.util.Stack;

public class BracketsCheckHashStack {

        /*Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

        An input string is valid if:

        Open brackets must be closed by the same type of brackets.
        Open brackets must be closed in the correct order.*/

        public static boolean isValidBracketsSequence(String s) {
            if(s.length()%2 != 0) return false;
            HashMap<Character, Character> bracket = new HashMap<>();
            bracket.put('(',')');
            bracket.put('{','}');
            bracket.put('[',']');
            Stack<Character> stack = new Stack<>();
            for(int i=0;i<s.length();i++) {
                Character ch = s.charAt(i);
                if(ch == '(' || ch == '{' || ch == '['){
                    stack.push(ch);
                } else {
                    if(stack.isEmpty()) {
                        return false;                    }
                    if(bracket.get(stack.pop()).equals(ch)) {
                        continue;
                    } else {                        return false;                    }                }
            }
            return stack.isEmpty();        }
}
