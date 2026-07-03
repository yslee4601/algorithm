package boj.DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 백준 2178 - 미로 탐색
 * https://www.acmicpc.net/problem/2178
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - N, M <= 100 이므로 칸(정점)은 최대 100*100 = 10,000개.
 *   - 격자에서 한 칸은 상하좌우 4개의 이웃만 가지므로 간선도 최대 4만개 수준.
 *   - 모든 칸을 한 번씩만 방문하는 BFS면 O(N*M) = 1만 -> 매우 빠름.
 *
 * [2] 알고리즘 유형 분류
 *   - "지나야 하는 최소 칸 수" = 시작점~도착점 최단거리.
 *   - 모든 이동 비용이 1(한 칸 이동)로 같음 -> 가중치 1의 최단거리 문제.
 *   - 가중치가 모두 1이면 BFS가 곧 최단거리다. (DFS는 최단을 보장하지 못함)
 *
 * [3] 예제로 규칙 발견
 *   - 4x6 미로에서 (1,1)에서 출발 -> 한 칸씩 인접한 1로만 이동 -> (4,6) 도착.
 *   - BFS는 "출발점에서 거리가 가까운 칸부터" 파도처럼 퍼진다.
 *     시작 칸의 거리를 1로 두면, 도착 칸에 처음 도달했을 때의 거리값이 곧 답(칸 수).
 *   - 예제1 답 15, 예제2 답 9. (시작/도착 칸을 모두 포함해서 셈)
 *
 * [4] 의사코드
 *   dist[start] = 1
 *   queue.push(start)
 *   while queue not empty:
 *       cur = queue.pop()
 *       for 4방향 (nx, ny):
 *           if 범위 안 && maze==1 && dist==0(아직 미방문):
 *               dist[nx][ny] = dist[cur] + 1
 *               queue.push((nx,ny))
 *   answer = dist[N][M]
 *
 * [5] 구현 시 주의
 *   - 입력이 "101111"처럼 공백 없이 붙어서 들어오므로 문자 하나씩 파싱한다.
 *   - dist 배열을 visited(방문표시) 겸용으로 쓴다. 0이면 미방문, 0이 아니면 이미 방문 + 거리 기록.
 *   - 방문 표시는 "큐에 넣는 순간" 해야 한다. (꺼낼 때 하면 같은 칸이 큐에 여러 번 들어가 비효율/오류)
 */
public class Boj2178 {

    // 4) 4방향(상,하,좌,우) 이동을 dx/dy 배열로 정의해두면 for 한 번으로 처리 가능
    static final int[] DX = {-1, 1, 0, 0};
    static final int[] DY = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 1) 입력 읽기: 첫 줄 N M
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 2) 미로 저장: 붙어있는 문자열을 한 글자씩 숫자로 변환
        int[][] maze = new int[n][m];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                maze[i][j] = line.charAt(j) - '0'; // 문자 '1' - '0' = 정수 1
            }
        }

        // 3) 거리 배열(방문표시 겸용). 0 = 아직 방문 안 함
        int[][] dist = new int[n][m];

        // 5) BFS 준비: 시작칸 (0,0)을 큐에 넣고 거리 1로 표시
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        dist[0][0] = 1;

        // 6) BFS 본체: 큐가 빌 때까지 가까운 칸부터 퍼져나감
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + DX[d];
                int ny = y + DY[d];

                // 7) 범위 체크 -> 이동 가능한 칸(1)인지 -> 아직 방문 안 했는지 순서로 확인
                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue; // 미로 밖
                if (maze[nx][ny] == 0) continue;                       // 벽
                if (dist[nx][ny] != 0) continue;                       // 이미 방문

                // 8) 한 칸 더 왔으니 거리 = 현재칸 거리 + 1, 방문 표시 후 큐에 넣기
                dist[nx][ny] = dist[x][y] + 1;
                queue.offer(new int[]{nx, ny});
            }
        }

        // 9) 도착칸 (N-1, M-1)의 거리가 곧 최소 칸 수
        System.out.println(dist[n - 1][m - 1]);
    }
}
