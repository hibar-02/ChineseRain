package ChineseRain;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {

    private ArrayList<FallingWord> words = new ArrayList<>();
    private JTextField inputField;
    private Thread gameThread;
    private Image MainImage;
    private long lastSpawnTime = 0;
    private int spawnInterval = 1500; // 1.5초마다 단어 생성
    private int maxWords = 4; // 화면에 동시에 4개까지만

    private int totalWords = 0;
    private int correctWords = 0;

    // 타이머
    private int timeLimit = 20; // 60초
    private long startTime;

    // 생성자
    public GamePanel() {
        setLayout(null);
        MainImage = new ImageIcon(getClass().getResource("/resources/picture/MainImage.jpeg")).getImage();
        inputField = new JTextField();
        inputField.setBounds(50, 600, 400, 30);
        add(inputField);

        inputField.addActionListener(e -> {
            String answer = inputField.getText().trim();

            Iterator<FallingWord> it = words.iterator();

            while (it.hasNext()) {
                FallingWord fw = it.next();
                if (fw.checkAnswer(answer)) {
                    correctWords++;
                    it.remove();

                    break;
                }
            }
            inputField.setText("");
        });

        startTime = System.currentTimeMillis();

        // 게임 스레드 시작
        gameThread = new Thread(this);
        gameThread.start();
    }

    // 게임 루프
    @Override
    public void run() {
        long spawnCheck = System.currentTimeMillis();

        while (true) {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsed >= timeLimit) {
                endGame();
                return;
            }

            updateWords();
            repaint();

            long now = System.currentTimeMillis();

            if (now - spawnCheck >= 1000) {
                if (words.size() < maxWords)
                    addNewWord();
                spawnCheck = now;
            }

            try {
                Thread.sleep(20);
            } catch (Exception ignored) {
            }
        }
    }

    // 새로운 단어 추가
    private void addNewWord() {
        if (Words.isEmpty())
            return;

        String[] wm = Words.getRandomWord();

        int x = (int) (Math.random() * (getWidth() - 100)); // 화면 전체 랜덤 X위치
        words.add(new FallingWord(wm[0], wm[1], x));
        totalWords++;
    }

    // 단어 위치 업데이트
    private void updateWords() {
        long currentTime = System.currentTimeMillis();

        // 새 단어 생성 (시간 + 최대 숫자 제한)
        if (currentTime - lastSpawnTime > spawnInterval && words.size() < maxWords) {
            addNewWord();
            lastSpawnTime = currentTime;
        }

        // 단어 이동
        for (int i = 0; i < words.size(); i++) {
            FallingWord fw = words.get(i);
            fw.update();

            // 화면 아래로 떨어지면 제거
            if (fw.isOutOfScreen(getHeight())) {
                words.remove(i);
                i--;
            }
        }
    }

    // 게임 종료 처리
    private void endGame() {
        gameThread = null; // 쓰레드 정지

        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            frame.add(new GameOverPanel(totalWords, correctWords));
            frame.revalidate();
            frame.repaint();
        });
    }

    // 단어 그리기
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(MainImage, 0, 0, getWidth(), getHeight(), this);

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        int remaining = Math.max(0, timeLimit - (int) elapsed);

        g.setFont(new Font("BM Jua", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("남은 시간: " + remaining + "초", 20, 30);
        g.drawString("맞춘 단어: " + correctWords, 350, 30);

        for (FallingWord fw : words)
            fw.draw(g); // 단어 그리기
    }
}
