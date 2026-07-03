package boj.Sort_BinarySearch_Comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 백준 1654 - 랜선 자르기
 * https://www.acmicpc.net/problem/1654
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - K(가진 랜선 수) <= 10,000, N(필요한 랜선 수) <= 1,000,000
 *   - 각 랜선 길이 <= 2^31 - 1 (약 21억) -> 길이 후보 범위가 매우 큼
 *   - "길이를 하나 정하면" 만들 수 있는 개수는 O(K)로 계산 가능.
 *     가능한 길이를 1..max(약 2^31)에서 선형 탐색하면 O(K * 2^31)로 불가능.
 *   - 길이를 이분탐색하면 O(K * log(maxLen)) ~= 10,000 * 31 ~= 31만 -> 충분히 빠름.
 *
 * [2] 알고리즘 유형 분류
 *   - "조건을 만족하는 최댓값"을 찾는 매개변수 탐색(Parametric Search) = 답에 대한 이분탐색.
 *   - 길이를 늘리면 만들 수 있는 개수는 단조 감소 -> 이분탐색 적용 가능.
 *
 * [3] 예제로 규칙 발견
 *   - 입력: 802, 743, 457, 539 / N=11
 *   - 길이 200으로 자르면: 802/200=4, 743/200=3, 457/200=2, 539/200=2 => 합 11 >= 11 (가능)
 *   - 길이 201로 자르면: 3+3+2+2=10 < 11 (불가능)
 *   - 따라서 가능한 최댓값은 200.
 *
 * [4] 의사코드
 *   lo = 1, hi = max(랜선 길이)
 *   while lo <= hi:
 *       mid = (lo + hi) / 2
 *       count = sum(len // mid)        // mid 길이로 만들 수 있는 개수
 *       if count >= N: lo = mid + 1    // 더 길게 시도 (가능)
 *       else:          hi = mid - 1    // 더 짧게 시도 (불가능)
 *   answer = hi                        // 조건을 만족한 마지막 길이
 *
 * [5] 구현 시 주의
 *   - count 합은 int 범위를 넘길 수 있으므로 long 사용.
 */
public class Boj1654 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        long[] lines = new long[k];
        long hi = 0;
        for (int i = 0; i < k; i++) {
            lines[i] = Long.parseLong(br.readLine().trim());
            hi = Math.max(hi, lines[i]);
        }

        long lo = 1;
        long answer = 0;
        while (lo <= hi) {
            long mid = (lo + hi) / 2;
            long count = 0;
            for (long len : lines) {
                count += len / mid;
            }
            if (count >= n) {   // mid 길이로 N개 이상 만들 수 있음 -> 더 길게
                answer = mid;
                lo = mid + 1;
            } else {            // 부족함 -> 더 짧게
                hi = mid - 1;
            }
        }

        System.out.println(answer);
    }
}
