import java.util.*;

public class Solution {
    static List<Node> path = new ArrayList<>();
    static int[][] dirs = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}
    };
    static String[] split;
    static int count = 0;
    static int cx, cy, nx, ny;

    public int solution(String direction) {
        String input = direction;
        split = input.split("");
        cx = 0;
        cy = 0;
        nx = 0;
        ny = 0;
        for (int i = 0; i < split.length; i++) {
            addNode(cx, cy, split[i]);
            if(nx >= -5 && ny >= -5 && nx <= 5 && ny <= 5){
                cx = nx;
                cy = ny;
            }
        }

        return count;
    }

    static void addNode(int cx, int cy, String type) {
        if(type. equals("U")){
            nx = cx + dirs[0][0];
            ny = cy + dirs[0][1];

            Node node1 = new Node(cx, cy, nx, ny);
            if(!path.contains(node1) && nx >= -5 && ny >= -5 && nx <= 5 && ny <= 5) {
                path.add(node1);
                Node node2 = new Node(nx, ny, cx, cy);
                path.add(node2);
                count++;
            }
        }

        if(type. equals("R")){
            nx = cx + dirs[1][0];
            ny = cy + dirs[1][1];

            Node node1 = new Node(cx, cy, nx, ny);
            if(!path.contains(node1) && nx >= -5 && ny >= -5 && nx <= 5 && ny <= 5) {
                path.add(node1);
                Node node2 = new Node(nx, ny, cx, cy);
                path.add(node2);
                count++;
            }
        }

        if(type. equals("D")){
            nx = cx + dirs[2][0];
            ny= cy + dirs[2][1];

            Node node1 = new Node(cx, cy, nx, ny);
            if(!path.contains(node1) && nx >= -5 && ny >= -5 && nx <= 5 && ny <= 5) {
                path.add(node1);
                Node node2 = new Node(nx, ny, cx, cy);
                path.add(node2);
                count++;
            }
        }

        if(type. equals("L")){
            nx = cx + dirs[3][0];
            ny= cy + dirs[3][1];

            Node node1 = new Node(cx, cy, nx, ny);
            if(!path.contains(node1) && nx >= -5 && ny >= -5 && nx <= 5 && ny <= 5) {
                path.add(node1);
                Node node2 = new Node(nx, ny, cx, cy);
                path.add(node2);
                count++;
            }
        }
    }

    static class Node {
        int prev_x, prev_y, next_x, next_y;

        public Node(int prev_x, int prev_y, int next_x, int next_y) {
            this.prev_x = prev_x;
            this.prev_y = prev_y;
            this.next_x = next_x;
            this.next_y = next_y;
        }

        @Override
        public boolean equals(Object object) {
            Node node = (Node) object;
            return prev_x == node.prev_x && prev_y == node.prev_y && next_x == node.next_x && next_y == node.next_y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(prev_x, prev_y, next_x, next_y);
        }
    }
}
