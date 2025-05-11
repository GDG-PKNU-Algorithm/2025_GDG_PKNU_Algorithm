import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    // 데이터의 변경 + 구간합 -> 세그먼트 트리

    static class SegmentTree {
        long[] arr;
        int pow = 1;


        public SegmentTree(int n, long[] data) {
            while (pow < n) {
                pow *= 2;
            }
            arr = new long[2*pow];
            int startIndex = pow;
            int endIndex = pow + n - 1;

            for(int i = 0; i < n; i++) {
                arr[startIndex+i] = data[i];
            }

            while (endIndex > 1) {
                int parent = endIndex / 2;
                arr[parent] += arr[endIndex];
                endIndex--;
            }
        }

        public long getPrefixSum(long start, long end) {
            long sum = 0;
            int st = (int) (start + pow - 1);
            int en = (int) (end + pow - 1);

            while (st <= en) {
                if (st % 2 == 1) {
                    sum += arr[st];
                }
                if (en % 2 == 0) {
                    sum += arr[en];
                }

                st = (st + 1) / 2;
                en = (en - 1) / 2;
            }

            return sum;
        }

        public void update(long index, long val) {
            int target = (int) (index + pow - 1);
            int parent = target / 2;
            arr[target] = val;

            while (parent > 0){
                arr[parent] = arr[2*parent] + arr[2*parent+1];
                parent /= 2;
            }
        }

        public void printTree() {
            for (int i = 0; i < arr.length; i++) {
                System.out.println("index[" + i + "] : " + arr[i]);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        // N개의 수 -> 부분합
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 수의 개수 -> 트리배열의 크기 2^k >= N 을 만족하는 k의 최솟값 -> 2^k * 2
        int M = Integer.parseInt(st.nextToken()); // 수의 변경 횟수
        int K = Integer.parseInt(st.nextToken()); // 구간 합을 구하는 횟수

        long[] data = new long[N];


        for (int i = 0; i < N; i++) {
            data[i] = Long.parseLong(br.readLine());
        }

        SegmentTree segmentTree = new SegmentTree(N, data);
        //segmentTree.printTree();

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            if(cmd == 1) {
                segmentTree.update(a, b);
                //segmentTree.printTree();
            } else if (cmd == 2) {
                answer.append(segmentTree.getPrefixSum(a, b)).append("\n");
            }
        }

        System.out.println(answer);
    }
}