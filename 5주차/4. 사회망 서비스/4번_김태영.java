import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static int[][] dp;
    static boolean[] visited;
    static ArrayList<Integer>[] adjacent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        adjacent = new ArrayList[N+1];
        dp = new int[N + 1][2];
        visited = new boolean[N + 1];

        for (int i = 0; i <= N; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int i = 0; i < N-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            adjacent[parent].add(child);
            adjacent[child].add(parent);
        }

        dfs(1);
        System.out.println(Math.min(dp[1][0], dp[1][1]));
    }

    private static void dfs(int node) {
        dp[node][0] = 0;
        dp[node][1] = 1;
        visited[node] = true;

        for(Integer child : adjacent[node]){
            if(!visited[child]) {
                dfs(child);
                dp[node][0] += dp[child][1];
                dp[node][1] += Math.min(dp[child][0], dp[child][1]);
            }
        }
    }
}
