import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static Planet[] planets;
    static ArrayList<Edge>[] edges;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        planets = new Planet[N];
        edges = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            Planet planet = new Planet(i, x, y, z);
            planets[i] = planet;
        }
        for (int i = 0; i < 3; i++) {
            sort(i);
        }

        int answer = prim();
        System.out.println(answer);
    }

    private static void sort(int num) {
        Arrays.sort(planets, (o1, o2) -> {
            if (num == 0) return Integer.compare(o1.x, o2.x);
            else if (num == 1) return Integer.compare(o1.y, o2.y);
            else return Integer.compare(o1.z, o2.z);
        });

        for (int i = 0; i < N-1; i++) {
            int minCost = getMinCost(planets[i], planets[i + 1]);
            Planet p1 = planets[i];
            Planet p2 = planets[i+1];

            edges[p1.id].add(new Edge(p1.id, p2.id, minCost));
            edges[p2.id].add(new Edge(p2.id, p1.id, minCost));
        }
    }

    private static int prim() {
        Queue<Edge> pq = new PriorityQueue<>();
        pq.addAll(edges[0]);
        boolean[] visited = new boolean[N];
        visited[0] = true;
        int answer = 0;

        while(!pq.isEmpty()){
            Edge poll = pq.poll();
            if(visited[poll.to]) {
               continue;
            }

            visited[poll.to] = true;
            answer += poll.cost;
            for(Edge edge : edges[poll.to]){
                if(!visited[edge.to]) pq.add(edge);
            }
        }

        return answer;
    }

    private static int getMinCost(Planet first, Planet second) {
        int diffX = Math.abs(first.x - second.x);
        int diffY = Math.abs(first.y - second.y);
        int diffZ = Math.abs(first.z - second.z);

        return Math.min(Math.min(diffX, diffY), diffZ);
    }

    private static class Planet {
        int id, x, y, z;

        public Planet(int id, int x, int y, int z) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static class Edge implements Comparable<Edge> {
        int from, to, cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o){
            return Integer.compare(this.cost, o.cost);
        }
    }
}
