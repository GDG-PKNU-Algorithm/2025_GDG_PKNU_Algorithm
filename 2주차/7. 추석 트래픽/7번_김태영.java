import java.util.*;

class Traffic {
    int start;
    int end;
    Traffic(int start, int end){
        this.start = start;
        this.end = end;
    }
}
class Solution {
    public int solution(String[] lines) {
        int answer = 0;
        List<Traffic> traffic_list = new ArrayList();
        for(int i = 0; i < lines.length; i++) {
            int end =(int)((Integer.parseInt(lines[i].substring(11,13)) * 3600 + Integer.parseInt(lines[i].substring(14,16)) * 60) * 1000
                    + Double.parseDouble(lines[i].substring(17,23)) * 1000);
            int processing_time = (int)(Double.parseDouble(lines[i].substring(24, lines[i].length() - 1)) * 1000);
            int start = end - processing_time + 1;
            traffic_list.add(new Traffic(start, end));
        }
        int max = 1;
        int cnt;
        for(int i = 0; i < traffic_list.size(); i++) {
            cnt = 1;
            for(int j = i + 1; j < traffic_list.size(); j++){
                if(traffic_list.get(i).end + 1000 > traffic_list.get(j).start)
                    cnt++;
            }
            if(max < cnt)
                max = cnt;
        }
        answer = max;
        return answer;
    }
}