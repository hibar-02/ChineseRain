import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("누가누가 중국어를 잘 맞출까?);
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GamePanel panel = new GamePanel();
        JTextField input = new JTextField();

        // 엔터 입력 → 단어 제거 시도
        input.addActionListener(e -> {
            String txt = input.getText().trim();
            panel.tryAnswer(txt);
            input.setText("");
        });

        add(panel, BorderLayout.CENTER);
        add(input, BorderLayout.SOUTH);

        setVisible(true);
    }
}
