import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;


public class Main {

    public static char[][] transpose(char[][] matrix) {
        char[][] transposed = new char[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix.length; j++)
                transposed[i][j] = matrix[j][i];
        return transposed;
    }

    public static boolean isVerticalSymmetric(char[][] matrix, int symmetryOffset, int tolerance) {
        for (char[] chars : matrix) {
            int xLeft = symmetryOffset;
            int xRight = symmetryOffset + 1;
            while (xLeft >= 0 && xRight < matrix[0].length) {
                if (chars[xLeft] != chars[xRight]) {
                    tolerance--;
                }
                xLeft--;
                xRight++;
            }
        }
        return tolerance == 0;
    }

    public static long findReflectionVertical(char[][] matrix) {
        for (int offset = 0; offset < matrix[0].length - 1; offset++) {
            if (isVerticalSymmetric(matrix, offset, 0)) {
                return offset + 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        String[] inputs = Files.readString(Paths.get("input")).split("\n\n");
        long sum = 0;
        for (String input : inputs) {
            char[][] pattern = Arrays.stream(input.split("\n")).map(String::toCharArray).toArray(char[][]::new);
            sum += findReflectionVertical(pattern) + 100 * findReflectionVertical(transpose(pattern));
            System.out.println(findReflectionVertical(pattern) + "\t" + findReflectionVertical(transpose(pattern)));
        }
        System.out.println(sum);
    }
}