package boj.DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 백준 2667 - 단지번호붙이기
 * https://www.acmicpc.net/problem/2667
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - N <= 25 이므로 칸은 최대 25*25 = 625개.
 *   - 각 칸을 한 번씩만 방문하는 탐색이면 O(N^2) = 625 -> 매우 작다.
 *
 * [2] 알고리즘 유형 분류
 *   - "상하좌우로 연결된 1들의 덩어리(단지)"를 찾는 문제 = 연결 요소(Connected Component) 세기.
 *   - 격자를 훑다가 아직 방문 안 한 집(1)을 만나면, 거기서 DFS로 연결된 모든 집을 한 덩어리로 묶고
 *     그 크기를 센다. 이 과정을 몇 번 반복했는지가 곧 단지 수다.
 *
 * [3] 예제로 규칙 발견
 *   - 7x7 지도 -> 단지 3개, 각 크기 7/8/9 (오름차순 정렬해서 출력).
 *   - 대각선은 연결로 치지 않으므로 이동 방향은 상하좌우 4방향만 사용한다.
 *
 * [4] 의사코드
 *   count = 0
 *   for 모든 칸 (i, j):
 *       if map==1 && 미방문:
 *           size = DFS(i, j)      // 연결된 1을 모두 방문표시하며 개수 셈
 *           단지크기 목록에 size 추가
 *   단지 수 출력, 크기 목록 오름차순 정렬 후 하나씩 출력
 *   DFS(x, y): visited=true; cnt=1; for 4방향: 조건 맞으면 cnt += DFS(...)
 *
 * [5] 구현 시 주의
 *   - 입력이 "0110100"처럼 붙어서 들어오므로 문자 하나씩 파싱.
 *   - visited는 "이미 어떤 단지에 포함되었다"는 표시. 재방문하면 크기가 중복 계산되므로 필수.
 *   - N=25면 재귀 깊이가 최대 625라 자바 기본 스택으로 충분(문제없음).
 */
public class Boj2667 {

    static int n;
    static int[][] map;
    static boolean[][] visited;

    // 상하좌우 4방향
    static final int[] DX = {-1, 1, 0, 0};
    static final int[] DY = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 1) 지도 크기 N
        n = Integer.parseInt(br.readLine().trim());

        // 2) 지도 저장 (붙어있는 문자열 -> 한 글자씩 숫자로)
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < n; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        visited = new boolean[n][n];
        List<Integer> sizes = new ArrayList<>(); // 각 단지의 집 수

        // 3) 격자 전체를 훑으며 "새 단지의 시작점"을 찾는다
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    int size = dfs(i, j); // 4) 이 칸에서 연결된 단지 하나를 통째로 탐색
                    sizes.add(size);
                }
            }
        }

        // 5) 단지 크기를 오름차순 정렬
        Collections.sort(sizes);

        // 6) 출력: 첫 줄에 단지 수, 이후 줄마다 각 단지 크기
        StringBuilder sb = new StringBuilder();
        sb.append(sizes.size()).append('\n');
        for (int size : sizes) {
            sb.append(size).append('\n');
        }
        System.out.print(sb);
    }

    // 7) DFS: (x,y)에서 시작해 상하좌우로 연결된 1을 모두 방문표시하고, 방문한 집의 개수를 반환
    static int dfs(int x, int y) {
        visited[x][y] = true;
        int count = 1; // 지금 칸 1개 포함

        for (int d = 0; d < 4; d++) {
            int nx = x + DX[d];
            int ny = y + DY[d];

            // 8) 범위 안 && 집(1) && 아직 미방문일 때만 이어서 탐색
            if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
            if (map[nx][ny] == 1 && !visited[nx][ny]) {
                count += dfs(nx, ny); // 이웃 단지의 크기를 누적
            }
        }
        return count;
    }
}
