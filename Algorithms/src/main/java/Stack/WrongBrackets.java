package Stack;

import javax.swing.plaf.IconUIResource;
import java.util.*;

public class WrongBrackets {
    /*/find wrong brackets/*/

    public static Stack<Character> wrongBracketsInSequence(String s) {
        HashMap<Character, Character> bracket = new HashMap<>();
        bracket.put('(', ')');
        Stack<Character> stack = new Stack<>();
        Stack<Integer> positionsOpen = new Stack<>();
        Stack<Integer> positionsWrong = new Stack<>();
        Stack<Character> wrong = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (ch == '(') {
                stack.push(ch);
                positionsOpen.push(i);
            } else {
                if (ch == ')') {
                    if (!stack.isEmpty() && bracket.get(stack.peek()).equals(ch)) {
                        stack.pop();
                        positionsOpen.pop();
                        continue;
                    } else {
                        wrong.push(')');
                        positionsWrong.push(i);
                    }
                }
            }
        }
        Stack<Character> wrongsSequence = new Stack<>();
        while (!stack.isEmpty() || !wrong.isEmpty()) {
            if (!positionsOpen.isEmpty() && !positionsWrong.isEmpty()) {
                if (positionsOpen.peek() > positionsWrong.peek()) {
                    wrongsSequence.push(stack.pop());
                    positionsOpen.pop();
                } else {
                    wrongsSequence.push(wrong.pop());
                    positionsWrong.pop();
                }
            } else {
                if (!positionsOpen.isEmpty()){
                    while (!stack.isEmpty()){
                        wrongsSequence.push(stack.pop());
                    }
                } else {
                    while (!wrong.isEmpty()){
                        wrongsSequence.push(wrong.pop());
                    }
                }
            }
        }

        return wrongsSequence;
    }
}
