package ChineseRain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameOverPanel extends JPanel {

    private int totalWords;
    private int correctWords;
    private ArrayList<String> wrongWords;
    private Image EndImage;

    public GameOverPanel(int totalWords, int correctWords, ArrayList<String> wrongWords) {

        this.totalWords = totalWords;
        this.correctWords = correctWords;
        this.wrongWords = wrongWords;

        setLayout(new GridBagLayout());
        EndImage = new ImageIcon(getClass().getResource("/resources/picture/EndImage.jpeg")).getImage();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // ★ 반투명 박스
        JPanel box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // 넉넉한 여백

        // 제목
        JLabel title = new JLabel("게임 종료!", SwingConstants.CENTER);
        title.setFont(new Font("BM Jua", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 점수
        JLabel score = new JLabel(getScoreText(), SwingConstants.CENTER);
        score.setFont(new Font("BM Jua", Font.PLAIN, 28));
        score.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 틀린 단어 HTML 변환
        StringBuilder sb = new StringBuilder("<html>👲错误👲<br><br>");
        for (String w : wrongWords) {
            sb.append("🥟 ").append(w).append("<br>");
        }
        sb.append("</html>");

        JLabel wrongLabel = new JLabel(sb.toString());
        wrongLabel.setFont(new Font("BM Jua", Font.PLAIN, 20));

        // ★ 오답 박스 (둥근 테두리)
        JPanel wrongPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 255, 255, 220));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        wrongPanel.setLayout(new BorderLayout());
        wrongPanel.setOpaque(false);
        wrongPanel.setPreferredSize(new Dimension(380, 230));
        wrongPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(wrongLabel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        wrongPanel.add(scroll);

        // 조립
        box.add(title);
        box.add(Box.createVerticalStrut(25));
        box.add(score);
        box.add(Box.createVerticalStrut(25));
        box.add(wrongPanel);

        add(box, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(EndImage, 0, 0, getWidth(), getHeight(), this);
    }

    private String getScoreText() {
        double accuracy = (totalWords == 0) ? 0 : (correctWords * 100.0 / totalWords);
        return "<html>"
                + "全部 : " + totalWords + "个<br>"
                + "正确 : " + correctWords + "个<br>"
                + "分数 : " + String.format("%.0f", accuracy) + "分<br>"
                + "</html>";
    }
}