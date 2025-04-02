import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Solution {
    public int solution(String dartResult) {
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        String regex = "(\\d{1,2}[SDT][*#]?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dartResult);

        while (matcher.find()) {
            String part = matcher.group();
            Matcher partOfMatcher = Pattern.compile("(\\d{1,2})([SDT])([*#]?)").matcher(part);
            partOfMatcher.find();

            int num = Integer.parseInt(partOfMatcher.group(1));
            int power = switch (partOfMatcher.group(2)) {
                case "S" -> 1;
                case "D" -> 2;
                case "T" -> 3;
                default -> 1;
            };

            int score = (int) Math.pow(num, power);

            if (partOfMatcher.group(3).equals("*")) {
                if (!stack.isEmpty()) {
                    stack.push(stack.pop() * 2);
                }
                score *= 2;
            } else if (partOfMatcher.group(3).equals("#")) {
                score *= -1;
            }

            stack.push(score);
        }

        for (int num : stack) {
            result += num;
        }

        return result;
    }
}
