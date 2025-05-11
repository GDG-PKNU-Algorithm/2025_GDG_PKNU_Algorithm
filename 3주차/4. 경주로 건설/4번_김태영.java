import java.util.*;

class Solution {
    static int[][] board;
    static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    static class Node {
        int y, x, dir, cost;
        Node(int y, int x, int dir, int cost) {
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.cost = cost;
        }
    }

    public int solution(int[][] input) {
        board = input;
        int n = board.length;
        int[][][] cost = new int[n][n][4];

        for (int[][] layer : cost)
            for (int[] row : layer)
                Arrays.fill(row, Integer.MAX_VALUE);

        Queue<Node> queue = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            cost[0][0][i] = 0;
            queue.offer(new Node(0, 0, i, 0));
        }

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = cur.y + dirs[i][0];
                int nx = cur.x + dirs[i][1];

                if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                if (board[ny][nx] == 1) continue;

                int newCost = cur.cost + (cur.dir == i ? 100 : 600);
                if (cost[ny][nx][i] > newCost) {
                    cost[ny][nx][i] = newCost;
                    queue.offer(new Node(ny, nx, i, newCost));
                }
            }
        }

        return Arrays.stream(cost[n - 1][n - 1]).min().getAsInt();
    }
}
