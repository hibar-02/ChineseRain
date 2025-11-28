package ChineseRain;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    private ArrayList<FallingWord> words = new ArrayList<>();
    private JTextField inputField;
    private Thread gameThread;

    public GamePanel() {
        setLayout(null);

        // test word
        for (int i = 0; i < 5; i++) {
            String[] wm = Words.getRandomWord();
            int x = 50 + i * 80;
            words.add(new FallingWord(wm[0], wm[1], x));
        }

        // input field
        inputField = new JTextField();
        inputField.setBounds(50, 600, 400, 30);
        add(inputField);

        // answer check
        inputField = new JTextField();
        inputField.setBounds(50, 600, 400, 30);
        add(inputField);

        inputField.addActionListener(e -> {
            String answer = inputField.getText().trim();

            for (FallingWord fw : words) {
                if (fw.checkAnswer(answer)) {
                    fw.reset(fw.getX());
                    break;
                }
            }

            inputField.setText("");
        });

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
        for (FallingWord fw : words) {
            fw.update();

            if (fw.isOutOfScreen(getHeight())) {
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
