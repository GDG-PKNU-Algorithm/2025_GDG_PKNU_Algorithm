def solution(n, arr1, arr2):
    answer = []
    for i in range(n):
        binary_str = bin(arr1[i] | arr2[i])[2:].zfill(n)
        answer.append(''.join('#' if bit == '1' else ' ' for bit in binary_str))
    return answer
