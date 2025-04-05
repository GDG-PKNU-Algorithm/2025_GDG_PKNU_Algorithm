import re

def solution(dartResult):
    answer = 0
    point = [0] * 3

    tryList = re.findall(r"(\d{1,2})([SDT])([*#]?)",dartResult)

    for i in range(3):
        point[i] = int(tryList[i][0])
        if(tryList[i][1] == "D"):
            point[i] = pow(point[i],2)
        elif(tryList[i][1] == "T"):
            point[i] = pow(point[i],3)

        if(tryList[i][2] == "*"):
            point[i] *= 2
            if(i != 0):
                point[i-1] *=2
        elif(tryList[i][2] == "#"):
            point[i] = -1 * point[i]


    for i in range(3):
        answer += point[i]

    return answer