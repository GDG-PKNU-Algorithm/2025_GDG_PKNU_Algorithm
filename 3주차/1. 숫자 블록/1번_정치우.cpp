#include <bits/stdc++.h>
using namespace std;

#define ll long long
#define MAX 10'000'000

ll f(ll num) {
    if (num == 1) return 0;
    ll res = 1;
    for(ll i=2; i*i <= num; i++) {
        if (num % i == 0) {
            res = i;
            if (num / i <= MAX)
                return (num / i);
        }
    }
    return res;
}

vector<int> solution(ll begin, ll end) {
    vector<int> answer;
    for (; begin <= end; begin++) {
        answer.push_back(f(begin));
    }
    return answer;
}
