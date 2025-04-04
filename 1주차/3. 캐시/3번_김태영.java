import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        Queue<String> queue = new LinkedList<>();
        int result =0;
        if (cacheSize == 0){
            return 5 * cities.length;
        }
        for(int i =0; i< cities.length; i++){
            if(queue.contains(cities[i].toLowerCase())){
                queue.remove(cities[i].toLowerCase());
                queue.add(cities[i].toLowerCase());
                result += 1;
            }
            else {
                if(queue.size() == cacheSize){
                    queue.poll();
                }
                queue.add(cities[i].toLowerCase());
                result += 5;
            }
        }

        return result;
    }
}