import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int[][][] price;
    static int size;
    static final int INF = 0x3f3f3f3f;
    static int minPrice = INF;

    static void bfs(int[][] board, int r, int c) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r, c, -1});


        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int row = cur[0];
            int col = cur[1];
            int dir = cur[2];

            for (int i = 0; i < 4; i++) {
                int newRow = row + dr[i];
                int newCol = col + dc[i];

                if(newRow >= 0 && newRow < size && newCol >= 0 && newCol < size
                        && board[newRow][newCol] == 0) {
                    int newPrice;

                    if (dir == -1 || dir == i) {
                        if (dir == -1) {
                            newPrice = price[row][col][i] + 100; 
                        } else {
                            newPrice = price[row][col][dir] + 100; 
                        }
                    } else {
                        newPrice = price[row][col][dir] + 600; 
                    }

                    if (price[newRow][newCol][i] > newPrice) {
                        price[newRow][newCol][i] = newPrice;
                        queue.add(new int[]{newRow, newCol, i});
                    }
                }
            }

        }

    }

    public int solution(int[][] board) {
        size = board.length;
        price = new int[size][size][4]; // 0:상 / 1:하 / 2:좌 / 3:우

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                price[i][j][0] = INF;
                price[i][j][1] = INF;
                price[i][j][2] = INF;
                price[i][j][3] = INF;
            }
        }

        price[0][0][0] = 0;
        price[0][0][1] = 0;
        price[0][0][2] = 0;
        price[0][0][3] = 0;

        bfs(board, 0, 0);
        int answer = INF;
        for (int i = 0; i < 4; i++) {
            answer = Math.min(answer, price[size - 1][size - 1][i]);
        }
        return answer;
    }
}




// 아래는 오답코드 : 채점 92점
// 백트래킹 + 직전방향 저장
// 다음방향이 직전방향보다의 비용보다 작으면 가지치기를 했는데 이렇게하니 반례가 있음
// 해결법 -> 방문배열에 4방향에대한 price를 다 저장 + bfs

class Solution {
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static boolean[][] visited;
    static int[][][] price;
    static int size;
    static int minPrice = Integer.MAX_VALUE;

    static void dfs(int[][] board, int r, int c) {
        int[] priceAndDir = price[r][c];
        if(r == size -1 && c == size -1) {
            minPrice = Math.min(minPrice, priceAndDir[0]);
            return;
        }

        for(int[] dir : dirs) {
            int nextRow = r + dir[0];
            int nextCol = c + dir[1];

            if(nextRow >= 0 && nextRow < size && nextCol >= 0 && nextCol < size
                    && !visited[nextRow][nextCol]
                    && board[nextRow][nextCol] == 0) {
                int nextPrice = priceAndDir[0];
                if (r == 0 && c == 0) { // 처음 진입
                    nextPrice += 100;
                } else if (priceAndDir[1] != dir[0] || priceAndDir[2] != dir[1]) {
                    nextPrice += 600;
                } else {
                    nextPrice += 100;
                }

                if(price[nextRow][nextCol][0] < nextPrice) {
                    continue;
                }

                price[nextRow][nextCol][0] = nextPrice;
                price[nextRow][nextCol][1] = dir[0];
                price[nextRow][nextCol][2] = dir[1];
                visited[nextRow][nextCol] = true;
                dfs(board, nextRow, nextCol);
                visited[nextRow][nextCol] = false;
            }
        }
    }
    
    
    public int solution(int[][] board) {
        size = board.length;
        visited = new boolean[size][size];
        price = new int[size][size][3]; // 해당칸을 어떻게 왔는지 + 그렇게 왔을때의 비용
        // visited[next][next][] -> visited[cur][cur][] 의 정보를 확인하고 방향이 같다면 + 100, 아니라면 + 600
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                price[i][j][0] = Integer.MAX_VALUE;
                price[i][j][1] = -1;
                price[i][j][2] = -1;
            }
        }
        price[0][0][0] = 0;

        dfs(board, 0, 0);
        if(minPrice == Integer.MAX_VALUE){
            return 0;
        } else {
            return minPrice;
        }
    }
}