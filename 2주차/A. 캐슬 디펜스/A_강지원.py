import sys
from collections import deque

N,M,D = map(int,sys.stdin.readline().split())

board = []
enemy_cnt = 0
for i in range(N):
    board.append(list(map(int,sys.stdin.readline().split())))
    enemy_cnt+=sum(board[i])

board.append([2 for i in range(M)])

moves = [[0,-1],[-1,0],[0,1]]

def bfs(game_board,visited,start):
    dq = deque()

    dq.append((start,0))
    min_node = (-1,-1)

    while dq:
        now = dq.popleft()

        node = now[0]
        dis = now[1]
        visited[node[0]][node[1]]=True

        if game_board[node[0]][node[1]]==1 and dis<=D:
            return node
            
        for move in moves:
            x = node[0]+move[0]
            y = node[1]+move[1]

            if x<0 or x>N-1 or y<0 or y>M-1 or visited[x][y] or dis+1>D:
                continue
            
            dq.append(((x,y),dis+1))

    return min_node

def game(people,game_board,ec):
    cnt = 0
    now = ec

    while now>0:
        enemys = []
        for p in people:
            visited = [[False for i in range(M)]for i in range(N+1)]
            enemy = bfs(game_board,visited,p)
            if enemy[0]!=-1:
                enemys.append(enemy)

        enemys = list(set(enemys))
        #print(enemys)
        for e in enemys:
            game_board[e[0]][e[1]]=0
            cnt+=1
        
        
        prev = []
        for i in range(N):
            tmp = []
            for j in range(M):
                tmp.append(game_board[i][j])
                if i == 0:
                    game_board[i][j]=0
                else:
                    game_board[i][j]=prev[i-1][j]
            #print(game_board[i])
            prev.append(tmp)
        
        now = 0
        for i in range(N):
            now+=sum(game_board[i])
        #print(people,now,cnt)
        

    return cnt

def copy_board(b):
    new = [[0 for i in range(M)]for j in range(N+1)]
    for i in range(N):
        for j in range(M):
            new[i][j]=b[i][j]
    new.append([2 for i in range(M)])
        
    return new

ans = 0
for i in range(M):
    for j in range(i+1,M):
        for z in range(j+1,M):
            people = [(N,i),(N,j),(N,z)]
            new = copy_board(board)
            ans = max(ans,game(people,new,enemy_cnt))

print(ans)