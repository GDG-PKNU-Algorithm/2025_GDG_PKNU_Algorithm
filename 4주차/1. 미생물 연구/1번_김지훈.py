from collections import deque

n, q = map(int, input().split())
container = [[0] * n for _ in range(n)]
microbe_id = 1
microbe_info = {}
directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

def remove_microbe(mid):
    if mid in microbe_info:
        for r, c in microbe_info[mid]:
            container[r][c] = 0
        del microbe_info[mid]

def is_connected(cells):
    if not cells:
        return True

    visited = set()
    queue = deque()
    start = next(iter(cells))
    queue.append(start)
    visited.add(start)

    while queue:
        r, c = queue.popleft()
        for dr, dc in directions:
            nr, nc = r + dr, c + dc
            if (nr, nc) in cells and (nr, nc) not in visited:
                visited.add((nr, nc))
                queue.append((nr, nc))

    return len(visited) == len(cells)

def inject_microbe(r1, c1, r2, c2):
    global microbe_id

    affected_microbes = set()
    for i in range(r1, r2):
        for j in range(c1, c2):
            if container[i][j] != 0:
                affected_microbes.add(container[i][j])

    for mid in affected_microbes:
        remaining_cells = {(r, c) for r, c in microbe_info[mid] if not (r1 <= r < r2 and c1 <= c < c2)}

        if not remaining_cells or not is_connected(remaining_cells):
            remove_microbe(mid)
        else:
            microbe_info[mid] = remaining_cells
            for i in range(r1, r2):
                for j in range(c1, c2):
                    if container[i][j] == mid:
                        container[i][j] = 0

    new_cells = set()
    for i in range(r1, r2):
        for j in range(c1, c2):
            container[i][j] = microbe_id
            new_cells.add((i, j))

    microbe_info[microbe_id] = new_cells
    microbe_id += 1

def move_microbes():
    global container, microbe_info

    microbes = sorted(microbe_info.items(), key=lambda x: (-len(x[1]), x[0]))

    new_container = [[0] * n for _ in range(n)]
    new_microbe_info = {}

    for mid, cells in microbes:
        min_r = min(r for r, _ in cells)
        min_c = min(c for _, c in cells)
        max_r = max(r for r, _ in cells)
        max_c = max(c for _, c in cells)

        height = max_r - min_r + 1
        width = max_c - min_c + 1

        shape = [(r - min_r, c - min_c) for r, c in cells]

        placed = False
        for r in range(n - height + 1):
            if placed:
                break
            for c in range(n - width + 1):
                can_place = True
                for dr, dc in shape:
                    if new_container[r + dr][c + dc] != 0:
                        can_place = False
                        break

                if can_place:
                    new_cells = set()
                    for dr, dc in shape:
                        new_container[r + dr][c + dc] = mid
                        new_cells.add((r + dr, c + dc))
                    new_microbe_info[mid] = new_cells
                    placed = True
                    break

    container = new_container
    microbe_info = new_microbe_info

def calc_result():
    result = 0
    pairs_checked = set()

    for r in range(n):
        for c in range(n):
            if container[r][c] == 0:
                continue
            mid1 = container[r][c]
            for dr, dc in directions:
                nr, nc = r + dr, c + dc
                if 0 <= nr < n and 0 <= nc < n:
                    mid2 = container[nr][nc]
                    if mid2 != 0 and mid1 != mid2:
                        pair = tuple(sorted((mid1, mid2)))
                        if pair not in pairs_checked:
                            pairs_checked.add(pair)
                            result += len(microbe_info[mid1]) * len(microbe_info[mid2])

    return result

for _ in range(q):
    r1, c1, r2, c2 = map(int, input().split())
    inject_microbe(r1, c1, r2, c2)
    move_microbes()
    print(calc_result())
