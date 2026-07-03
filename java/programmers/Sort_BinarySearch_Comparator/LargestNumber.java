package programmers.Sort_BinarySearch_Comparator;

import java.util.Arrays;
import java.util.Comparator;

/*
 * 프로그래머스 - 가장 큰 수
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - numbers 길이 <= 100,000, 원소 0..1,000
 *   - 모든 순열은 불가능. 정렬 기준만 잘 잡으면 O(N log N) -> 약 10만*17 -> 충분.
 *
 * [2] 알고리즘 유형 분류
 *   - 그리디 + 사용자 정의 정렬. 두 수 a, b를 문자열로 이어붙여 (a+b) vs (b+a) 비교.
 *
 * [3] 예제로 규칙 발견
 *   - [3, 30, 34, 5, 9]: "9" > "5" > "34" vs "3"? "343">"334" 이므로 34 먼저, 그 다음 3, 30
 *     -> "9","5","34","3","30" => "9534330"
 *   - "3"+"30"="330" > "30"+"3"="303" 이므로 3이 30보다 앞.
 *
 * [4] 의사코드
 *   strs = numbers를 문자열 배열로
 *   sort(strs) by (a, b) -> compare (b+a) with (a+b)  // 내림차순
 *   if strs[0] == "0": return "0"   // 모두 0이면 "00..0" 방지
 *   return join(strs)
 *
 * [5] 구현 - 첫 글자가 "0"이면 전부 0이므로 "0" 반환.
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class LargestNumber {

    public String solution(int[] numbers) {
        String[] strs = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            strs[i] = String.valueOf(numbers[i]);
        }

        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // (b+a) - (a+b): 큰 조합이 앞으로 오도록 내림차순 정렬
                return (b + a).compareTo(a + b);
            }
        });

        if (strs[0].equals("0")) {  // 가장 큰 값이 0 -> 전부 0
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LargestNumber s = new LargestNumber();
        System.out.println(s.solution(new int[]{6, 10, 2}));            // 6210
        System.out.println(s.solution(new int[]{3, 30, 34, 5, 9}));     // 9534330
        System.out.println(s.solution(new int[]{0, 0, 0}));             // 0
    }
}
