package controllers;

/**
 * Created by rashok on 9/6/14.
 */
public class FizzBuzzUtil {

    public static String speakFizBuzz (int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= number; i++) {
            sb.append(", ");
            if (i % 15 == 0) {
                sb.append("FizzBuzz");
            } else if (i % 3 == 0) {
                sb.append("Fizz");
            } else if (i % 5 == 0) {
                sb.append("Buzz");
            } else {
                sb.append(i);
            }
        }
        return sb.toString();
    }
}
