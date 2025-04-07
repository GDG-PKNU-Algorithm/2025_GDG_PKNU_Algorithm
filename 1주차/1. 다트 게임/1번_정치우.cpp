#include <bits/stdc++.h>

using namespace std;

int solution(string dartResult) {
    int answer = 0, idx = 0;
    string num = "";
    vector<int> result;
    for(auto c:dartResult) {
        if(c >= '0' && c <= '9')
            num += c;
        else if(c == 'S') {
            result.push_back(stoi(num));
            num = "", ++idx;
        }
        else if(c == 'D') {
            int n = stoi(num);
            result.push_back(n*n);
            num = "", ++idx;
        }
        else if(c == 'T') {
            int n = stoi(num); 
            result.push_back(n*n*n);
            num = "", ++idx;
        }
        else if(c == '*') {
            if(idx == 1) result[idx-1]*=2;
            else result[idx-1]*=2, result[idx-2]*=2;
        }
        else result[idx-1] = -result[idx-1];
    }
    for(int r:result) answer+=r;
    return answer;
}