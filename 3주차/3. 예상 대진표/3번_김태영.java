class Solution
{
    public int solution(int n, int A, int B)
    {
        int answer = 0;

        while(A - B != 0){
            if(A % 2 == 1)
                A += 1;
            if(B % 2 == 1)
                B += 1;

            A = A / 2;
            B = B / 2;
            answer++;

        }
        

        return answer;
    }
}
