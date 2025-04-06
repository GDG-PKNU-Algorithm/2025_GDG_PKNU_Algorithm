import re
from collections import Counter

def solution(str1, str2):
    answer = 0
    str1 = str1.lower()
    str2 = str2.lower()
    jstr1 = []
    jstr2 = []

    for i in range(len(str1)-1):
        if(re.fullmatch(r"[a-z]{1}",str1[i]) and re.fullmatch(r"[a-z]{1}",str1[i+1])):
            jstr1.append(str1[i] + str1[i+1])
    for i in range(len(str2)-1):
        if(re.fullmatch(r"[a-z]{1}",str2[i]) and re.fullmatch(r"[a-z]{1}",str2[i+1])):
            jstr2.append(str2[i] + str2[i+1])

    inter = list((Counter(jstr1) & Counter(jstr2)).elements())
    union = list((Counter(jstr1) | Counter(jstr2)).elements())

    if len(union) == 0:
        answer = 65536
    else:
        answer = int(len(inter) / len(union) * 65536)

    return answer