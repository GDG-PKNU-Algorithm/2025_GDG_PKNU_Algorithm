#include <bits/stdc++.h>
using namespace std;

struct P {
    int x, y, d, cost;
};
int n, dx[] = {0, -1, 0, 1}, dy[] = {-1, 0, 1, 0};
int cost[27][27][4];

bool comp(P p1, P p2) { return p1.cost > p2.cost; }
inline bool isOut(int x, int y) { return x < 0 || y < 0 || x >= n || y >= n; }

int solution(vector<vector<int>> board) {
    priority_queue<P, vector<P>, decltype(&comp)> pq(comp);
    n = board.size();
    pq.push({0,0,-1,-500}), board[0][0] = 1;
    while(!pq.empty()) {
        P now = pq.top();
        pq.pop();
        for(int d=0;d<4;d++) {
            if(now.d != d && (now.d%2 == d%2)) continue;
            int nx = now.x+dx[d], ny = now.y+dy[d];
            if(isOut(nx,ny) || board[nx][ny] == 1) continue;
            int nCost = now.cost + 100;
            if(now.d != d) nCost += 500;
            if(cost[nx][ny][d] == 0 || cost[nx][ny][d] > nCost) {
                cost[nx][ny][d] = nCost;
                pq.push({nx,ny,d,nCost});
            }
        }
    }
    int res = INT_MAX;
    for(int c:cost[n-1][n-1]) {
        if(!c) continue;
        res = min(res, c);
    }
    return res;
}
