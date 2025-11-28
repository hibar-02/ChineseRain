package ChineseRain;

import java.awt.*;

public class FallingWord {
    private String word;
    private String meaning;
    private int x, y;
    private int speed;
    private boolean active = true;

    public FallingWord(String word, String meaning, int x) {
        this.word = word;
        this.meaning = meaning;
        this.x = x;
        this.y = 0;
        this.speed = 1;
    }

    public void update() {
        if (active)
            y += speed;
    }

    public void draw(Graphics g) {
        if (!active)
            return;
        g.setFont(new Font("Noto Sans CJK", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString(word, x, y);
    }

    public boolean checkAnswer(String answer) {
        if (meaning.equals(answer)) {
            active = false;
            return true;
        }
        return false;
    }

    public boolean isOutOfScreen(int height) {
        return y > height;
    }

    public boolean isActive() {
        return active;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public int getX() {
        return x;
    }

    public void reset(int x) {
        String[] wm = Words.getRandomWord();
        this.word = wm[0];
        this.meaning = wm[1];
        this.x = x;
        this.y = 0;
        this.active = true;
    }
}
