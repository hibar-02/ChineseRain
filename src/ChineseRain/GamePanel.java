package ChineseRain;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {

    private ArrayList<FallingWord> words = new ArrayList<>();
    private JTextField inputField;
    private Thread gameThread;

    public GamePanel() {
        setLayout(null);

        // 게임 시작 시 3개 떨어뜨리기
        for (int i = 0; i < 3; i++) {
            String[] wm = Words.getRandomWord();
            int x = 50 + i * 80;
            words.add(new FallingWord(wm[0], wm[1], x));
        }

        // input field
        inputField = new JTextField();
        inputField.setBounds(50, 600, 400, 30);
        add(inputField);

        // 입력 엔터처리
        inputField.addActionListener(e -> {
            String answer = inputField.getText().trim();

            for (FallingWord fw : words) {
                if (fw.checkAnswer(answer)) {
                    // 단어가 사라지면 종료
                    if (Words.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "모든 단어를 맞추셨습니다! 게임 종료!");
                        System.exit(0);
                    }
                    fw.reset(fw.getX());
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
        Iterator<FallingWord> it = words.iterator();

        while (it.hasNext()) {
            FallingWord fw = it.next();
            fw.update();

            // 화면 벗어나면
            if (fw.isOutOfScreen(getHeight())) {

                if (Words.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "모든 단어를 맞추셨습니다! 게임 종료!");
                    System.exit(0);
                }

                fw.reset(fw.getX());
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
