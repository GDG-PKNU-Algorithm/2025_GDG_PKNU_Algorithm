package codetree.삼성_2025_상반기_오후;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        // time limit : 3초
        // memory limit : 272 MB
        // 기대 출력 -> 정찰명령(400) -> 보고받는 시간 출력

        // 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int Q = Integer.parseInt(br.readLine());

        HashMap<Integer, Integer> map = new LinkedHashMap<>();
        map.put(0, 0);
        int idx = 1;

        for (int t = 0; t < Q; t++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());

            if (cmd == 100) { // 마을 건설
                int N = Integer.parseInt(st.nextToken());
                for (int i = 0; i < N; i++) {
                    int x = Integer.parseInt(st.nextToken()); // 좌표
                    map.put(idx++, x);
                }

            } else if (cmd == 200) { //개미집 건설
                int x = Integer.parseInt(st.nextToken()); // 건설 위치 x
                map.put(idx++, x);

            } else if (cmd == 300) { // 개미집 철거
                int q = Integer.parseInt(st.nextToken());
                map.remove(q);

            } else if (cmd == 400) {
                int r = Integer.parseInt(st.nextToken());

                ArrayList<Integer> list = new ArrayList<>(map.keySet());

                // 이분 탐색
                int left = 0;
                int right = 1000000000;

                // 시간의 최소를 구해야함
                while (left <= right) {
                    int mid = (left + right) / 2;
                    int count = 0; // 개미 수

                    int lastPos = -0x3f3f3f3f; // 첫번째 칸에는 무조건 들어가야하기 때문에

                    for(int i = 1; i < list.size(); i++) {
                        int curHouse = map.get(list.get(i));

                        if(curHouse - lastPos > mid){
                            count++;
                            lastPos = curHouse;
                        }
                    }

                    if (r >= count) {
                        // 문제에서 제시한 마리수보다 mid 값을 만족시키기위한 개미수가 작다면 mid 값을 더 줄여봐도됨
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                System.out.println(left);

            }

        }


        // 1. 마을 건설
        // 여왕 -> 0
        // 개미집 : 1부터 N
        // 오름차순으로 주어짐


        // 2. 개미집 건설
        // 개미집 위치는 p -> 이전 건설된 위치보다 더 큰 (먼 좌표)
        // k 번째 건설 명령
        // 해당 개미집 번호는 N + k


        // 3. 개미집 철거
        // 철거 대상 : q -> q번 개미집 철거해라
        // ( q 번이 없는 경우 없으)


        // 4. 개미집 정찰 (핵심)
        // 정찰을 나갈 개미의 수 r
        // 선택 -> 일개미들을ㄹ 추발 위치를 선택
        // 각 개미들을 매초 x 값이 1 증가하는 방향으로 이동
        // 개미가 지나는 집 (처음 선택 집 포함)은 안전한 개미집, 여왕 개미집은 항상 안전한 개미집
        // 각 개미들은 처음 만나는 개미집이 안전한 개미집인 경우에는 이동 중지
        // 개미들은 처음 개미집 선택 시, 정찰에 걸리는 시간이 최소가 되도록 적절히 선택

        // 생각 나는 자료구조 -> 배열, 연결리스트 정도 아니면 개미집들과 거리를 맵으로...
        // r 개의 포인터(개미) 들이 전체 리스트를 순회하는데 걸리는 시간이 최소가 되는 위치를 잡아야함
        // 추가적으로 리스트내의 값들에 빈번한 수정이 발생함

        // 1. parameterSearch? 각 개미집들 사이의 거리를 이동시간으로 두고 : 아닌거같음.. 개미의 수를 구하는 것도 아니고 결과적으로 개미의 위치를 알아야하기 때문에..
        // 2. 그리디? -> 개미집을 선택하는 그리디한 방법이 있나?
        // 생각나는 그리디 풀이 -> 각 개미집 사이의 거리를 구간이라 했을때 그 구간의 길이가 가장 큰 곳부터 제외(개미 배치) 시키는 방식

        // 전체 시간 = 구간 A + 구간 B + 구간 C
        // 가장 긴 구간을 먼저 제외시키는 것이 결과적으로 제일 최선의 선택인가??
        // 구간 B가 최대라고 하고 만약 구간 B를 제외 시키지 않고 구간 C나 A를 제외 시켜서는
        // 절대 전체 시간이 B를 제외시킨것보다 짧아 질 수 없음

        // 해보니깐 잘못된 방식...
        // 처음 조건에서 x 좌표의 범위가 1000000000 인걸 못보고 10000으로 착각함 범위가 10억인걸 봤으면 이분탐색을 바로 생각했을듯
        // 개미집의 위치가 중요한 변수이고 이걸 구하는 그리디한 방법을 떠오렸는데 정당하지 않은듯
        // 그리디 풀이는 한번해보고 안되면 손절하자
        // 
        // parametric search로 푸는게 맞음 개미의 출발위치를 정확히 알아야하고 그걸 결정하는게 중요한줄 알았는데 아니였음 개미위치보다는 r 개의 개미로 탐색할 수 있는 최소 시간을 구하고
        // 개미집의 수가 최대 10000 개이니깐 충분히 탐색해볼만 함
        // 최적화 : r 개의 개미로 탐색할 수 있는 최소 시간
        // 결정 : 시간이 x 일때, 필요한 개미가 r 개 이상인가 아닌가
        // 결정함수 -> 주어진 시간을 몇개의 개미로 커버가능한지 확인하고 개미의 수가 주어진 r 보다 크다면 시간을 늘리고 작거나 같다면 시간을 더 줄여보면 됨



        // 3. 개미집 간의 간격/정찰개미 수/... 최소시간을 저장할 dp 테이블을 만들 수 있나


        // 정찰시 무조건 1번에는 개미가 있어야함 (개미는 x 가 증가하는 방향으로 나아감)

    }
}
