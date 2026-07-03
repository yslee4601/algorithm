package programmers.Sort_BinarySearch_Comparator;

/*
 * 프로그래머스 - 입국심사
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - n(사람 수) <= 1,000,000,000, times 길이(심사관) <= 100,000, 각 시간 <= 1,000,000,000
 *   - "시간 t가 주어지면 처리 인원"은 O(M)으로 계산 가능.
 *   - 시간을 1..(min(times)*n) 이분탐색 -> O(M * log(n * maxTime)) ~= 1e5 * 60 -> 충분.
 *
 * [2] 알고리즘 유형 분류
 *   - 매개변수 탐색(답에 대한 이분탐색). "모두 처리 가능한 최소 시간" 탐색.
 *   - 시간이 늘면 처리 인원은 단조 증가 -> 이분탐색 가능.
 *
 * [3] 예제로 규칙 발견
 *   - n=6, times=[7,10]
 *   - t=28: 28/7=4명 + 28/10=2명 = 6명 >= 6 (가능)
 *   - t=27: 27/7=3 + 27/10=2 = 5 < 6 (불가) -> 답 28
 *
 * [4] 의사코드
 *   lo = 1, hi = min(times) * n   // 가장 빠른 심사관이 혼자 다 처리하는 최악 시간
 *   while lo <= hi:
 *       mid = (lo + hi) / 2
 *       people = Σ (mid / time)
 *       if people >= n: hi = mid - 1   // 더 줄여보기 (가능)
 *       else:           lo = mid + 1   // 더 늘리기 (부족)
 *   answer = lo                        // 조건을 만족하는 최소 시간
 *
 * [5] 구현 시 주의 - hi 와 people 누적은 long 사용(오버플로 방지).
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class Immigration {

    public long solution(int n, int[] times) {
        long min = Long.MAX_VALUE;
        for (int t : times) {
            min = Math.min(min, t);
        }

        long lo = 1;
        long hi = min * (long) n;   // 가장 빠른 심사관이 전부 처리하는 시간(상한)
        long answer = hi;

        while (lo <= hi) {
            long mid = (lo + hi) / 2;
            long people = 0;
            for (int t : times) {
                people += mid / t;
            }
            if (people >= n) {  // mid 시간이면 충분 -> 더 줄이기
                answer = mid;
                hi = mid - 1;
            } else {            // 부족 -> 더 늘리기
                lo = mid + 1;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Immigration s = new Immigration();
        System.out.println(s.solution(6, new int[]{7, 10})); // 28
    }
}
