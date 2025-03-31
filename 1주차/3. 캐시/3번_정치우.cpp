#include <bits/stdc++.h>
using namespace std;

int solution(int cacheSize, vector<string> cities) {
    int answer = 0, cS = cities.size();
    if(!cacheSize) {
        answer = 5 * cS;
        return answer;
    }
    vector<string> cache;
    for(auto &city:cities) {
        transform(city.begin(), city.end(), city.begin(), ::tolower);
        auto it = find(cache.begin(), cache.end(), city);
        if(it != cache.end()) {
            cache.erase(it);
            cache.push_back(city);
            ++answer;
        }
        else {
            if(cache.size() < cacheSize) cache.push_back(city);
            else {
                cache.erase(cache.begin() + 0);
                cache.push_back(city);
            }
            answer+=5;
        }
    }
    return answer;
}