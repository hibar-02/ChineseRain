package ChineseRain;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("누가누가 중국어를 잘 맞출까?");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new GamePanel());
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
