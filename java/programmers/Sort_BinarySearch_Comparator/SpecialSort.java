package programmers.Sort_BinarySearch_Comparator;

import java.util.Arrays;
import java.util.Comparator;

/*
 * 프로그래머스 - 특이한 정렬
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - numlist 길이 <= 100, 원소/n <= 10,000, 중복 없음.
 *   - 정렬 한 번 O(N log N) -> 매우 작음.
 *
 * [2] 알고리즘 유형 분류
 *   - 사용자 정의 비교 정렬(Custom Comparator).
 *
 * [3] 예제로 규칙 발견
 *   - [1,2,3,4,5,6], n=4 -> 거리 [3,2,1,0,1,2]
 *     거리 오름차순, 거리 같으면 큰 수 먼저 -> [4, 5,3, 6,2, 1] = [4,5,3,6,2,1]
 *
 * [4] 의사코드
 *   sort(numlist) with comparator:
 *       1순위: |x - n| 오름차순
 *       2순위: x 내림차순
 *
 * [5] 구현 - int[] 정렬에는 Comparator를 쓸 수 없어 Integer[]로 박싱 후 정렬.
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class SpecialSort {

    public int[] solution(int[] numlist, int n) {
        Integer[] boxed = new Integer[numlist.length];
        for (int i = 0; i < numlist.length; i++) {
            boxed[i] = numlist[i];
        }

        Arrays.sort(boxed, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                int da = Math.abs(a - n);
                int db = Math.abs(b - n);
                if (da != db) {
                    return Integer.compare(da, db); // 거리 오름차순
                }
                return Integer.compare(b, a);       // 거리 같으면 큰 수 먼저
            }
        });

        int[] answer = new int[boxed.length];
        for (int i = 0; i < boxed.length; i++) {
            answer[i] = boxed[i];
        }
        return answer;
    }

    public static void main(String[] args) {
        SpecialSort s = new SpecialSort();
        System.out.println(Arrays.toString(
                s.solution(new int[]{1, 2, 3, 4, 5, 6}, 4)));            // [4, 5, 3, 6, 2, 1]
        System.out.println(Arrays.toString(
                s.solution(new int[]{10000, 20, 36, 47, 40, 6, 10, 7000}, 30))); // [36, 40, 20, 47, 10, 6, 7000, 10000]
    }
}
