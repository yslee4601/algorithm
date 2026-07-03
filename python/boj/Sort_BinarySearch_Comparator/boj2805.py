import sys

# 백준 2805 - 나무 자르기
# https://www.acmicpc.net/problem/2805
#
# [1] 제약조건 -> 시간복잡도 역산
#   - N(나무 수) <= 1,000,000, M <= 2,000,000,000, 높이 <= 1,000,000,000
#   - 높이 H를 정하면 가져갈 양은 O(N). H를 0..max(높이) 이분탐색 -> O(N * log(maxH)).
#
# [2] 알고리즘 유형 분류
#   - "적어도 M을 가져가는 절단 높이의 최댓값" -> 매개변수 탐색(답에 대한 이분탐색).
#   - H가 높을수록 가져가는 양은 단조 감소 -> 이분탐색 가능.
#
# [3] 예제로 규칙 발견
#   - 20,15,10,17 / M=7 -> H=15: 5+0+0+2=7 >= 7 (가능), H=16: 4+0+0+1=5 < 7 (불가) -> 답 15
#
# [4] 의사코드
#   lo, hi = 0, max(trees)
#   while lo <= hi:
#       mid = (lo + hi) // 2
#       total = sum(max(0, t - mid) for t in trees)
#       if total >= M: lo = mid + 1   # 더 높게
#       else:          hi = mid - 1   # 더 낮게
#   answer = hi
#
# [5] 구현 시 주의 - 합(total)이 매우 커질 수 있으나 파이썬은 큰 정수 자동 처리.


def solve(n, m, trees):
    lo, hi = 0, max(trees)
    answer = 0
    while lo <= hi:
        mid = (lo + hi) // 2
        total = sum(t - mid for t in trees if t > mid)
        if total >= m:      # 충분 -> 더 높게
            answer = mid
            lo = mid + 1
        else:               # 부족 -> 더 낮게
            hi = mid - 1
    return answer


def main():
    data = sys.stdin.read().split()
    n = int(data[0])
    m = int(data[1])
    trees = [int(x) for x in data[2:2 + n]]
    print(solve(n, m, trees))


if __name__ == "__main__":
    main()
