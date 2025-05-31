import java.util.*;

public class Main {
    static Map<String, String> parent = new HashMap<>();
    static Map<String, Integer> size = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            parent.clear();
            size.clear();

            int N = sc.nextInt();

            for (int i = 0; i < N; i++) {
                String a = sc.next();
                String b = sc.next();

                parent.putIfAbsent(a, a);
                parent.putIfAbsent(b, b);
                size.putIfAbsent(a, 1);
                size.putIfAbsent(b, 1);

                union(a, b);
                System.out.println(size.get(find(a)));
            }
        }
    }

    static String find(String x) {
        if(!x.equals(parent.get(x))) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    static void union(String a, String b) {
        String findA = find(a);
        String findB = find(b);

        if(!findA.equals(findB)) {
            parent.put(findB, findA);
            size.put(findA, size.get(findA) + size.get(findB));
        }
    }
}
