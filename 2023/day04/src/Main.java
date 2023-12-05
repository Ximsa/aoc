import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {

    public static int cardScore(Map<String, List<String>> card) {
        List<String> ownNumbers = card.get("own");
        List<String> winningNumbers = card.get("winning");
        int score = 0;
        for (String winning : winningNumbers) {
            if (!winning.isEmpty() && ownNumbers.contains(winning)) {
                score++;
            }
        }
        return score;
    }

    public static Map<String, List<String>> parseCard(String card) {
        String[] splitCard = card.split("\\|");
        List<String> winningNumbers = Arrays.asList(splitCard[0].split("\\s+")); // card and n: get ignored
        List<String> ownNumbers = Arrays.asList(splitCard[1].split("\\s+"));
        return Map.of("winning", winningNumbers, "own", ownNumbers);
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("input"));
        int sum = 0;
        ArrayList<Map<String, List<String>>> cards = new ArrayList<>();
        for (String content : contents) {
            Map<String, List<String>> card = parseCard(content);
            cards.add(card);
            sum += (int) (Math.pow(2, cardScore(card)) / 2); // part one
        }
        // part two
        int[] counts = new int[cards.size()];
        Arrays.fill(counts, 1);
        for (int i = 0; i < cards.size(); i++) {
            int score = cardScore(cards.get(i));
            for (int j = 0; j < score; j++) {
                counts[i + j + 1] += counts[i];
            }
        }
        System.out.println(sum + "\t" + IntStream.of(counts).sum());
    }
}