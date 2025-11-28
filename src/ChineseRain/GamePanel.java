package ChineseRain;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {

    private ArrayList<FallingWord> words = new ArrayList<>();
    private JTextField inputField;
    private Thread gameThread;
    private long lastSpawnTime = 0;
    private int spawnInterval = 1500; // 1.5초마다 단어 생성
    private int maxWords = 4; // 화면에 동시에 4개까지만

    private void addNewWord() {
        if (Words.isEmpty())
            return;

        String[] wm = Words.getRandomWord();

        int x = (int) (Math.random() * (getWidth() - 100)); // 화면 전체 랜덤 X위치
        words.add(new FallingWord(wm[0], wm[1], x));
    }

    public GamePanel() {
        setLayout(null);
        setBackground(Color.RED);

        inputField = new JTextField();
        inputField.setBounds(50, 600, 400, 30);
        add(inputField);

        inputField.addActionListener(e -> {
            String answer = inputField.getText().trim();

            for (FallingWord fw : words) {
                if (fw.checkAnswer(answer)) {
                    words.remove(fw);
                    break;
                }
            }
            inputField.setText("");
        });

        // 게임 스레드 시작
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true) {
            updateWords(); // y좌표 증가
            repaint(); // 화면 갱신

            try {
                Thread.sleep(30);
            } catch (Exception ignored) {
            }
        }
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (FallingWord fw : words)
            fw.draw(g); // 단어 그리기
    }
}
