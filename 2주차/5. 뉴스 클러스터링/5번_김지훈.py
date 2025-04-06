import re
from collections import Counter

def solution(str1, str2):
    str1 = str1.lower()
    str2 = str2.lower()

    substr1 = []
    substr2 = []

    for i in range(len(str1) - 1):
        substring = str1[i:i + 2]
        if re.match("^[a-z]{2}$", substring):
            substr1.append(substring)

    for i in range(len(str2) - 1):
        substring = str2[i:i + 2]
        if re.match("^[a-z]{2}$", substring):
            substr2.append(substring)

    counter1 = Counter(substr1)
    counter2 = Counter(substr2)

    intersection = 0
    union = 0

    for key in set(counter1) | set(counter2):
        intersection += min(counter1[key], counter2[key])
        union += max(counter1[key], counter2[key])

    if union == 0:
        return 65536

    answer = int(65536 * intersection / union)

    return answer
