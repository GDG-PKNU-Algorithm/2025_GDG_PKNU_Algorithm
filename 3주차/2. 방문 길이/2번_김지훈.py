def solution(dirs):
    answer = 0
    current_pos = [0, 0]
    visited_paths = set()

    move = {
        "U": (0, 1),
        "D": (0, -1),
        "R": (1, 0),
        "L": (-1, 0)
    }

    for dir in dirs:
        dx, dy = move[dir]
        next_x = current_pos[0] + dx
        next_y = current_pos[1] + dy

        if -5 <= next_x <= 5 and -5 <= next_y <= 5:
            path = ((current_pos[0], current_pos[1]), (next_x, next_y))
            reverse_path = ((next_x, next_y), (current_pos[0], current_pos[1]))

            if path not in visited_paths and reverse_path not in visited_paths:
                visited_paths.add(path)
                visited_paths.add(reverse_path)
                answer += 1

            current_pos = [next_x, next_y]

    return answer