import sys

# 백준 1654 - 랜선 자르기
# https://www.acmicpc.net/problem/1654
#
# [1] 제약조건 -> 시간복잡도 역산
#   - K(가진 랜선 수) <= 10,000, N(필요한 랜선 수) <= 1,000,000, 각 길이 <= 2^31-1
#   - 길이를 정하면 개수는 O(K). 길이를 1..max 선형 탐색은 불가능(범위가 21억).
#   - 길이를 이분탐색하면 O(K * log(maxLen)) ~= 1만 * 31 -> 충분히 빠름.
#
# [2] 알고리즘 유형 분류
#   - "조건을 만족하는 최댓값" -> 매개변수 탐색(답에 대한 이분탐색).
#   - 길이가 길수록 만들 수 있는 개수는 단조 감소 -> 이분탐색 가능.
#
# [3] 예제로 규칙 발견
#   - 802,743,457,539 / N=11
#   - 길이 200: 4+3+2+2 = 11 >= 11 (가능) / 길이 201: 3+3+2+2 = 10 < 11 (불가) -> 답 200
#
# [4] 의사코드
#   lo, hi = 1, max(lines)
#   while lo <= hi:
#       mid = (lo + hi) // 2
#       count = sum(len // mid for len in lines)
#       if count >= N: lo = mid + 1   # 더 길게
#       else:          hi = mid - 1   # 더 짧게
#   answer = hi
#
# [5] 구현 시 주의 - 파이썬 정수는 자동 다중정밀도라 오버플로 걱정 없음.


def solve(k, n, lines):
    lo, hi = 1, max(lines)
    answer = 0
    while lo <= hi:
        mid = (lo + hi) // 2
        count = sum(length // mid for length in lines)
        if count >= n:      # mid 길이로 N개 이상 -> 더 길게
            answer = mid
            lo = mid + 1
        else:               # 부족 -> 더 짧게
            hi = mid - 1
    return answer


def main():
    data = sys.stdin.read().split()
    k = int(data[0])
    n = int(data[1])
    lines = [int(x) for x in data[2:2 + k]]
    print(solve(k, n, lines))


if __name__ == "__main__":
    main()
