package boj.Sort_BinarySearch_Comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 백준 2805 - 나무 자르기
 * https://www.acmicpc.net/problem/2805
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - N(나무 수) <= 1,000,000, M(필요 길이) <= 2,000,000,000
 *   - 나무 높이 <= 1,000,000,000
 *   - 절단기 높이 H를 정하면 가져갈 양은 O(N)으로 계산.
 *   - H를 0..max(높이)에서 이분탐색: O(N * log(maxH)) ~= 1e6 * 30 ~= 3e7 -> 충분.
 *
 * [2] 알고리즘 유형 분류
 *   - "적어도 M을 가져가는 절단 높이의 최댓값" -> 매개변수 탐색(답에 대한 이분탐색).
 *   - H를 높이면 가져가는 양은 단조 감소 -> 이분탐색 가능.
 *
 * [3] 예제로 규칙 발견
 *   - 높이 20,15,10,17 / M=7
 *   - H=15: (20-15)+(15-15)+0+(17-15) = 5+0+0+2 = 7 >= 7 (가능)
 *   - H=16: 4+0+0+1 = 5 < 7 (불가능) -> 답 15
 *
 * [4] 의사코드
 *   lo = 0, hi = max(높이)
 *   while lo <= hi:
 *       mid = (lo + hi) / 2
 *       sum = Σ max(0, tree - mid)
 *       if sum >= M: lo = mid + 1   // 더 높게 (가능)
 *       else:        hi = mid - 1   // 더 낮게 (부족)
 *   answer = hi
 *
 * [5] 구현 시 주의
 *   - 잘린 나무 합(sum)과 M은 int 범위를 넘기므로 long 사용.
 */
public class Boj2805 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        long[] trees = new long[n];
        long hi = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            trees[i] = Long.parseLong(st.nextToken());
            hi = Math.max(hi, trees[i]);
        }

        long lo = 0;
        long answer = 0;
        while (lo <= hi) {
            long mid = (lo + hi) / 2;
            long sum = 0;
            for (long t : trees) {
                if (t > mid) {
                    sum += t - mid;
                }
            }
            if (sum >= m) {     // 충분히 가져감 -> 더 높게
                answer = mid;
                lo = mid + 1;
            } else {            // 부족 -> 더 낮게
                hi = mid - 1;
            }
        }

        System.out.println(answer);
    }
}
