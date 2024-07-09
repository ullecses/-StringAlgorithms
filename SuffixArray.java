import java.util.Arrays;
import java.util.Scanner;

public class SuffixArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        Integer[] suffixArray = buildSuffixArray(text);
        System.out.println(suffixArray.length);
        for (int i = 0; i < suffixArray.length; i++) {
            System.out.print((suffixArray[i] + 1) + " ");
        }
    }

    public static Integer[] buildSuffixArray(String text) {
        int n = text.length();
        Integer[] suffixArray = new Integer[n];
        int[] ranks = new int[n];
        int[] tempRanks = new int[n];

        // Инициализация начальных рангов
        for (int i = 0; i < n; i++) {
            suffixArray[i] = i;
            ranks[i] = text.charAt(i);
        }

        // Обновление рангов и сортировка суффиксов
        for (int k = 1; k < n; k <<= 1) {
            SuffixComparator comparator = new SuffixComparator(k, ranks);
            Arrays.sort(suffixArray, comparator);

            tempRanks[suffixArray[0]] = 0;
            for (int i = 1; i < n; i++) {
                tempRanks[suffixArray[i]] = tempRanks[suffixArray[i - 1]] +
                        (comparator.compare(suffixArray[i - 1], suffixArray[i]) < 0 ? 1 : 0);
            }

            System.arraycopy(tempRanks, 0, ranks, 0, n);
        }

        return suffixArray;
    }

    static class SuffixComparator implements java.util.Comparator<Integer> {
        private final int k;
        private final int[] ranks;

        SuffixComparator(int k, int[] ranks) {
            this.k = k;
            this.ranks = ranks;
        }

        public int compare(Integer a, Integer b) {
            int rankA = ranks[a];
            int rankB = ranks[b];
            if (rankA != rankB) {
                return Integer.compare(rankA, rankB);
            } else {
                int nextRankA = (a + k < ranks.length) ? ranks[a + k] : -1;
                int nextRankB = (b + k < ranks.length) ? ranks[b + k] : -1;
                return Integer.compare(nextRankA, nextRankB);
            }
        }
    }
}