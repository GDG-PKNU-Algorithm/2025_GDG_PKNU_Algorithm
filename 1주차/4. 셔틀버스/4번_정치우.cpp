#include <bits/stdc++.h>
using namespace std;

string solution(int n, int t, int m, vector<string> timetable) {
    string answer = "";
    vector<int> minuteTimeTable;
    for(auto t:timetable) {
        int num = stoi(t.substr(0,2)) * 60 + stoi(t.substr(3,2));
        minuteTimeTable.push_back(num);
    }
    sort(minuteTimeTable.begin(), minuteTimeTable.end());
    int st = 0, rideCnt, stTime, myTime;
    for(int i=0;i<n;i++) {
        rideCnt = 0, stTime = myTime = 9*60+i*t;
        for(int j=st;j<minuteTimeTable.size();j++) {
            if(minuteTimeTable[j] <= stTime) ++rideCnt;
            else {
                st = j;
                break;
            }
            if(rideCnt == m) {
                st = j+1;
                myTime = minuteTimeTable[j]-1;
                break;
            }
        }
    }
    string hour = to_string(myTime/60), minute = to_string(myTime%60);
    if(hour.size() < 2) hour = '0' + hour;
    if(minute.size() < 2) minute = '0' + minute;
    answer = hour + ':' + minute;
    return answer;
}