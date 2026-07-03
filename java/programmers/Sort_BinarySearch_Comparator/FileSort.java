package programmers.Sort_BinarySearch_Comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 프로그래머스 - [3차] 파일명 정렬
 *
 * [1] 제약조건 -> 시간복잡도 역산
 *   - files <= 1,000개, 각 파일명 <= 100자.
 *   - 각 파일명 파싱 O(길이), 정렬 O(N log N) -> 1,000 * log -> 매우 작음.
 *
 * [2] 알고리즘 유형 분류
 *   - 문자열 파싱 + 안정 정렬(Stable Sort). HEAD/NUMBER/TAIL 분리 후 다중 기준 정렬.
 *
 * [3] 예제로 규칙 발견 (HEAD/NUMBER/TAIL)
 *   - foo9.txt        -> HEAD="foo", NUMBER="9",   TAIL=".txt"
 *   - foo010bar020.zip-> HEAD="foo", NUMBER="010", TAIL="bar020.zip"
 *   - F-15            -> HEAD="F-",  NUMBER="15",  TAIL=""
 *   정렬 기준: 1) HEAD 소문자 사전순  2) NUMBER 정수값  3) 동률이면 입력순 유지
 *
 * [4] 의사코드
 *   for each file:
 *       head = 앞에서부터 숫자 직전까지의 문자
 *       number = 그 뒤 연속 숫자(최대 5자리)
 *       (tail은 정렬에 불필요)
 *   stable sort by (head.toLowerCase(), Integer.parseInt(number))
 *
 * [5] 구현
 *   - 안정 정렬을 위해 Arrays.sort(Object[]) 사용(병합정렬 기반, stable).
 *   - 동률 시 입력 순서 유지가 보장됨.
 *
 * (제출 시) 클래스명을 Solution 으로 변경.
 */
public class FileSort {

    private static class FileEntry {
        String original;
        String head;    // 비교용(소문자)
        int number;

        FileEntry(String original, String head, int number) {
            this.original = original;
            this.head = head;
            this.number = number;
        }
    }

    public String[] solution(String[] files) {
        FileEntry[] entries = new FileEntry[files.length];

        for (int i = 0; i < files.length; i++) {
            String f = files[i];
            int idx = 0;
            int len = f.length();

            // HEAD: 숫자가 나오기 전까지
            StringBuilder head = new StringBuilder();
            while (idx < len && !Character.isDigit(f.charAt(idx))) {
                head.append(f.charAt(idx));
                idx++;
            }

            // NUMBER: 연속 숫자 최대 5자리
            StringBuilder num = new StringBuilder();
            while (idx < len && Character.isDigit(f.charAt(idx)) && num.length() < 5) {
                num.append(f.charAt(idx));
                idx++;
            }

            entries[i] = new FileEntry(f, head.toString().toLowerCase(),
                    Integer.parseInt(num.toString()));
        }

        // 안정 정렬: HEAD 사전순 -> NUMBER 값 -> (동률 시 입력순 유지)
        Arrays.sort(entries, (a, b) -> {
            int cmp = a.head.compareTo(b.head);
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(a.number, b.number);
        });

        List<String> result = new ArrayList<>();
        for (FileEntry e : entries) {
            result.add(e.original);
        }
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) {
        FileSort s = new FileSort();
        String[] files = {
                "img12.png", "img10.png", "img02.png", "img1.png",
                "IMG01.GIF", "img2.JPG"
        };
        System.out.println(Arrays.toString(s.solution(files)));
        // [img1.png, IMG01.GIF, img02.png, img2.JPG, img10.png, img12.png]
    }
}
