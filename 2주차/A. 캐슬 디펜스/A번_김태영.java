import java.io.*;
import java.util.*;

public class Main {
    static int N, M, D, result = 0;
    static int[][] monster_map, copy_map;
    static int[] archer = new int[3];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        monster_map = new int[N][M];
        copy_map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                monster_map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        locateArcher(0,0);

        System.out.println(result);
        br.close();
    }

    // 궁수 배치
    private static void locateArcher(int depth, int index){
        if(depth == 3){
            deepCopy();
            int killCount = attack();
            result = Math.max(result, killCount);
            return;
        }
        for (int i = index; i < M; i++) {
            archer[depth] = i;
            locateArcher(depth+1, i+1);
        }
    }

    // 공격
    private static int attack(){
        int sum = 0;
        PriorityQueue<Monster> pq = new PriorityQueue<>();
        Set<Monster> set = new HashSet<>();
        for (int p = 0; p < N; p++) {
            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < M; k++) {
                        if (copy_map[j][k] == 1 && (Math.abs(N-j) + Math.abs(archer[i] - k)) <= D){
                            pq.add(new Monster(k, j, Math.abs(N - j) + Math.abs(archer[i] - k)));
                        }
                    }
                }

                if (!pq.isEmpty()) {
                    Monster target = pq.poll();
                    set.add(target);
                }
                pq.clear();
            }

            for (Monster target : set) {
                copy_map[target.y][target.x] = 0;
                sum++;
            }
            set.clear();
            downMap();
        }
        return sum;
    }

    // Deep Copy
    private static void deepCopy(){
        for (int i = 0; i <N; i++) {
            copy_map[i] = Arrays.copyOf(monster_map[i], M);
        }
    }

    // 몬스터 드랍
    private static void downMap(){
        for (int i = N-2; i >= 0; i--) {
            copy_map[i+1] = copy_map[i];
        }
        copy_map[0] = new int[M];
    }

    static class Monster implements Comparable<Monster>{
        int x, y, dist;

        @Override
        public boolean equals(Object object) {
            Monster monster = (Monster) object;
            return x == monster.x && y == monster.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Monster(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
        @Override
        public int compareTo(Monster o) {
            return this.dist == o.dist ? this.x - o.x : this.dist - o.dist;
        }
    }
}