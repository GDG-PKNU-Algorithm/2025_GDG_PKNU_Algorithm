#include <bits/stdc++.h>
using namespace std;

#define S 0
#define E 1

int solution(vector<string> lines) {
    
    int answer = 0;
    vector<vector<int>> times;

    for(auto s:lines) {
        int hour = stoi(s.substr(11,2)) * 60 * 60;
        int minute = stoi(s.substr(14,2)) * 60;
        double second = stod(s.substr(17, 6));
        
        int end = (hour + minute + second) * 1000;
        int ms = stod(s.substr(24, 5)) * 1000;
        int start = end - ms + 1;
        times.push_back({start, S}), times.push_back({end + 999, E});
    } 
    
    sort(times.begin(), times.end());
    
    int ans = 0;
    for(auto t:times) {
        if(t[1] == S) ++ans;
        else --ans;
        answer = max(answer, ans);
    }

    return answer;
}