#include <bits/stdc++.h>
using namespace std;
#define SIZE 33

int dx[]={0,0,1,1}, dy[]={0,1,0,1};
set<pair<int,int>> s;

void RemoveBlock(vector<string> &board, int r, int c) {
    set<char> m;
    for(int i=0;i<4;i++) 
        m.insert(board[r+dx[i]][c+dy[i]]);
    if(m.size() == 1) {
        if(m.find('-') != m.end()) 
            return;
        for(int i=0;i<4;i++)
            s.insert({r+dx[i], c+dy[i]});
    }
}

void DropBlock(vector<string> &board) {
    for(auto [x,y]:s)
        board[x][y] = '-';
    s.clear();
    for(int i = 0; i < board[i].size(); ++i)
        for(int j = board.size() - 1; j >= 1; --j)
            if(board[j][i] == '-')
                for(int k = j - 1; k >= 0; --k)
                    if(board[k][i] != '-') {
                        board[j][i] = board[k][i];
                        board[k][i] = '-';
                        break;
                    }
}

int solution(int m, int n, vector<string> board) {
    int answer = 0;
    while(1) {
        for(int i=0;i<m-1;i++) 
            for(int j=0;j<n-1;j++)
                RemoveBlock(board, i, j);
        if(s.empty()) break;
        answer += s.size();
        DropBlock(board);
    }
    return answer;
}