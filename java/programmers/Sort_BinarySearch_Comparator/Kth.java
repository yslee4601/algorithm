package programmers.Sort_BinarySearch_Comparator;

import java.util.Arrays;

/*
 * 프로그래머스 - K번째수
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - array 길이 <= 100, commands 길이 <= 50
 *   - command 하나당 부분배열 추출 + 정렬: O(N log N), N<=100 -> 매우 작음.
 *   - 전체 O(commands * N log N) ~= 50 * 700 -> 단순 구현으로 충분.
 *
 * [2] 알고리즘 유형 분류
 *   - 정렬 + 구간 슬라이싱 (단순 구현).
 *
 * [3] 예제로 규칙 발견
 *   - array=[1,5,2,6,3,7,4], command [2,5,3]
 *     -> 2~5번째(1-based) = [5,2,6,3] -> 정렬 [2,3,5,6] -> 3번째 = 5
 *
 * [4] 의사코드
 *   for each [i, j, k] in commands:
 *       sub = array[i-1 .. j-1]   // 양끝 포함
 *       sort(sub)
 *       result += sub[k-1]
 *
 * [5] 구현 - Arrays.copyOfRange는 [from, to) 이므로 (i-1, j) 사용.
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class Kth {

    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        for (int c = 0; c < commands.length; c++) {
            int i = commands[c][0];
            int j = commands[c][1];
            int k = commands[c][2];
            int[] sub = Arrays.copyOfRange(array, i - 1, j); // [i-1, j)
            Arrays.sort(sub);
            answer[c] = sub[k - 1];
        }
        return answer;
    }

    public static void main(String[] args) {
        Kth s = new Kth();
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};
        System.out.println(Arrays.toString(s.solution(array, commands))); // [5, 6, 3]
    }
}
