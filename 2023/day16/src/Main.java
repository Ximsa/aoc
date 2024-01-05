import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {

    public static int convertDirections(int dx, int dy){
        return dy == 0? dx + 1 : dy + 2;
    }
    public static void propagateBeam(char[][] reflectors, int[][][] beamPositions, int x, int y, int dx, int dy) {
        if (x < 0 || x >= reflectors[0].length || y < 0 || y >= reflectors.length) {
            return;
        } else {
            int direction = convertDirections(dy,dx);
            if (beamPositions[y][x][direction] == 0) {
                beamPositions[y][x][direction]++;
                switch (reflectors[y][x]) {
                    case '/':
                        propagateBeam(reflectors, beamPositions, x - dy, y - dx, -dy, -dx);
                        break;
                    case '\\':
                        propagateBeam(reflectors, beamPositions, x + dy, y + dx, dy, dx);
                        break;
                    case '|':
                        if (dy == 0) {
                            propagateBeam(reflectors, beamPositions, x, y + 1, 0, 1);
                            propagateBeam(reflectors, beamPositions, x, y - 1, 0, -1);
                        } else {
                            propagateBeam(reflectors, beamPositions, x + dx, y + dy, dx, dy);
                        }
                        break;
                    case '-':
                        if (dx == 0) {
                            propagateBeam(reflectors, beamPositions, x + 1, y, 1, 0);
                            propagateBeam(reflectors, beamPositions, x - 1, y, -1, 0);
                        } else {
                            propagateBeam(reflectors, beamPositions, x + dx, y + dy, dx, dy);
                        }
                        break;
                    case '.':
                    default:
                        propagateBeam(reflectors, beamPositions, x + dx, y + dy, dx, dy);
                        break;
                }
            }
        }
    }

    public static int summarizeBeams(int [][][] beamPositions){
        int sum = 0;
        for (int[][] line : beamPositions) {
            for(int[] beam: line){
                sum += Arrays.stream(beam).max().getAsInt();
            }
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        char[][] reflectors = Files.readAllLines(Paths.get("input"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        int maxBeams = 0;
        for(int y = 0; y < reflectors.length; y++){
            int[][][] beamPositions = new int[reflectors.length][reflectors[0].length][4];
            propagateBeam(reflectors, beamPositions, 0, y, 1, 0);
            maxBeams = Math.max(maxBeams, summarizeBeams(beamPositions));
            beamPositions = new int[reflectors.length][reflectors[0].length][4];
            propagateBeam(reflectors, beamPositions, reflectors[0].length-1 , y, -1, 0);
            maxBeams = Math.max(maxBeams, summarizeBeams(beamPositions));
        }
        for(int x = 0; x < reflectors[0].length; x++){
            int[][][] beamPositions = new int[reflectors.length][reflectors[0].length][4];
            propagateBeam(reflectors, beamPositions, x, 0, 0, 1);
            maxBeams = Math.max(maxBeams, summarizeBeams(beamPositions));
            beamPositions = new int[reflectors.length][reflectors[0].length][4];
            propagateBeam(reflectors, beamPositions, x , reflectors.length-1, 0, 1);
            maxBeams = Math.max(maxBeams, summarizeBeams(beamPositions));
        }
        int[][][] beamPositions = new int[reflectors.length][reflectors[0].length][4];
        propagateBeam(reflectors, beamPositions, 0, 0, 1, 0);

        System.out.println(maxBeams);
    }
}