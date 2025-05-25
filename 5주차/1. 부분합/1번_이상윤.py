import sys

n, s = map(int, input().split())
arr = list(map(int, input().split()))

start, end = 0, 0
shortest = n + 1
total = 0

while True:
    if total >= s:
        shortest = min(shortest, end - start)
        total -= arr[start]
        start += 1
    elif end == n:
        break
    else:
        total += arr[end]
        end += 1

print(shortest if shortest != n + 1 else 0)