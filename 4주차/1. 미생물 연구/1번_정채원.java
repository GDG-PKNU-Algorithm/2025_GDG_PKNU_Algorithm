import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    static int[][] dirs = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    
    /**
     * <------------------ 풀이 후 ------------------>
     *  저번주 문제 중 좌표를 문자열로 해시맵에다 기록하는 방식이 생각나서 똑같이 풀어봤는데
     * 정렬등을 할때 매번 문자를 int배열로 다시 바꾼다던지하는 비용 발생
     * 좌표들의 갯수가 많지 않아 굳이 해시로 검색 할 필요도 없을듯
     * 직관적이긴 한데 생각보다 복잡... 다음엔 클래스로 빼는게 더 나을꺼 같음...
     * 덕분에 스트림, 함수형 Map 연습 많이함
     */


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //StringBuilder result = new StringBuilder();

        int N = Integer.parseInt(st.nextToken()); // 배양용기 크기
        // N 이 최대 15
        int Q = Integer.parseInt(st.nextToken()); // 실험횟수
        // Q 가 최대 50

        // case 별로  bfs 완탐해도 시간초과는 없을 듯

        int[][] board = new int[N][N];
        HashMap<Integer, HashSet<String>> microbes = new HashMap<>();
        // 크기는 HashSet 사이즈로 찾으면 됨
        //HashMap<Integer, Integer> microbesSize = new HashMap<>();

        // 미생물은 사각형으로 (r1, c1) (r2, c2)로 주어짐
        for (int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());
            int microbeType = q + 1;
            // 1. 투입
            // << 투입되는 미생물을 저장할 자료구조 >>
            // 미생물 무리의 크기, 미생물 무리의 고유번호(투입 순서, q),
            // 배열은 0으로 초기화 -> 미생물 인덱스는 1부터
            // 현재 배양요기의 상태를 표기할 board 2차원 3차원 4차원...??
            insertMicrobe(board, microbeType, microbes, r1, c1, r2, c2);
//            System.out.println("insert : " + microbeType);
//            printBoard(board);

            // 겹치는 영역은 나중에 투입 된 녀석으로 대체
            // 겹치는 영역 판별은 어떻게??
            // 겹칠라면 우측하단 (ra2, ca2) 의 각 값들이 무조건 (rb1, cb1)보다 크다
            // 그 크기는 ?? -> (ra2 - rb1 + 1) * (ca2 - cb1 + 1)
            checkMicrobeArea(board, microbes);
//            System.out.println("check : " + microbeType);
//            printBoard(board);


            // 영역이 나뉘면 나뉜 지역 삭제 -> 모든 영역 bfs -> 중복 영역일시 해당 영역은 삭제 -> 중복영역 판별은 어떻게?? (해시맵등을 쓰고 val 값에다 bfs 시작위치를 작성)
            // 시작 위치를 받아서 삭제하는 함수 구현


            // 2. 배양용기 이동
            // 크기가 가장 큰 무리를 이동 -> 미생물 무리의 정보를 저장할 자료구조가 필요.. 영역을 String으로 다 저장할까... < Q - "0,0" , "0,1" , ...> : HashMap<Integer, ArrayList<String>> - > get(q).size()
            // 새로 이동할 board를 완탐하면서 미생물 무리의 val 값을 탐색하면서 boar에 넣을 수 있는지 확인 만약 끝까지 갔는데도 못했다면 삭제
            // 보드를 완탐하는 기준은 r과 y가 작은 순
            // 그런 위치가 2개 이상일때는 어떻게하지..? cur의 r,c를 두고 무조건 끝까지 가봐야하나...
            board = moveMicrobe(board, microbes);
//            System.out.println("move : " + microbeType);
//            printBoard(board);


            // 3. 결과 기록
            // 인접한 무리쌍끼리의 곱
            // bfs 해서 인접한 type 쌍을 기록
            // 해당 type들의 size로 곱을 하면 될 듯
            calculateResult(board, microbes);

        }
//        printBoard(board);
//        printMap(microbes);


    }

    static void insertMicrobe(int[][] board, int microbeType, HashMap<Integer, HashSet<String>> microbes, int r1, int c1, int r2, int c2) {
        for (int i = r1; i < r2; i++) {
            for (int j = c1; j < c2; j++) {
                String coordinate = toStringCoordinate(i, j);

                if (board[i][j] != 0) {
                    int preType = board[i][j];

                    microbes.computeIfPresent(preType, (k,set) -> {
                        set.remove(coordinate);
                        return set.isEmpty() ? null : set;
                    });
                }
                board[i][j] = microbeType;
                microbes.computeIfAbsent(microbeType, k -> new HashSet<>()).add(coordinate);
            }
        }
    }

    private static void checkMicrobeArea(int[][] board, HashMap<Integer, HashSet<String>> microbes) {
        boolean[][] visited = new boolean[board.length][board.length];

        HashMap<Integer, ArrayList<int[]>> startPoint = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                // 미생물도 있고 방문한적도 x
                if(board[i][j] != 0 && !visited[i][j]) {
                    int microbeType = board[i][j];
                    startPoint.computeIfAbsent(microbeType, k -> new ArrayList<>()).add(new int[]{i, j});

                    Queue<int[]> queue = new ArrayDeque<>();
                    queue.add(new int[]{i, j});
                    visited[i][j] = true;

                    while(!queue.isEmpty()) {
                        int[] cur = queue.poll();
                        int row = cur[0];
                        int col = cur[1];

                        for(int[] dir : dirs) {
                            int newRow = row + dir[0];
                            int newCol = col + dir[1];

                            if(newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board.length && !visited[newRow][newCol] && board[newRow][newCol] == microbeType) {
                                queue.add(new int[]{newRow, newCol});
                                visited[newRow][newCol] = true;
                            }
                        }
                    }

                }

            }
        }

        for(Map.Entry<Integer, ArrayList<int[]>> entry : startPoint.entrySet()) {
            int microbeType = entry.getKey();
            ArrayList<int[]> points = entry.getValue();

            if(points.size() > 1) { // 2부분이상 나눠졌다면
                for(int[] point : points) {
                    deleteMicrobe(board, microbeType, point);
                }
                microbes.remove(microbeType); // 해당 미생물 삭제
            }
        }


    }

    static void deleteMicrobe(int[][] board, int microbeType, int[] startPoint) {
        Queue<int[]> queue = new ArrayDeque<>();

        queue.add(new int[]{startPoint[0], startPoint[1]});
        board[startPoint[0]][startPoint[1]] = 0;


        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int row = cur[0];
            int col = cur[1];

            for(int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if(newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board.length && board[newRow][newCol] == microbeType) {
                    queue.add(new int[]{newRow, newCol});
                    board[newRow][newCol] = 0;
                }
            }
        }

    }

    public static int[][] moveMicrobe(int[][] board, HashMap<Integer, HashSet<String>> microbes) {
        // 크기가 가장크고 같을 경우 입력순서가 빠른 microbeType 부터 이동시키기위한 큐 생성
        Queue<Integer> microbeQueue = microbes.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<Integer, HashSet<String>> e) -> e.getValue().size()).reversed()
                .thenComparingInt(Map.Entry::getKey))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayDeque::new));

        int[][] newBoard = new int[board.length][board.length];

        while(!microbeQueue.isEmpty()) {
            int microbeType = microbeQueue.poll();
            //if(!microbes.containsKey(microbeType))continue;

            boolean isMoved = false;

            //System.out.println("debug : " + microbeType); //debug

            // set 중 좌하단(배열상 좌상단) 좌표 구하기
            ArrayList<String> sortedCoordinates = sortingCoordinatesSet(microbes.get(microbeType));
            int[] basePoint = toIntCoordinate(sortedCoordinates.get(0));

            outer:
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    boolean isValid = true;
                    // 매 루프에서는 아래 보드로 비교 -> 입력후 안되는 경우 그냥 버리면 됨
                    int[][] tempBoard = cloneBoard(newBoard);

                    if(tempBoard[i][j] != 0) continue; // 이미 미생물이 있다면 어짜피 그칸에서 시작 불가

                    // 상대적 차이를 구하고 평행이동
                    int diffRow = basePoint[0] - i;
                    int diffCol = basePoint[1] - j;

                    ArrayList<int[]> moved = sortedCoordinates.stream()
                            .map((String str) -> toIntCoordinate(str))
                            .map(o -> {
                                int row = o[0] - diffRow;
                                int col = o[1] - diffCol;
                                return new int[]{row, col};
                            })
                            .collect(Collectors.toCollection(ArrayList::new));

                    for (int[] m : moved) {
                        int row = m[0];
                        int col = m[1];

                        if(row >= 0 && row < board.length && col >= 0 && col < board.length && tempBoard[row][col] == 0) {
                            tempBoard[row][col] = microbeType;
                        } else {
                            isValid = false;
                            break;
                        }
                    }

                    // moved를 모두 순회 했다면
                    if(isValid) {
                        // newBoard에다가 temp 복사
                        newBoard = tempBoard;
                        // microbes 의 좌표정보 수정
                        microbes.put(microbeType, moved.stream().map((int[] cor) -> toStringCoordinate(cor)).collect(Collectors.toCollection(HashSet::new)));

                        isMoved = true;
                        break outer;
                    }
                }
            }

            if(!isMoved){
                microbes.remove(microbeType);
            }

        }

        return newBoard;
    }

    static void calculateResult(int[][] board, HashMap<Integer, HashSet<String>> microbes) {
        HashSet<String> adjMicrobes = new HashSet<>();
        int result = 0;
        for(Map.Entry<Integer, HashSet<String>> entry : microbes.entrySet()) {
            int microbeType = entry.getKey();
            int[] startPoint = toIntCoordinate(new ArrayList<>(entry.getValue()).get(0));
            boolean[][] visited = new boolean[board.length][board.length];

            Queue<int[]> queue = new ArrayDeque<>();
            queue.add(startPoint);
            visited[startPoint[0]][startPoint[1]] = true;

            while(!queue.isEmpty()) {
                int[] cur = queue.poll();
                int row = cur[0];
                int col = cur[1];

                for(int[] dir : dirs) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];

                    if(newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board.length && board[newRow][newCol] != 0 && !visited[newRow][newCol]){
                        if(board[newRow][newCol] == microbeType) {
                            queue.add(new int[]{newRow, newCol});
                            visited[newRow][newCol] = true;
                        } else {
                            int nearMicrobeType = board[newRow][newCol];
                            int a = microbeType;
                            int b = nearMicrobeType;

                            if(a > b){
                                int temp = a;
                                a = b;
                                b = temp;
                            }

                            adjMicrobes.add(a+","+b);
                        }
                    }
                }
            }
        }


        if(!adjMicrobes.isEmpty()) {
            for(String adjMicrobe : adjMicrobes) {
                int[] adj = toIntCoordinate(adjMicrobe);
                result += microbes.get(adj[0]).size() * microbes.get(adj[1]).size();
            }
        }
        System.out.println(result);
        //debug
//        for(String adjMicrobe : adjMicrobes) {
//            System.out.print(adjMicrobe + " ");
//        }
//        System.out.println();

    }



    // 유틸 함수
    static String toStringCoordinate(int r, int c) {
        return r+","+c;
    }

    static String toStringCoordinate(int[] cor) {
        return cor[0]+","+cor[1];
    }

    static int[][] cloneBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    static ArrayList<String> sortingCoordinatesSet(HashSet<String> coordinates) {
        ArrayList<int[]> sortedCoordinates = coordinates.stream()
                .map(cor -> toIntCoordinate(cor))
                .collect(Collectors.toCollection(ArrayList::new));

        return sortedCoordinates.stream()
                .sorted(Comparator.comparing((int[] i) -> i[0])
                        .thenComparing(i -> i[1]))
                .map(i -> toStringCoordinate(i))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    static int[] toIntCoordinate(String coordinate) {
        int[] result = new int[2];
        String[] split = coordinate.split(",");
        result[0] = Integer.parseInt(split[0]);
        result[1] = Integer.parseInt(split[1]);
        return result;
    }

    static void printMap(HashMap<Integer, HashSet<String>> microbes) {
        for(Map.Entry<Integer, HashSet<String>> microbe : microbes.entrySet()){
            int k = microbe.getKey();
            HashSet<String> set = microbe.getValue();
            System.out.print(k + " : ");
            for (String coordi : set) {
                System.out.print(coordi+ " ");
            }
            System.out.println();
        }
    }

    static void printBoard(int[][] board) {
        int size = board.length;
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                result.append(board[i][j]).append(" ");
            }
            result.append("\n");
        }
        System.out.println(result);
    }




}
