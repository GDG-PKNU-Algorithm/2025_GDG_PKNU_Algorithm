def solution(m, n, board):
    board = [list(row) for row in board]
    answer = 0

    def find_to_remove_blocks():
        remove_blocks = set()
        for i in range(m - 1):
            for j in range(n - 1):
                if board[i][j] != 0 and board[i][j] == board[i][j + 1] == board[i + 1][j] == board[i + 1][j + 1]:
                    remove_blocks.add((i, j))
                    remove_blocks.add((i, j + 1))
                    remove_blocks.add((i + 1, j))
                    remove_blocks.add((i + 1, j + 1))
        return remove_blocks

    def drop_blocks():
        for i in range(n):
            empty_rows = []
            for j in range(m - 1, -1, -1):
                if board[j][i] == 0:
                    empty_rows.append(j)
                elif empty_rows:
                    lowest_empty_row = empty_rows.pop(0)
                    board[lowest_empty_row][i] = board[j][i]
                    board[j][i] = 0
                    empty_rows.append(j)

    while True:
        to_remove_blocks = find_to_remove_blocks()
        if not to_remove_blocks:
            break
        answer += len(to_remove_blocks)
        for i, j in to_remove_blocks:
            board[i][j] = 0
        drop_blocks()
    return answer
