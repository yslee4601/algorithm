import sys
from collections import deque

# 백준 1260 - DFS와 BFS
# https://www.acmicpc.net/problem/1260
#
# [1] 제약조건 -> 시간복잡도 역산
#   - 정점 N <= 1,000, 간선 M <= 10,000.
#   - 인접 리스트로 만들면 DFS/BFS 모두 O(N + M) -> 최대 1.1만 -> 매우 빠름.
#
# [2] 알고리즘 유형 분류
#   - 그래프 탐색 두 기본기: DFS(깊이 우선, 재귀/스택)와 BFS(너비 우선, 큐)를 그대로 구현.
#   - DFS = 갈 수 있는 데까지 파고들다 막히면 되돌아옴.
#   - BFS = 현재 정점의 이웃을 전부 방문한 뒤 다음 층으로 넘어감.
#
# [3] 예제로 규칙 발견
#   - "번호가 작은 정점 먼저 방문" -> 각 인접 리스트를 오름차순 정렬해두면 자동 해결.
#   - 예제1: 시작 1 -> DFS: 1 2 4 3 / BFS: 1 2 3 4
#
# [4] 의사코드
#   graph[v] 오름차순 정렬
#   DFS(v): visited[v]=True; 기록 v; for w in graph[v]: if not visited[w]: DFS(w)
#   BFS(v): visited[v]=True; push v
#           while q: u=pop; 기록 u; for w in graph[u]: if not visited[w]: visited[w]=True; push w
#
# [5] 구현 시 주의
#   - 간선은 양방향 -> a,b 마다 graph[a]에 b, graph[b]에 a 둘 다 추가.
#   - 중복 간선이 있어도 visited 체크로 결과에 영향 없음.
#   - 정점 번호가 1..N이므로 리스트 크기를 N+1로 잡고 인덱스 0은 버린다.
#   - DFS는 재귀가 깊어질 수 있으니 setrecursionlimit로 여유를 준다(N=1000 대비).
#   - DFS/BFS는 각각 별도의 visited를 사용한다(탐색을 두 번 하므로).

sys.setrecursionlimit(10 ** 6)


def dfs(graph, start, visited, order):
    # 방문 즉시 기록하고, 미방문 이웃을 파고든다.
    visited[start] = True
    order.append(start)
    for nxt in graph[start]:
        if not visited[nxt]:
            dfs(graph, nxt, visited, order)


def bfs(graph, start, visited, order):
    q = deque()
    visited[start] = True  # 큐에 넣는 순간 방문 표시 (중복 삽입 방지)
    q.append(start)
    while q:
        cur = q.popleft()
        order.append(cur)
        for nxt in graph[cur]:
            if not visited[nxt]:
                visited[nxt] = True
                q.append(nxt)


def solve(n, v, edges):
    # 1) 인접 리스트 초기화 (1..N -> 크기 N+1)
    graph = [[] for _ in range(n + 1)]

    # 2) 간선 입력: 양방향이므로 양쪽 모두 추가
    for a, b in edges:
        graph[a].append(b)
        graph[b].append(a)

    # 3) "작은 번호 먼저" 조건을 위해 각 인접 리스트 오름차순 정렬
    for i in range(1, n + 1):
        graph[i].sort()

    # 4) DFS 결과
    dfs_visited = [False] * (n + 1)
    dfs_order = []
    dfs(graph, v, dfs_visited, dfs_order)

    # 5) BFS 결과 (visited 새로 준비)
    bfs_visited = [False] * (n + 1)
    bfs_order = []
    bfs(graph, v, bfs_visited, bfs_order)

    return dfs_order, bfs_order


def main():
    data = sys.stdin.read().split()
    idx = 0
    n = int(data[idx]); idx += 1
    m = int(data[idx]); idx += 1
    v = int(data[idx]); idx += 1
    edges = []
    for _ in range(m):
        a = int(data[idx]); idx += 1
        b = int(data[idx]); idx += 1
        edges.append((a, b))

    dfs_order, bfs_order = solve(n, v, edges)
    print(" ".join(map(str, dfs_order)))
    print(" ".join(map(str, bfs_order)))


if __name__ == "__main__":
    main()
