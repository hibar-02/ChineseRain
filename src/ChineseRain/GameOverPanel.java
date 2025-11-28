package ChineseRain;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    private int totalWords;
    private int correctWords;
    private Image EndImage;

    public GameOverPanel(int totalWords, int correctWords) {

        this.totalWords = totalWords;
        this.correctWords = correctWords;

        setLayout(new GridBagLayout());
        EndImage = new ImageIcon(getClass().getResource("/resources/picture/EndImage.jpeg")).getImage();
        JPanel box = new JPanel(new GridLayout(2, 1));
        box.setOpaque(false); // 박스 배경 투명

        JLabel title = new JLabel("게임 종료!", SwingConstants.CENTER);
        title.setFont(new Font("BM Jua", Font.PLAIN, 40));

        JLabel score = new JLabel(getScoreText(), SwingConstants.CENTER);
        score.setFont(new Font("BM Jua", Font.PLAIN, 26));

        box.add(title);
        box.add(score);

        add(box);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(EndImage, 0, 0, getWidth(), getHeight(), this); // 배경
    }

    private String getScoreText() {
        double accuracy = (totalWords == 0) ? 0 : (correctWords * 100.0 / totalWords);
        return "<html>"
                + "총 단어 수 : " + totalWords + "<br>"
                + "맞춘 단어 수 : " + correctWords + "<br>"
                + "정확도 : " + String.format("%.2f", accuracy) + "%<br>"
                + "</html>";
    }
}
