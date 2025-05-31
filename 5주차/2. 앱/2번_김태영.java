import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static int[] activate;
    static int[] cost;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        activate = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            activate[i] = Integer.parseInt(st.nextToken());
        }

        cost = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[10001][N + 1];

        for (int i = 0; i <= 10000; i++) {
            for (int j = 1; j <= N; j++) {
                if (i < cost[j]) {
                    dp[i][j] = dp[i][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - cost[j]][j - 1] + activate[j]);
                }
            }
        }

        for (int i = 0; i <= 10000; i++) {
            if (dp[i][N] >= M) {
                System.out.println(i);
                break;
            }
        }
    }
}
