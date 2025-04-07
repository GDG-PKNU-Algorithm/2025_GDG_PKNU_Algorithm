import math
def solution(str1, str2):
    answer = 0
    
    len_1 = len(str1)
    len_2 = len(str2)
    
    A=[]
    B=[]
    A_cnt = {}
    B_cnt = {}
    for i in range(len_1-1):
        tmp = str1[i:i+2].lower()
        if ord(tmp[0])<ord('a') or ord(tmp[0])>ord('z') or ord(tmp[1])<ord('a') or ord(tmp[1])>ord('z'):
            continue
        A.append(tmp)
        if tmp in A_cnt:
            A_cnt[tmp]+=1
        else:
            A_cnt[tmp]=1
    for i in range(len_2-1):
        tmp = str2[i:i+2].lower()
        if ord(tmp[0])<ord('a') or ord(tmp[0])>ord('z') or ord(tmp[1])<ord('a') or ord(tmp[1])>ord('z'):
            continue
        B.append(tmp)
        if tmp in B_cnt:
            B_cnt[tmp]+=1
        else:
            B_cnt[tmp]=1
    
    A.sort()
    B.sort()
    
    
    if len(A)==0 and len(B)==0:
        return 65536
    
    inter = {}
    union = {}
    
    def bin_search(target,is_a):
        start = 0
        end = -1
        tmp = []
        
        if is_a:
            end = len(B)-1
            tmp = B
        else:
            tmp = A
            end = len(A)-1
        
        while start<=end:
            mid = (start+end)//2
            
            if tmp[mid]==target:
                inter[target] = min(A_cnt[target],B_cnt[target])
                union[target] = max(A_cnt[target],B_cnt[target])
                return 1
            
            elif tmp[mid]>target:
                end = mid-1
                
            else:
                start = mid+1
            
        return 0

    for a in A:
        if a in inter or a in union:
            continue
            
        if bin_search(a,True)==0:
            union[a]=A_cnt[a]
    
    for b in B:
        if b in inter or b in union:
            continue
            
        if bin_search(b,False)==0:
            union[b]=B_cnt[b]
    
    res_1=0
    for key,value in inter.items():
        res_1+=value
    
    res_2=0
    for key,value in union.items():
        res_2+=value
    
    answer = (res_1/res_2)*65536

    return int(answer)