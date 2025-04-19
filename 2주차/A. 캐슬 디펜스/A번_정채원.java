import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    static int[][] initMap;
    static ArrayList<ArrayList<Integer>> archerIdxList = new ArrayList<>();
    static int n;
    static int m;
    static int d;
    static int maxKill = Integer.MIN_VALUE;
    static int[][] dirs = new int[][]{{0, -1}, {-1, 0}, {0, 1}}; // 순서 중요

    static void placeArcher(int depth, int now, ArrayList<Integer> result){
        if(depth == 3){
            archerIdxList.add(new ArrayList<>(result));
            return;
        }

        for (int i = now; i < m; i++) {
            result.add(i);
            placeArcher(depth + 1, i + 1, result);
            result.remove(result.size() - 1);
        }
    }
    
    static int startGame(ArrayList<Integer> archerIndex){
        int kill = 0;

        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(initMap[i], 0, map[i], 0, m);
        }

        for (int turn = 0; turn < n; turn++) {
            Set<String> targets = new HashSet<>();

            for (int archerCol : archerIndex) {
                Queue<int[]> queue = new ArrayDeque<>();
                boolean[][] visited = new boolean[n][m];

                queue.add(new int[]{n - 1, archerCol, 1});
                visited[n - 1][archerCol] = true;

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    int r = cur[0];
                    int c = cur[1];
                    int dist = cur[2];

                    if (map[r][c] == 1) {
                        targets.add(r + "," + c);
                        break;
                    }

                    if (dist >= d) continue;

                    for (int[] dir : dirs) {
                        int nr = r + dir[0];
                        int nc = c + dir[1];

                        if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc]) {
                            visited[nr][nc] = true;
                            queue.add(new int[]{nr, nc, dist + 1});
                        }
                    }
                }
            }
            // 적 동시에 제거 -> 서로다른 궁수가 하나의 적을 쏠 수 있음
            for (String pos : targets) {
                String[] split = pos.split(",");
                int r = Integer.parseInt(split[0]);
                int c = Integer.parseInt(split[1]);

                if (map[r][c] == 1) {
                    kill++;
                    map[r][c] = 0;
                }
            }

            
            for (int i = n - 1; i > 0; i--) {
                map[i] = Arrays.copyOf(map[i - 1], m);
            }
            Arrays.fill(map[0], 0); 
        }

        return kill;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        initMap = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                initMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        placeArcher(0,0, new ArrayList<>());

        for(ArrayList<Integer> list : archerIdxList){
            maxKill= Math.max(maxKill, startGame(list));
        }

        System.out.println(maxKill);
    }
}
