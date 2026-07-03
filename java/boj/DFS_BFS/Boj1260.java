package boj.DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 백준 1260 - DFS와 BFS
 * https://www.acmicpc.net/problem/1260
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - 정점 N <= 1,000, 간선 M <= 10,000.
 *   - 인접 리스트로 그래프를 만들면 DFS/BFS 모두 O(N + M) -> 최대 1.1만 -> 매우 빠름.
 *
 * [2] 알고리즘 유형 분류
 *   - 그래프 탐색의 두 기본기: DFS(깊이 우선)와 BFS(너비 우선)를 그대로 구현.
 *   - DFS = "한 방향으로 갈 수 있는 데까지 파고들었다가 막히면 되돌아온다" (재귀/스택).
 *   - BFS = "현재 정점의 이웃을 전부 방문한 뒤 그 다음 층으로 넘어간다" (큐).
 *
 * [3] 예제로 규칙 발견
 *   - "번호가 작은 정점을 먼저 방문" 조건 -> 각 정점의 인접 리스트를 오름차순 정렬해두면
 *     자연스럽게 작은 번호부터 탐색하게 된다.
 *   - 예제1: 시작 1 -> DFS: 1 2 4 3 / BFS: 1 2 3 4
 *
 * [4] 의사코드
 *   그래프 = 인접 리스트, 각 리스트 오름차순 정렬
 *   DFS(v): visited[v]=true; 출력 v; for w in adj[v]: if !visited[w]: DFS(w)
 *   BFS(v): visited[v]=true; push v
 *           while queue: u=pop; 출력 u; for w in adj[u]: if !visited[w]: visited[w]=true; push w
 *
 * [5] 구현 시 주의
 *   - 간선은 양방향 -> a-b 입력마다 adj[a]에 b, adj[b]에 a 둘 다 추가.
 *   - 중복 간선이 있어도 visited 체크 덕분에 결과에 영향 없다.
 *   - 정점 번호가 1..N이므로 배열 크기를 N+1로 잡고 인덱스 0은 버린다.
 *   - DFS/BFS는 서로 다른 visited 배열을 써야 한다(두 번 탐색하므로).
 */
public class Boj1260 {

    static List<List<Integer>> adj; // 인접 리스트: adj.get(v) = v와 연결된 정점들
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 1) 첫 줄: 정점 수 N, 간선 수 M, 시작 정점 V
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());

        // 2) 인접 리스트 초기화 (1..N 사용 -> 크기 N+1)
        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        // 3) 간선 입력: 양방향이므로 양쪽 모두에 추가
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        // 4) "작은 번호 먼저" 조건을 위해 각 인접 리스트를 오름차순 정렬
        for (int i = 1; i <= n; i++) {
            Collections.sort(adj.get(i));
        }

        // 5) DFS 수행 (visited 새로 준비)
        visited = new boolean[n + 1];
        dfs(v);
        sb.append('\n');

        // 6) BFS 수행 (visited 다시 초기화)
        visited = new boolean[n + 1];
        bfs(v);

        System.out.print(sb);
    }

    // 7) DFS: 재귀로 구현. 방문 즉시 출력하고, 이웃 중 미방문을 파고든다.
    static void dfs(int cur) {
        visited[cur] = true;
        sb.append(cur).append(' ');
        for (int next : adj.get(cur)) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }

    // 8) BFS: 큐로 구현. 큐에서 꺼내며 출력하고, 미방문 이웃을 큐에 넣는다.
    static void bfs(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        visited[start] = true; // 큐에 넣는 순간 방문 표시 (중복 삽입 방지)
        queue.offer(start);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            sb.append(cur).append(' ');
            for (int next : adj.get(cur)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
    }
}
