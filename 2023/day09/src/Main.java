import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static long[] getDifferences(long[] values)
    {
        long[] differences = new long[values.length-1];
        for(int i = 0; i < values.length-1; i++){
            differences[i] = values[i+1] - values[i];
        }
        return differences;
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("input"));
        long part1 = 0;
        long part2 = 0;
        for (String content : contents) {
            long[] numbers = Arrays.stream(content.split("\\s+")).map(Long::parseLong).mapToLong(Long::longValue).toArray();
            List<long[]> pyramid = new ArrayList<>();
            pyramid.add(numbers);
            long[] differences = getDifferences(numbers);
            for(int i = 0; i < numbers.length-1; i++){
                pyramid.add(differences);
                differences = getDifferences(differences);
            }
            long intermediate = 0;
            for(long[] xs : pyramid){
                part1 += xs[xs.length-1];
            }
            Collections.reverse(pyramid);
            for(long[] xs : pyramid)
            {
                intermediate = -intermediate + xs[0];
            }
            part2+= intermediate;
        }
        System.out.println(part1);
        System.out.println(part2);
    }
}