package programmers.Sort_BinarySearch_Comparator;

import java.util.Arrays;

/*
 * 프로그래머스 - H-Index
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - 논문 수 n <= 1,000, 인용 횟수 0..10,000
 *   - 정렬 O(N log N) 또는 카운팅 O(N + maxCite) -> 매우 작음. 정렬 방식 사용.
 *
 * [2] 알고리즘 유형 분류
 *   - 정렬 후 탐색. 정의: h번 이상 인용된 논문이 h편 이상인 h의 최댓값.
 *
 * [3] 예제로 규칙 발견
 *   - [3,0,6,1,5] -> 내림차순 [6,5,3,1,0]
 *     i=0: citations[0]=6 >= 1 (h후보 1)
 *     i=1: 5 >= 2 (h후보 2)
 *     i=2: 3 >= 3 (h후보 3)
 *     i=3: 1 >= 4? 아니오 -> 중단. 답 3
 *   - 내림차순 정렬 후 citations[i] >= i+1 을 만족하는 가장 큰 (i+1).
 *
 * [4] 의사코드
 *   sort desc
 *   h = 0
 *   for i in 0..n-1:
 *       if citations[i] >= i + 1: h = i + 1
 *       else: break
 *   return h
 *
 * [5] 구현 - 오름차순 정렬 후 뒤에서 인덱스를 뒤집어 처리해도 동일.
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class HIndex {

    public int solution(int[] citations) {
        // 오름차순 정렬 후, 뒤에서부터 보면 citations[i] 이상 인용된 논문 수 = n - i
        Arrays.sort(citations);
        int n = citations.length;
        int h = 0;
        for (int i = 0; i < n; i++) {
            int cite = citations[i];
            int papersWithAtLeast = n - i; // citations[i] 이상 인용된 논문 수
            h = Math.max(h, Math.min(cite, papersWithAtLeast));
        }
        return h;
    }

    public static void main(String[] args) {
        HIndex s = new HIndex();
        System.out.println(s.solution(new int[]{3, 0, 6, 1, 5})); // 3
    }
}
