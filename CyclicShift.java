import java.io.*;
import java.util.StringTokenizer;
public class CyclicShift {

    public static void main(String[] args) throws FileNotFoundException {
        FastReader scanner = new FastReader();
        PrintWriter out = new PrintWriter("output.txt");
        int n = scanner.nextInt();
        String s1 = scanner.next();
        String s2 = scanner.next();

        String concatenated = s1 + s1;
        int shift = findShift(concatenated, s2);

        out.println(shift);
        out.flush();
    }
    private static int findShift(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        int[] lps = computeLPS(pattern);

        int i = 0;  // индекс для text
        int j = 0;  // индекс для pattern

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;

                if (j == m) {
                    // Найдено вхождение
                    return i - j;
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        // Вхождение не найдено
        return -1;
    }

    private static int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() throws FileNotFoundException {
            br = new BufferedReader(new FileReader("input.txt"));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                if (st.hasMoreTokens()) {
                    str = st.nextToken("\n");
                } else {
                    str = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}