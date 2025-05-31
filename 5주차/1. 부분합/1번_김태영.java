import java.util.*;
import java.io.*;

public class Main {
    static int N, S;
    static int[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        arr = new int[N];
        arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int left = 0;
        int right = 0;
        int minLen = Integer.MAX_VALUE;
        int sum = arr[left];
        while(left <= right) {
            if(sum >= S){
                minLen = Math.min(right-left+1, minLen);
                sum -= arr[left];
                left++;
            }

            if(sum < S){
                if(right == N-1)
                    break;
                right++;
                sum += arr[right];
            }
        }

        if(minLen == Integer.MAX_VALUE)
            System.out.println(0);
        else
            System.out.println(minLen);
    }
}
