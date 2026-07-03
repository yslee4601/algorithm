# 프로그래머스 - K번째수
#
# [1] 제약조건 -> 시간복잡도 역산
#   - array 길이 <= 100, commands 길이 <= 50
#   - command 당 슬라이싱 + 정렬 O(N log N), 전체 O(commands * N log N) -> 매우 작음.
#
# [2] 알고리즘 유형 분류 - 정렬 + 구간 슬라이싱(단순 구현).
#
# [3] 예제로 규칙 발견
#   - array=[1,5,2,6,3,7,4], [2,5,3] -> 2~5번째 [5,2,6,3] -> 정렬 [2,3,5,6] -> 3번째 = 5
#
# [4] 의사코드
#   for i, j, k in commands:
#       sub = sorted(array[i-1:j])   # 파이썬 슬라이스 [i-1:j]는 i-1..j-1 포함
#       answer.append(sub[k-1])
#
# [5] 구현


def solution(array, commands):
    answer = []
    for i, j, k in commands:
        sub = sorted(array[i - 1:j])   # i번째~j번째(1-based) = 인덱스 i-1..j-1
        answer.append(sub[k - 1])
    return answer


if __name__ == "__main__":
    print(solution([1, 5, 2, 6, 3, 7, 4], [[2, 5, 3], [4, 4, 1], [1, 7, 3]]))  # [5, 6, 3]
