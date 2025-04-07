#include<bits/stdc++.h>
using namespace std;

int solution(string str1, string str2) {
    
    transform(str1.begin(), str1.end(), str1.begin(), ::tolower);
    transform(str2.begin(), str2.end(), str2.begin(), ::tolower);
    
    vector<string> multiset1, multiset2;
    
    for(int i=0; i<str1.size()-1; i++) {
        if(islower(str1[i]) && islower(str1[i+1])) {
            string s = "";
            s += str1[i];
            s += str1[i+1];
            multiset1.push_back(s);
        }
    }
    
    for(int i=0; i<str2.size()-1; i++) {
        if(islower(str2[i]) && islower(str2[i+1])) {
            string s = "";
            s += str2[i];
            s += str2[i+1];
            multiset2.push_back(s);
        }
    }
    
    if(multiset1.empty() && multiset2.empty()) {
        return 65536;
    }
    
    map<string, int> count1, count2;
    for(auto s : multiset1) count1[s]++;
    for(auto s : multiset2) count2[s]++;
    
    int intersection_size = 0;
    int union_size = 0;
    
    set<string> all_elements;
    for(auto s : multiset1) all_elements.insert(s);
    for(auto s : multiset2) all_elements.insert(s);
    
    for(auto s : all_elements) {
        intersection_size += min(count1[s], count2[s]);
        union_size += max(count1[s], count2[s]);
    }
    
    double similarity = (double)intersection_size / union_size;
    return (int)(similarity * 65536);
}
