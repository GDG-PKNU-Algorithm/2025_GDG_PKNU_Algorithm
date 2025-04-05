from collections import deque
import re

def solution(cacheSize, cities):
    answer = 0

    for i in range(len(cities)):
        cities[i] = cities[i].lower()

    if cacheSize == 0:
        return len(cities) * 5

    cache = deque(maxlen=cacheSize)

    for i in range(len(cities)):
        if(cities[i] in cache):
            answer += 1
            cache.remove(cities[i])
            cache.append(cities[i])
        else:
            answer += 5
            cache.append(cities[i])

    return answer