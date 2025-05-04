import java.util.*;

class Solution {
    public int solution(int m, int n, String[] b) {
        int answer = 0;

        Set<Integer> set = new HashSet<Integer>();
        Iterator<Integer> it;

        String[] board = new String[m * n];
        for(int i = 0; i < m; i++) {
            char[] charArray = b[i].toCharArray();
            for(int j = 0; j < n; j++) {
                board[i * n + j] = Character.toString(charArray[j]);
            }
        }

        while(true) {
            for(int i = 0; i < m - 1; i++) {
                for(int j = 0; j < n - 1; j++) {
                    int index = i * n + j;
                    if(!board[index].equals(" ") &&
                            board[index].equals(board[index + 1]) &&
                            board[index].equals(board[index + n]) &&
                            board[index].equals(board[index + n + 1])) {
                        set.add(index);
                        set.add(index + 1);
                        set.add(index + n);
                        set.add(index + n + 1);
                    }
                }
            }
            if(set.size() == 0) break;

            answer += set.size();
            it = set.iterator();
            while(it.hasNext()) {
                board[it.next()] = " ";
            }
            set.clear();

            for(int i = 0; i < n; i++) {
                for(int j = m - 1; j >= 0; j--) {
                    int index = j * n + i;
                    while(index + n < m * n && board[index + n].equals(" ")) {
                        board[index + n] = board[index];
                        board[index] = " ";
                        index = index + n;
                    }
                }
            }
        }

        return answer;
    }
}