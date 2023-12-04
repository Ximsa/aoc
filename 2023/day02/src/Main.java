import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static int cardScore(Map<String,List<String>> card){
        List<String> ownNumbers = card.get("own");
        List<String> winningNumbers = card.get("winning");
        int score = 0;
        for(String winning: winningNumbers) {
            if (!winning.isEmpty() && ownNumbers.contains(winning)) {
                score++;
            }
        }
        return score;
    }

    public static Map<String,List<String>> parseCard(String card)
    {
        String[] splitCard = card.split("\\|");
        List<String> winningNumbers = Arrays.asList(splitCard[0].split("\\s+")); // card and n: get ignored
        List<String> ownNumbers = Arrays.asList(splitCard[1].split("\\s+"));
        return Map.of(
                "winning", winningNumbers,
                "own", ownNumbers);
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("input"));
        /*contents = Arrays.asList("""
                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                """.split("\n"));*/
        int sum = 0;
        ArrayList<Map<String,List<String>>> cards = new ArrayList<>();
        for (String content:contents) {
            Map<String,List<String>> card = parseCard(content);
            cards.add(card);
            sum += (int) (Math.pow(2, cardScore(card)) / 2); // part one
        }
        // part two
        int[] counts = new int[cards.size()];
        Arrays.fill(counts, 1);
        for(int i = 0; i < cards.size(); i++) {
            int score = cardScore(cards.get(i));
            for(int j = 0; j < score; j++){
                counts[i+j+1] += counts[i];
            }
        }
        System.out.println(sum + "\t" + IntStream.of(counts).sum());
    }
}