package programmers.DFS_BFS;

/*
 * 프로그래머스 - 네트워크
 * https://school.programmers.co.kr/learn/courses/30/lessons/43162
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - 컴퓨터 수 n: 1 ~ 200.
 *   - 연결 정보 computers는 n x n 인접 "행렬".
 *   - DFS로 모든 정점을 훑되, 각 정점에서 이웃을 확인할 때 행렬 한 줄(n칸)을 본다 -> O(n^2) = 4만. 매우 작다.
 *
 * [2] 알고리즘 유형 분류
 *   - "서로 연결된 컴퓨터 묶음의 개수" = 연결 요소(Connected Component) 개수 세기.
 *   - 아직 방문 안 한 컴퓨터에서 DFS를 시작해 도달 가능한 모든 컴퓨터를 한 네트워크로 방문표시.
 *     DFS를 새로 시작한 횟수가 곧 네트워크 개수.
 *
 * [3] 예제로 규칙 발견
 *   - n=3, [[1,1,0],[1,1,0],[0,0,1]] -> 0-1 연결, 2는 홀로 -> 네트워크 2개.
 *   - computers[i][j] == 1 이면 i와 j가 직접 연결(간선)이라는 뜻. (i==j는 자기 자신이라 무시해도 됨)
 *
 * [4] 의사코드
 *   count = 0
 *   for i in 0..n-1:
 *       if !visited[i]:
 *           DFS(i)      // i가 속한 네트워크 전체를 방문표시
 *           count++
 *   return count
 *   DFS(cur): visited[cur]=true; for j in 0..n-1: if computers[cur][j]==1 && !visited[j]: DFS(j)
 *
 * [5] 구현 시 주의
 *   - 인접 "행렬"이므로 이웃을 찾을 때 0..n-1을 전부 훑어 computers[cur][j]==1 인지 확인한다.
 *   - visited 없이는 무한히 왔다갔다 하므로 방문표시는 필수.
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class Network {

    int n;
    int[][] computers;
    boolean[] visited;

    public int solution(int n, int[][] computers) {
        // 1) DFS에서 공용으로 참조할 값 저장
        this.n = n;
        this.computers = computers;
        this.visited = new boolean[n];

        int count = 0;

        // 2) 모든 컴퓨터를 순회하며 "새 네트워크의 시작점" 찾기
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i);   // 3) i가 속한 네트워크를 통째로 방문표시
                count++;  // 4) 새 네트워크 하나 발견
            }
        }
        return count;
    }

    // 5) DFS: cur와 직접 연결된(값 1) 미방문 컴퓨터로 계속 퍼져나간다
    private void dfs(int cur) {
        visited[cur] = true;
        for (int j = 0; j < n; j++) {
            // 6) 인접 행렬이므로 한 줄(cur 행)을 훑어 연결 여부 확인
            if (computers[cur][j] == 1 && !visited[j]) {
                dfs(j);
            }
        }
    }

    public static void main(String[] args) {
        Network s = new Network();
        System.out.println(s.solution(3, new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}})); // 2
        System.out.println(s.solution(3, new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}})); // 1
    }
}
