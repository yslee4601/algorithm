# 프로그래머스 - 특이한 정렬
#
# [1] 제약조건 -> 시간복잡도 역산
#   - numlist 길이 <= 100, 원소/n <= 10,000, 중복 없음 -> 정렬 한 번 O(N log N).
#
# [2] 알고리즘 유형 분류 - 사용자 정의 키 정렬(sorted key).
#
# [3] 예제로 규칙 발견
#   - [1,2,3,4,5,6], n=4 -> 거리 오름차순, 같으면 큰 수 먼저 -> [4,5,3,6,2,1]
#
# [4] 의사코드
#   key = (|x - n|, -x)   # 거리 오름차순, 거리 같으면 x 내림차순
#   return sorted(numlist, key)
#
# [5] 구현


def solution(numlist, n):
    return sorted(numlist, key=lambda x: (abs(x - n), -x))


if __name__ == "__main__":
    print(solution([1, 2, 3, 4, 5, 6], 4))                       # [4, 5, 3, 6, 2, 1]
    print(solution([10000, 20, 36, 47, 40, 6, 10, 7000], 30))    # [36, 40, 20, 47, 10, 6, 7000, 10000]
