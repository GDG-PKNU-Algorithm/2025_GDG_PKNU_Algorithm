import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static List<Boolean> anthillsDeleted = new ArrayList<>(Arrays.asList(false));
    static List<Integer> anthillsPosition = new ArrayList<>(Arrays.asList(0));

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            String cmd = input[0];
            if (cmd.equals("100")) {
                townInit(input);
            } else if (cmd.equals("200")) {
                buildHouse(input);
            } else if (cmd.equals("300")) {
                demolishHouse(input);
            } else if (cmd.equals("400")) {
                reconnoitre(input);
            }
        }

    }

    private static void townInit(String[] input) {
        int num = Integer.parseInt(input[1]);
        for (int i = 1; i <= num; i++) {
            anthillsPosition.add(Integer.parseInt(input[i + 1]));
            anthillsDeleted.add(false);
        }
    }

    private static void buildHouse(String[] input) {
        anthillsPosition.add(Integer.parseInt(input[1]));
        anthillsDeleted.add(false);
    }

    private static void demolishHouse(String[] input) {
        int index = Integer.parseInt(input[1]);
        anthillsDeleted.set(index, true);
    }

    private static void reconnoitre(String[] input) {
        int num = Integer.parseInt(input[1]);

        int left = 0;
        int right = 1_000_000_000;
        int min_time = 0;

        while(left <= right) {
            int mid = (left + right) / 2;
            int needed_num = 0;
            int lastCoverPos = -1_000_000_000;

            for (int i = 1; i < anthillsPosition.size(); i++) {
                if(anthillsDeleted.get(i))
                    continue;

                int cur_position = anthillsPosition.get(i);

                if(cur_position - lastCoverPos > mid) {
                    lastCoverPos = cur_position;
                    needed_num++;
                }
            }

            if(needed_num <= num) {
                min_time = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        System.out.println(min_time);
    }
}
