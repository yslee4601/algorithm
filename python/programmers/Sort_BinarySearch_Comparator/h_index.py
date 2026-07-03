# 프로그래머스 - H-Index
#
# [1] 제약조건 -> 시간복잡도 역산
#   - 논문 수 <= 1,000, 인용 횟수 0..10,000 -> 정렬 O(N log N)로 충분.
#
# [2] 알고리즘 유형 분류
#   - 정렬 후 탐색. 정의: h번 이상 인용된 논문이 h편 이상인 h의 최댓값.
#
# [3] 예제로 규칙 발견
#   - [3,0,6,1,5] -> 오름차순 [0,1,3,5,6], n=5
#     i번째(0-base)에서 "citations[i] 이상 인용된 논문 수" = n - i
#     각 위치의 후보 h = min(citations[i], n - i), 그 최댓값이 답.
#     i=2: min(3, 3)=3 -> 답 3
#
# [4] 의사코드
#   sort asc
#   h = max over i of min(citations[i], n - i)
#   return h
#
# [5] 구현


def solution(citations):
    citations.sort()
    n = len(citations)
    h = 0
    for i in range(n):
        # citations[i] 이상 인용된 논문 수 = n - i
        h = max(h, min(citations[i], n - i))
    return h


if __name__ == "__main__":
    print(solution([3, 0, 6, 1, 5]))  # 3
