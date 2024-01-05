import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static long getCombinations(int recordPosition, char[] record, int groupPosition, int[] groups, boolean groupActive) {
        // sanity checks
        if(recordPosition == record.length){
            return Arrays.stream(groups).allMatch(x->x==0)? 1 : 0;
        }
        // recursion
        switch(record[recordPosition]){
            case '.':
                return getCombinations(recordPosition+1, record, groupPosition, groups, false);
            case '#': // start of group
                if(!groupActive){
                    if(groupPosition >= 0 && groups[groupPosition] != 0){
                        return 0;
                    }
                    groupPosition++;
                }
                if(groupPosition >= groups.length) {
                    return 0; // fail condition: group cant be started
                }
                groups[groupPosition]--;
                if(groups[groupPosition] < 0){
                    return 0;
                }
                return getCombinations(recordPosition+1, record, groupPosition, groups, true);
            case '?': // branch
                char[] recordFork = record.clone();
                int[] groupsFork = groups.clone();
                recordFork[recordPosition] = '#';
                record[recordPosition] = '.';
                // only branch # when groups are left
                return getCombinations(recordPosition, record, groupPosition, groups, groupActive)
                        + getCombinations(recordPosition, recordFork, groupPosition, groupsFork, groupActive);
            default:
                return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> contents = Files.readAllLines(Paths.get("example"));
        long sum = 0;
        int i = 0;
        for (String content : contents) {
            String[] info = content.split("\\s+");
            char[] record = (String.join("?",info[0],info[0],info[0],info[0],info[0])).toCharArray();
            int[] groups = Arrays.stream(String.join(",",info[1],info[1],info[1],info[1],info[1]).split(",")).mapToInt(Integer::parseInt).toArray();
            System.out.printf("(%d/%d):\t%d\t",i+1, contents.size(), CharBuffer.wrap(record).chars().filter(x-> x=='?').count());
            long combinations = getCombinations(0, record, -1, groups, false);
            System.out.println(combinations);
            sum += combinations;
            i++;
        }
        System.out.println(sum);
    }
}