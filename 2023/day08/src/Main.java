import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static int countReachedEnd(String[] nodes) {
        int count = 0;
        for (String node : nodes) {
            if (node.charAt(node.length() - 1) == 'Z') {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("input"));
        int[] instructions = contents.get(0).chars().map(x -> x == 'L' ? 0 : 1).toArray();
        Map<String, String[]> network = new HashMap<>();
        for (String content : contents.subList(2, contents.size())) {
            String[] mappings = content.split("[\\s|=(),]+");
            System.out.println(Arrays.asList(mappings));
            String node = mappings[0];
            String left = mappings[1];
            String right = mappings[2];
            network.put(node, new String[]{left, right});
        }
        // part one
        long count = 0;
        String node = "AAA";
        while (!node.equals("ZZZ")) {
            //System.out.println("" + instructions[(int)count % instructions.length] + " " + node + " -> " + network.get(node)[instructions[(int)count % instructions.length]]);
            node = network.get(node)[instructions[(int) count % instructions.length]];
            count++;
        }
        System.out.println(count);
        // part two
        // obtain starting nodes
        String[] nodes = network.keySet().stream().filter(x -> x.charAt(x.length() - 1) == 'A').toArray(String[]::new);
        long[] cycleStarts = new long[nodes.length];
        System.out.println(Arrays.asList(nodes));
        count = 0;
        boolean done = false;
        while (!done) { // while not at the end on every point
            done = true;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = network.get(nodes[i])[instructions[(int) count % instructions.length]];
                if (nodes[i].charAt(nodes[i].length() - 1) == 'Z') {
                    // cycle competed
                    if (cycleStarts[i] == 0) {// already did a cycle?
                        cycleStarts[i] = count + 1;
                    }
                }
                if (cycleStarts[i] == 0) {
                    done = false;
                }
            }
            count++;
        }
        // then calculate lcm, this only works because the network is very specific
        System.out.println(Arrays.toString(cycleStarts));
    }
}