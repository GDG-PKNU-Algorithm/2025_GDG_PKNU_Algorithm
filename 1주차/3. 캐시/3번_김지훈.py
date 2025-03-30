from collections import deque

def solution(cache_size, cities):
    answer = 0
    cache_list = deque(maxlen=cache_size)
    if cache_size == 0:
        answer = len(cities) * 5
    else:
        for city in cities:
            city = city.lower()
            if city in cache_list:
                answer += 1
                cache_list.remove(city)
                cache_list.append(city)
            else:
                answer += 5
                if len(cache_list) == cache_size:
                    cache_list.popleft()
                cache_list.append(city)
    return answer
