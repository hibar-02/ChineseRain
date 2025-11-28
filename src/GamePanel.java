import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements ActionListener {

    List<FallingWord> fallingWords = new ArrayList<>();
    java.util.List<Word> dictionary = new ArrayList<>();

    Timer timer;
    int lives = 3;
    int score = 0;

    public GamePanel() {
        setBackground(Color.WHITE);
        loadWords();
        timer = new Timer(30, this);
        timer.start();

        // 초기 5개 생성
        for (int i = 0; i < 5; i++) {
            spawnWord();
        }
    }

    // 단어 데이터 로딩 (나중에 CSV로 확장 가능)
    public void loadWords() {
        dictionary.add(new Word("苹果", "사과"));
        dictionary.add(new Word("朋友", "친구"));
        dictionary.add(new Word("学校", "학교"));
        dictionary.add(new Word("喜欢", "좋아하다"));
        dictionary.add(new Word("电影", "영화"));
    }

    // 랜덤 단어 생성
    public void spawnWord() {
        Word w = dictionary.get((int)(Math.random() * dictionary.size()));
        int x = (int)(Math.random() * 400);
        int speed = 2 + (int)(Math.random() * 3);

        fallingWords.add(new FallingWord(w, x, speed));
    }

    // 매 프레임마다 실행되는 게임 루프
    @Override
    public void actionPerformed(ActionEvent e) {
        Iterator<FallingWord> it = fallingWords.iterator();

        while (it.hasNext()) {
            FallingWord fw = it.next();
            fw.update();

            if (fw.y > getHeight()) { // 바닥 도달
                it.remove();
                lives--;
                if (lives <= 0) {
                    timer.stop();
                }
                spawnWord();
            }
        }

        // 화면 갱신
        repaint();
    }

    // 단어 제거 (정답 입력)
    public boolean tryAnswer(String input) {
        Iterator<FallingWord> it = fallingWords.iterator();

        while (it.hasNext()) {
            FallingWord fw = it.next();
            if (fw.getMeaning().equals(input)) {
                it.remove();
                score++;
                spawnWord();
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 단어 그리기
        for (FallingWord fw : fallingWords) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(fw.getChinese(), fw.x, fw.y);
        }

        // 점수/목숨 표시
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.BLACK);
        g.drawString("점수: " + score, 10, 20);
        g.drawString("목숨: " + lives, 10, 40);

        if (lives <= 0) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 120, 300);
        }
    }
}
