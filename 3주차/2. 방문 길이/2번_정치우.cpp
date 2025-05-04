#include <bits/stdc++.h>
using namespace std;

int vis[15][15][4], dx[] = {0, 0, -1 , 1}, dy[] = {-1, 1, 0, 0};

int R(int dir) {
    if(dir == 0) return 1;
    if(dir == 1) return 0;
    if(dir == 2) return 3;
    if(dir == 3) return 2;
}

int solution(string dirs) {
    int answer = 0, x = 5, y = 5;
    for(auto c:dirs) {
        int nx, ny, dir;
        if(c == 'D') {
            dir = 2;
            nx = x + dx[2], ny = y + dy[2];
        }
        else if(c == 'U') {
            dir = 3;
            nx = x + dx[3], ny = y + dy[3];
        }
        else if(c == 'L') {
            dir = 0;
            nx = x + dx[0], ny = y + dy[0];
        }
        else if(c == 'R') {
            dir = 1;
            nx = x + dx[1], ny = y + dy[1];
        }
        if(nx < 0 || ny < 0 || nx > 10 || ny > 10) continue;
        if(!vis[nx][ny][dir] && !vis[nx][ny][R(dir)])
            ++answer, vis[nx][ny][dir] = 1;
        x = nx, y = ny;
    }
    return answer;
}
