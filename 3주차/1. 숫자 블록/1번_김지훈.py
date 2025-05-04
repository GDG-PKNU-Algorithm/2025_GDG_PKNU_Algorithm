def solution(begin, end):
    def get_largest_block(i):
        if i == 1:
            return 0

        INF = 10000000
        largest_block = 1
        for j in range(2, int(i ** 0.5) + 1):
            if i % j == 0:
                pair = i // j
                if pair <= INF:
                    return pair
                if j <= INF:
                    largest_block = j
        return largest_block

    return [get_largest_block(i) for i in range(begin, end + 1)]