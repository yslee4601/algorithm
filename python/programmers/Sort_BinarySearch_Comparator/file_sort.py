# 프로그래머스 - [3차] 파일명 정렬
#
# [1] 제약조건 -> 시간복잡도 역산
#   - files <= 1,000개, 각 파일명 <= 100자.
#   - 파싱 O(길이) + 정렬 O(N log N) -> 매우 작음.
#
# [2] 알고리즘 유형 분류
#   - 문자열 파싱 + 안정 정렬. 파이썬 sorted는 stable이라 동률 시 입력순 유지.
#
# [3] 예제로 규칙 발견 (HEAD/NUMBER/TAIL)
#   - foo9.txt         -> HEAD="foo", NUMBER="9"
#   - foo010bar020.zip -> HEAD="foo", NUMBER="010"
#   - F-15             -> HEAD="F-",  NUMBER="15"
#   정렬: 1) HEAD 소문자 사전순  2) NUMBER 정수값  3) 동률이면 입력순 유지
#
# [4] 의사코드
#   for f in files:
#       head = 숫자 직전까지의 문자
#       number = 그 뒤 연속 숫자(최대 5자리)
#   sorted(files, key=(head.lower(), int(number)))   # 안정 정렬
#
# [5] 구현


def parse(f):
    idx, n = 0, len(f)

    # HEAD: 숫자 직전까지
    head = []
    while idx < n and not f[idx].isdigit():
        head.append(f[idx])
        idx += 1

    # NUMBER: 연속 숫자 최대 5자리
    num = []
    while idx < n and f[idx].isdigit() and len(num) < 5:
        num.append(f[idx])
        idx += 1

    return "".join(head).lower(), int("".join(num))


def solution(files):
    # sorted는 안정 정렬이므로 (head, number)가 같으면 입력 순서 유지
    return sorted(files, key=parse)


if __name__ == "__main__":
    files = ["img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"]
    print(solution(files))
    # ['img1.png', 'IMG01.GIF', 'img02.png', 'img2.JPG', 'img10.png', 'img12.png']
