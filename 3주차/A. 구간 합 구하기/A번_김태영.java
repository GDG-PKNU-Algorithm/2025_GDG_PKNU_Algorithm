import java.util.*;

public class Main {
    static int N, M, K;
    static long[] arr, tree;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        arr = new long[N+1];
        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextLong();
        }

        int k = (int)Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int tree_size = (int)Math.pow(2, k);
        tree = new long[tree_size+1];
        init(1, N, 1);

        for (int i = 0; i < M + K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            long c = sc.nextLong();

            if(a == 1){
                long diff = c - arr[b];
                arr[b] = c;
                update(1, N, 1, b, diff);
            }
            if(a == 2){
                long rst = sum(1, N, 1, b, (int) c);
                System.out.println(rst);
            }
        }
    }

    static long init(int start, int end, int node) {
        if(start == end) {
            tree[node] = arr[start];
            return tree[node];
        }

        int mid = (start + end) / 2;
        tree[node] = init(start, mid, node*2) + init(mid+1, end, node*2 +1);
        return tree[node];
    }

    static void update(int start, int end, int node, int index, long diff){
        if(index < start || index > end) {
            return;
        }

        tree[node] += diff;

        if (start == end) {
            return;
        }

        int mid = (start + end) / 2;
        update(start, mid, node * 2, index, diff);
        update(mid+1, end, node * 2 + 1, index, diff);
    }

    static long sum(int start, int end, int node, int left, int right) {
        if (left > end || right < start){
            return 0;
        }

        if(left <= start && right >= end){
            return tree[node];
        }

        int mid = (start + end) / 2;
        return sum(start, mid, node * 2, left, right) + sum(mid + 1, end, node * 2 + 1, left, right);
    }
}
