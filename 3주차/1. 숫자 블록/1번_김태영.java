class Solution {
    public int[] solution(long begin, long end) {
        int[] answer = new int[(int)(end-begin) + 1];
        int index = 0;

        for (long num = begin; num <= end; num++) {
            if(num == 1){
                answer[index] = 0;
                index++;
                continue;
            }
            
            if (num % 10_000_000 == 0) {
                answer[index] = 10_000_000;
                index++;
                continue;
            }
            
            int divisor = 2;
            for (int i = divisor; (long) i * i <= num; i++) {
                if(num % i == 0 && i <= 10_000_000){
                    if ( num/i <= 10_000_000)
                        answer[index] = Math.max(answer[index], (int)num/i);
                    else
                        answer[index] = Math.max(answer[index], i);
                }
            }
          
            if (answer[index] == 0){
                answer[index] = 1;
            }
            index++;
        }
        
        return answer;
    }

}
