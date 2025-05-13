import java.io.*;
import java.util.*;

public class Main {
    static int N, Q;
    static int[][] map;
    static int num = 1;
    static int[][] dirs = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // 상, 우, 하, 좌
    static Queue<Microbe_group> microbe_groups;
    static int[] microbe_size;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        Q = Integer.parseInt(input[1]);
        map = new int[N + 1][N + 1];
        microbe_size = new int[Q + 2];
        microbe_groups = new PriorityQueue<>();

        for (int i = 0; i < Q; i++) {
            input = br.readLine().split(" ");
            int lx = Integer.parseInt(input[0]);
            int ly = Integer.parseInt(input[1]);
            int rx = Integer.parseInt(input[2]);
            int ry = Integer.parseInt(input[3]);

            input(lx, ly, rx, ry);
            move();
            int answer = cal();
            bw.write(answer+"\n");
        }
        bw.flush();
        bw.close();
    }

    static void input(int lx, int ly, int rx, int ry) {
        int[] microbe_num = new int[Q + 2];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i >= ly + 1 && j >= lx + 1 && i <= ry && j <= rx) {
                    map[i][j] = num;
                }
                if (map[i][j] > 0) microbe_num[map[i][j]]++;
            }
        }

        boolean[][] visited = new boolean[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j] != 0 && !visited[i][j]) {
                    Queue<Microbe> queue = new LinkedList<>();
                    List<Microbe> group = new ArrayList<>();
                    queue.add(new Microbe(i, j));
                    group.add(new Microbe(i, j));
                    visited[i][j] = true;

                    while (!queue.isEmpty()) {
                        Microbe cur = queue.poll();
                        for (int k = 0; k < 4; k++) {
                            int ny = cur.y + dirs[k][1];
                            int nx = cur.x + dirs[k][0];
                            if (nx >= 1 && ny >= 1 && nx <= N && ny <= N && !visited[ny][nx] && map[ny][nx] == map[i][j]) {
                                visited[ny][nx] = true;
                                Microbe next = new Microbe(ny, nx);
                                queue.add(next);
                                group.add(next);
                            }
                        }
                    }

                    if (group.size() != microbe_num[map[i][j]]) {
                        for (Microbe m : group) {
                            map[m.y][m.x] = 0;
                        }
                    } else {
                        microbe_groups.add(new Microbe_group(group, map[i][j]));
                    }
                }
            }
        }
        num++;
    }

    static void move() {
        boolean[][] visited = new boolean[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            Arrays.fill(map[i], 0);
        }

        while (!microbe_groups.isEmpty()) {
            Microbe_group group = microbe_groups.poll();

            boolean placed = false;
            // x 좌표가 작은 게 우선이므로 이번에는 X 먼저
            for (int x = 1; x <= N && !placed; x++) {
                for (int y = 1; y <= N && !placed; y++) {
                    if (movable(y, x, group, visited)) {
                        Microbe ref = group.members.get(0);
                        for (Microbe m : group.members) {
                            int ny = y + (m.y - ref.y);
                            int nx = x + (m.x - ref.x);
                            map[ny][nx] = group.type;
                            visited[ny][nx] = true;
                        }
                        microbe_size[group.type] = group.size;
                        placed = true;
                    }
                }
            }
        }
    }

    static boolean movable(int y, int x, Microbe_group group, boolean[][] visited) {
        Microbe ref = group.members.get(0);
        for (Microbe m : group.members) {
            int ny = y + (m.y - ref.y);
            int nx = x + (m.x - ref.x);
            if (ny < 1 || ny > N || nx < 1 || nx > N || visited[ny][nx]) {
                return false;
            }
        }
        return true;
    }

    static int cal() {
        boolean[][] visited = new boolean[N + 1][N + 1];
        Set<String> set = new HashSet<>();
        int result = 0;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j] != 0 && !visited[i][j]) {
                    Queue<Microbe> queue = new LinkedList<>();
                    queue.add(new Microbe(i, j));
                    visited[i][j] = true;

                    while (!queue.isEmpty()) {
                        Microbe cur = queue.poll();
                        for (int k = 0; k < 4; k++) {
                            int ny = cur.y + dirs[k][1];
                            int nx = cur.x + dirs[k][0];
                            if (nx >= 1 && ny >= 1 && nx <= N && ny <= N) {
                                if (!visited[ny][nx] && map[ny][nx] == map[i][j]) {
                                    visited[ny][nx] = true;
                                    queue.add(new Microbe(ny, nx));
                                } else if (map[ny][nx] != 0 && map[ny][nx] != map[i][j]) {
                                    int a = map[i][j];
                                    int b = map[ny][nx];
                                    String key = a < b ? a + "," + b : b + "," + a;
                                    set.add(key);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (String s : set) {
            String[] pair = s.split(",");
            int a = Integer.parseInt(pair[0]);
            int b = Integer.parseInt(pair[1]);
            result += microbe_size[a] * microbe_size[b];
        }

        return result;
    }

    static class Microbe {
        int y, x;
        public Microbe(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Microbe_group implements Comparable<Microbe_group> {
        List<Microbe> members;
        int type, size;

        public Microbe_group(List<Microbe> members, int type) {
            this.members = members;
            this.type = type;
            this.size = members.size();
        }

        @Override
        public int compareTo(Microbe_group o) {
            if (this.size == o.size) return this.type - o.type;
            return o.size - this.size;
        }
    }
}
