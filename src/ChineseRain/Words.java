package ChineseRain;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Words {
    private static List<String> wordList = new ArrayList<>();
    private static List<String> meaningList = new ArrayList<>();
    private static Random random = new Random();

    static {
        loadWords();
    }

    private static void loadWords() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("HSK1.txt"), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":"); // ← 구분자
                if (parts.length == 2) {
                    wordList.add(parts[0].trim());
                    meaningList.add(parts[1].trim());
                }
            }
            System.out.println("단어 로딩 완료: " + wordList.size() + "개");

        } catch (Exception e) {
            System.out.println("txt 불러오기 실패 → " + e.getMessage());
        }
    }

    // random 단어 반환
    public static String[] getRandomWord() {
        int idx = random.nextInt(wordList.size());
        return new String[] { wordList.get(idx), meaningList.get(idx) };
    }

    // 단어 제거
    public static void removeWord(String word, String meaning) {
        int idx = wordList.indexOf(word);
        if (idx != -1 && meaningList.get(idx).equals(meaning)) {
            wordList.remove(idx);
            meaningList.remove(idx);
        }
    }

    // 잔여 단어 확인
    public static boolean isEmpty() {
        return wordList.isEmpty();
    }
}
