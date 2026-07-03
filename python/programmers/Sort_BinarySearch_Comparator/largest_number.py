

# 프로그래머스 - 가장 큰 수
#
# [1] 제약조건 -> 시간복잡도 역산
#   - numbers 길이 <= 100,000, 원소 0..1,000
#   - 정렬 기준만 잘 잡으면 O(N log N) -> 약 10만*17 -> 충분.
#
# [2] 알고리즘 유형 분류
#   - 그리디 + 사용자 정의 정렬. 두 수 a, b 문자열을 (a+b) vs (b+a)로 비교.
#
# [3] 예제로 규칙 발견
#   - [3,30,34,5,9] -> "9534330".  "3"+"30"="330" > "30"+"3"="303" 이므로 3이 30보다 앞.
#
# [4] 의사코드
#   strs = [str(x) for x in numbers]
#   sort strs by comparator: (a+b) vs (b+a) 내림차순
#   if strs[0] == "0": return "0"      # 모두 0인 경우
#   return "".join(strs)
#
# [5] 구현 - cmp_to_key 로 두 문자열 결합 비교.


def compare(a, b):
    # (a+b) > (b+a) 이면 a가 앞으로 와야 하므로 음수 반환
    if a + b > b + a:
        return -1
    elif a + b < b + a:
        return 1
    return 0


def solution(numbers):
    strs = [str(x) for x in numbers]
    strs.sort(key=cmp_to_key(compare))
    if strs[0] == "0":      # 가장 큰 값이 0 -> 전부 0
        return "0"
    return "".join(strs)


if __name__ == "__main__":
    print(solution([6, 10, 2]))          # 6210
    print(solution([3, 30, 34, 5, 9]))   # 9534330
    print(solution([0, 0, 0]))           # 0
