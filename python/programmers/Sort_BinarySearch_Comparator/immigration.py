# 프로그래머스 - 입국심사
#
# [1] 제약조건 -> 시간복잡도 역산
#   - n <= 1,000,000,000, times 길이 <= 100,000, 각 시간 <= 1,000,000,000
#   - 시간 t -> 처리 인원은 O(M). 시간을 이분탐색 -> O(M * log(n * maxTime)) -> 충분.
#
# [2] 알고리즘 유형 분류
#   - 매개변수 탐색(답에 대한 이분탐색). "모두 처리 가능한 최소 시간".
#   - 시간이 늘면 처리 인원은 단조 증가 -> 이분탐색 가능.
#
# [3] 예제로 규칙 발견
#   - n=6, times=[7,10] -> t=28: 4+2=6 >= 6 (가능), t=27: 3+2=5 < 6 (불가) -> 답 28
#
# [4] 의사코드
#   lo, hi = 1, min(times) * n
#   while lo <= hi:
#       mid = (lo + hi) // 2
#       people = sum(mid // t for t in times)
#       if people >= n: hi = mid - 1   # 줄이기
#       else:           lo = mid + 1   # 늘리기
#   answer = lo
#
# [5] 구현


def solution(n, times):
    lo, hi = 1, min(times) * n
    answer = hi
    while lo <= hi:
        mid = (lo + hi) // 2
        people = sum(mid // t for t in times)
        if people >= n:     # 충분 -> 더 줄이기
            answer = mid
            hi = mid - 1
        else:               # 부족 -> 더 늘리기
            lo = mid + 1
    return answer


if __name__ == "__main__":
    print(solution(6, [7, 10]))  # 28
