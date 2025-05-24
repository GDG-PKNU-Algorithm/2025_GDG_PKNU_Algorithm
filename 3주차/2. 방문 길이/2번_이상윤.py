moves = {
    'U': (0, 1),
    'D': (0, -1),
    'R': (1, 0),
    'L': (-1, 0)
}

def solution(dirs):
    x, y = 0, 0
    visited = set()

    for d in dirs:
        if canMove(x, y, d):
            path = move(x, y, d)
            visited.add((path[0], path[1]))
            visited.add((path[1], path[0]))
            x, y = path[1]

    return len(visited) // 2

def canMove(x, y, d):
    dx, dy = moves[d]
    nx, ny = x + dx, y + dy

    if nx ** 2 > 25 or ny ** 2 > 25:
        return False
    return True

def move(x,y,d):
    dx, dy = moves[d]
    nx, ny = x + dx, y + dy

    return (x, y), (nx, ny)
