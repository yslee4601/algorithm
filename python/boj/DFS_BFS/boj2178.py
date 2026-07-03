import sys
from collections import deque

# 백준 2178 - 미로 탐색
# https://www.acmicpc.net/problem/2178
#
# [1] 제약조건 -> 시간복잡도 역산
#   - N, M <= 100 이므로 칸(정점)은 최대 100*100 = 10,000개.
#   - 격자에서 한 칸은 상하좌우 4개 이웃만 가지므로 BFS는 O(N*M) = 1만 -> 매우 빠름.
#
# [2] 알고리즘 유형 분류
#   - "지나야 하는 최소 칸 수" = 시작~도착 최단거리.
#   - 모든 이동 비용이 1(한 칸)로 같음 -> 가중치 1의 최단거리 -> BFS가 곧 최단거리.
#     (DFS는 먼저 도달한 경로가 최단이라는 보장이 없어 최단거리에는 부적합)
#
# [3] 예제로 규칙 발견
#   - BFS는 출발점에서 "거리가 가까운 칸부터" 파도처럼 퍼진다.
#   - 시작 칸 거리를 1로 두면, 도착 칸에 처음 도달했을 때 거리값이 곧 답(칸 수).
#   - 예제1 -> 15, 예제2 -> 9 (시작/도착 칸 모두 포함해서 셈).
#
# [4] 의사코드
#   dist[start] = 1
#   push start
#   while queue:
#       x, y = pop
#       for 4방향 nx, ny:
#           if 범위 안 and maze==1 and dist==0:   # 아직 미방문
#               dist[nx][ny] = dist[x][y] + 1
#               push (nx, ny)
#   answer = dist[N-1][M-1]
#
# [5] 구현 시 주의
#   - 입력이 "101111"처럼 공백 없이 붙어 들어오므로 문자열을 한 글자씩 다룬다.
#   - dist 배열을 방문표시 겸용으로 사용(0이면 미방문).
#   - 방문 표시는 큐에 "넣는 순간" 한다. (꺼낼 때 하면 같은 칸이 중복으로 큐에 들어감)

# 4방향(상,하,좌,우) 이동 델타
DX = (-1, 1, 0, 0)
DY = (0, 0, -1, 1)


def solve(n, m, maze):
    # 1) 거리 배열(방문표시 겸용). 0 = 미방문
    dist = [[0] * m for _ in range(n)]

    # 2) BFS 준비: 시작칸 (0,0)을 큐에 넣고 거리 1로 표시
    queue = deque()
    queue.append((0, 0))
    dist[0][0] = 1

    # 3) BFS 본체
    while queue:
        x, y = queue.popleft()
        for d in range(4):
            nx, ny = x + DX[d], y + DY[d]
            # 4) 범위 밖 / 벽(0) / 이미 방문한 칸은 건너뜀
            if nx < 0 or ny < 0 or nx >= n or ny >= m:
                continue
            if maze[nx][ny] == 0:
                continue
            if dist[nx][ny] != 0:
                continue
            # 5) 한 칸 더 왔으니 거리 +1, 방문 표시 후 큐에 넣기
            dist[nx][ny] = dist[x][y] + 1
            queue.append((nx, ny))

    # 6) 도착칸 거리가 곧 최소 칸 수
    return dist[n - 1][m - 1]


def main():
    data = sys.stdin.read().split()
    n = int(data[0])
    m = int(data[1])
    # 미로 줄들은 data[2:]에 문자열로 들어온다 (예: "101111")
    maze = [[int(ch) for ch in data[2 + i]] for i in range(n)]
    print(solve(n, m, maze))


if __name__ == "__main__":
    main()
