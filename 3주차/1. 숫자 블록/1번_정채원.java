import java.util.*;

class Solution {
    // 소수면 -> 1 아니면 약수중 최댓값
    // 그 값이 10_000_000 을 넘으면 안됨
        public static int[] solution(long begin, long end) {
        ArrayList<Integer> answer = new ArrayList<>();

        for(long num = begin; num <= end; num++) {
            if (num == 1) {
                answer.add(0);
                continue;
            }

            int result = 1;

            for (long i = 2; i * i <= num; i++) {
                // quot * i = num
                // quot 가 10_000_000 보다 큰경우
                // i가 루프의 범위 끝까지 가도 quote가 10_000_000 아래로 내려오지 않는 case 존재
                // 아래 처럼 조건을 빡세게 걸어줘야함
                if(num % i == 0) {
                    long quot = num / i;
                    if(quot <= 10_000_000){
                        result = (int) quot;
                        break;
                    } else if (i <= 10_000_000){
                        result = (int) i;
                    }
                }

                // ------------- 이렇게 하면 qout 가 10_000_000 이상일때 i 가 최대 약수가 되는 경우를 커버하지 못함
                //  for(long i = 2; i * i <= num; i++) {
                //     if(num % i == 0) {
                //         result = (int)(num/i);
                //         if(result <= 10_000_000) break;
                //     }
                // }
                
                
            }
            answer.add(result);
        }


        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

}