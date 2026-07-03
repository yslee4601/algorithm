# 프로그래머스 - 타겟 넘버
# https://school.programmers.co.kr/learn/courses/30/lessons/43165
#
# [1] 제약조건 -> 시간복잡도 역산
#   - 숫자 개수 n: 2 ~ 20개. 각 숫자는 +/- 두 가지 -> 경우의 수 2^n.
#   - n=20이면 2^20 ~= 100만 -> 모든 경우를 다 만들어봐도(완전탐색) 충분히 빠르다.
#
# [2] 알고리즘 유형 분류
#   - "모든 조합을 빠짐없이 시도" = 완전탐색 -> DFS(백트래킹)로 자연스럽게 표현.
#   - 각 숫자마다 (더한다 / 뺀다) 두 갈래로 갈라지는 이진 트리를 깊이 우선 탐색.
#
# [3] 예제로 규칙 발견
#   - [1,1,1,1,1], target 3 -> 정답 5가지.
#   - index번째 숫자에서 +numbers[index] 하고 다음으로, -numbers[index] 하고 다음으로 내려간다.
#   - 마지막 숫자까지 다 썼을 때(index == n) 합이 target과 같으면 성공 1가지.
#
# [4] 의사코드
#   dfs(index, total):
#       if index == n: return 1 if total == target else 0
#       return dfs(index+1, total + numbers[index]) + dfs(index+1, total - numbers[index])
#   answer = dfs(0, 0)
#
# [5] 구현 시 주의
#   - visited 배열이 필요 없다. 격자/그래프가 아니라 "선택의 트리"라 같은 상태 재방문이 없다.
#   - 성공 개수를 세는 것이므로 각 가지의 반환값을 더해서 위로 올려준다.


def solution(numbers, target):
    n = len(numbers)

    # index번째 숫자를 +/- 두 갈래로 시도하는 DFS
    def dfs(index, total):
        # 모든 숫자를 다 썼다면(잎 노드): 합이 target과 같으면 성공 1가지
        if index == n:
            return 1 if total == target else 0
        # 더하는 경우 + 빼는 경우의 성공 수를 합산
        return dfs(index + 1, total + numbers[index]) + dfs(index + 1, total - numbers[index])

    # 0번째 숫자부터, 지금까지 합 0으로 시작
    return dfs(0, 0)


if __name__ == "__main__":
    print(solution([1, 1, 1, 1, 1], 3))  # 5
    print(solution([4, 1, 2, 1], 4))      # 2
