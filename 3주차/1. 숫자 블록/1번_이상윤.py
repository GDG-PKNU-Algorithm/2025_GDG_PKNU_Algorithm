def solution(begin, end):
    answer = list(range(begin, end + 1))

    for i in range(len(answer)):
        answer[i] = getBiggest(answer[i], end)

    if begin == 1:
        answer[0] = 0

    return answer

def getBiggest(num, end):
    maxDivisor = 1

    for i in range(2, int(num**0.5) + 1):
        if num % i == 0:
            paired = num // i
            if paired <= 10000000:
                return paired
            if i <= 10000000:
                maxDivisor = i

    return maxDivisor
