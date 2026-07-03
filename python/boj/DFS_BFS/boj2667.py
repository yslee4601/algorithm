import sys

# 백준 2667 - 단지번호붙이기
# https://www.acmicpc.net/problem/2667
#
# [1] 제약조건 -> 시간복잡도 역산
#   - N <= 25 이므로 칸은 최대 25*25 = 625개.
#   - 각 칸을 한 번씩만 방문하면 O(N^2) = 625 -> 매우 작다.
#
# [2] 알고리즘 유형 분류
#   - "상하좌우로 연결된 1들의 덩어리(단지)" 찾기 = 연결 요소(Connected Component) 세기.
#   - 격자를 훑다 미방문 집(1)을 만나면 DFS로 연결된 집을 한 덩어리로 묶고 크기를 센다.
#     이 과정을 몇 번 했는지가 곧 단지 수.
#
# [3] 예제로 규칙 발견
#   - 7x7 지도 -> 단지 3개, 크기 7/8/9 (오름차순 출력).
#   - 대각선은 연결로 치지 않으므로 상하좌우 4방향만 사용.
#
# [4] 의사코드
#   for 모든 칸 (i, j):
#       if map==1 and 미방문:
#           size = DFS(i, j)   # 연결된 1을 모두 방문표시하며 개수 셈
#           sizes.append(size)
#   print(단지 수); sizes 오름차순 정렬 후 하나씩 출력
#
# [5] 구현 시 주의
#   - 입력이 "0110100"처럼 붙어서 들어오므로 문자 하나씩 파싱.
#   - visited는 "이미 단지에 포함됨" 표시. 재방문하면 크기가 중복 계산되므로 필수.
#   - N=25면 재귀 깊이가 최대 625라 파이썬 기본 한도(1000)에 근접 -> setrecursionlimit로 여유.

sys.setrecursionlimit(10 ** 6)

# 상하좌우 4방향
DX = (-1, 1, 0, 0)
DY = (0, 0, -1, 1)


def dfs(x, y, n, grid, visited):
    # (x, y)에서 시작해 연결된 1을 모두 방문표시하고, 방문한 집 개수를 반환
    visited[x][y] = True
    count = 1  # 지금 칸 1개 포함
    for d in range(4):
        nx, ny = x + DX[d], y + DY[d]
        if nx < 0 or ny < 0 or nx >= n or ny >= n:
            continue
        if grid[nx][ny] == 1 and not visited[nx][ny]:
            count += dfs(nx, ny, n, grid, visited)  # 이웃 단지 크기 누적
    return count


def solve(n, grid):
    visited = [[False] * n for _ in range(n)]
    sizes = []  # 각 단지의 집 수

    # 격자 전체를 훑으며 "새 단지의 시작점"을 찾는다
    for i in range(n):
        for j in range(n):
            if grid[i][j] == 1 and not visited[i][j]:
                sizes.append(dfs(i, j, n, grid, visited))

    sizes.sort()  # 오름차순 정렬
    return sizes


def main():
    data = sys.stdin.read().split()
    n = int(data[0])
    grid = [[int(ch) for ch in data[1 + i]] for i in range(n)]

    sizes = solve(n, grid)
    out = [str(len(sizes))]
    out.extend(str(s) for s in sizes)
    print("\n".join(out))


if __name__ == "__main__":
    main()
