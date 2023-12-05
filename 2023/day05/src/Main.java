import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.LongStream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static long performMapping(Almanac almanac, long seed) {
        for (String key : Almanac.order) {
            List<Long[]> mapping = almanac.mappings.get(key);
            for (Long[] map : mapping) {
                long destRangeStart = map[0];
                long srcRangeStart = map[1];
                long rangeLength = map[2];
                if (seed >= srcRangeStart && seed < srcRangeStart + rangeLength) {
                    seed += destRangeStart - srcRangeStart;
                    break;
                }
            }
        }
        return seed;
    }

    public static Almanac parseInput(List<String> input) {
        String currentKey = "";
        Long[] seeds = null;
        Map<String, List<Long[]>> mappings = new HashMap<>();
        for (String line : input) {
            if (line.isEmpty()) { // new key
                currentKey = "";
            } else {
                String[] tokens = line.split("\\s+");
                if (tokens[0].equals("seeds:")) // seed input
                {
                    seeds = Arrays.stream(tokens, 1, tokens.length).map(Long::parseLong).toArray(Long[]::new);
                } else if (Character.isDigit(tokens[0].charAt(0))) { // mapping
                    Long[] translation = Arrays.stream(tokens).map(Long::parseLong).toArray(Long[]::new);
                    mappings.get(currentKey).add(translation);
                } else {// key
                    currentKey = tokens[0];
                    System.out.println(currentKey);
                    mappings.put(currentKey, new ArrayList<>());
                }
            }
        }
        return new Almanac(seeds, mappings);
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("input"));
        Almanac almanac = parseInput(contents);
        // part 1
        System.out.println(Arrays.stream(almanac.seeds).map(seed -> performMapping(almanac, seed)).min(Long::compare));
        // part 2 - brute force
        System.out.println(LongStream.range(0, almanac.seeds.length / 2).parallel().map(i -> {
            i *= 2;
            long seed = almanac.seeds[(int) i];
            long range = almanac.seeds[(int) i + 1];
            long min = Long.MAX_VALUE;
            for (long j = seed; j < seed + range; j++) {
                min = Math.min(min, performMapping(almanac, j));
            }
            return min;
        }).min());
    }

    public record Almanac(Long[] seeds, Map<String, List<Long[]>> mappings) {
        static final String[] order = new String[]{"seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"};
    }
}