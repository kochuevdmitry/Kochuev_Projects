package BinarySearch;

public class GuessNumber {

    public static int pic = 3;

    public static int guessNumber(int n) {
        int left = 1, right = n;
        int guess, check;
        while (left <= right) {
            guess = left + (right - left) / 2;
            check = guess(guess);
            if (check == 0) return guess;
            if (check == 1) left = guess + 1;
            else right = guess - 1;
        }
        return -1;
    }

    private static int guess(int guess) {
        if (guess > pic) return -1;
        if (guess < pic) return 1;
        return 0;
    }
}
