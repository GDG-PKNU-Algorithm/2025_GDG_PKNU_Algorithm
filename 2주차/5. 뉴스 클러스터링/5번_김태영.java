import java.util.*;
import java.lang.String;
import java.util.regex.*;


class Solution {

    public static double union(List<String> arr1, List<String> arr2) {
        List<String> big = arr1.size() > arr2.size() ? arr1 : arr2;
        List<String> small = arr1.size() > arr2.size() ? arr2 : arr1;
        Iterator<String> it = small.iterator();

        while (it.hasNext()) {
            String find = it.next();
            if (big.contains(find)) {
                big.remove(find);
            }
        }
        System.out.println(big.size() + small.size());
        return big.size() + small.size();
    }

    public static double intersect(List<String> arr1, List<String> arr2) {
        Iterator<String> it = arr1.iterator();
        ArrayList<String> clone = new ArrayList<String>();
        clone.addAll(arr2);

        int num = 0;
        while (it.hasNext()) {
            String find = it.next();
            if (clone.contains(find)) {
                clone.remove(find);
                num++;
            }
        }

        return num;
    }

    public int solution(String str1, String str2) {
        int answer = 0;
        List<String> arr1 = new ArrayList<>();
        List<String> arr2 = new ArrayList<>();
        String addition = null;
        for (int i = 0; i < str1.length() - 1; i++) {
            addition = str1.substring(i, i + 2);
            if (Pattern.matches("^[a-zA-Z]*$", addition))
                arr1.add(addition.toLowerCase());
        }

        for (int i = 0; i < str2.length() - 1; i++) {
            addition = str2.substring(i, i + 2);
            if (Pattern.matches("^[a-zA-Z]*$", addition))
                arr2.add(addition.toLowerCase());
        }

        if (arr1.size() == 0 && arr2.size() == 0) {
            return 65536;
        }
        double intersect = intersect(arr1, arr2);
        double union = union(arr1, arr2);
        return (int) ((intersect / union) * 65536);
    }
}
