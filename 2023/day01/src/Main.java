import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static String digitize(String input) {
        StringBuilder result = new StringBuilder();
        String[] numbers = {"zero","one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for(int i = 0; i < input.length(); i++){
            if(Character.isDigit(input.charAt(i))){
                // part 1
                result.append(input.charAt(i));
            } else {
                // part 2
                for(int j = 0; j < numbers.length; j++) {
                    String number = numbers[j];
                    int endOfNumber = i + number.length();
                    if (endOfNumber <= input.length() && input.substring(i, endOfNumber).equals(number)){
                        result.append(j);
                    }
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("input"));
        int sum = 0;
        for (String content:contents) {
            String digits = digitize(content);
            sum += 10 * (digits.charAt(0) - '0') + (digits.charAt(digits.length()-1) - '0');
        }
        System.out.println(sum);
    }
}