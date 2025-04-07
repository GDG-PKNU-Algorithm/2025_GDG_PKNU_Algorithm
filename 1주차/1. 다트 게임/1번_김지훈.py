import re

def solution(dart_result):
    arr = re.findall(r'\d+[DST]|\D', dart_result)
    scores = []
    for i in range(len(arr)):
        if arr[i] == '*':
            if len(scores) > 1:
                scores[-2] *= 2
            scores[-1] *= 2
        elif arr[i] == '#':
            scores[-1] *= -1
        else:
            score = int(arr[i][:-1])
            area = arr[i][-1]
            if area == 'S':
                scores.append(score)
            elif area == 'D':
                scores.append(score ** 2)
            elif area == 'T':
                scores.append(score ** 3)
    return sum(scores)
