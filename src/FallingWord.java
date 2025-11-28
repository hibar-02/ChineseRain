public class FallingWord {
    Word word;
    int x;
    int y;
    int speed;

    public FallingWord(Word word, int x, int speed) {
        this.word = word;
        this.x = x;
        this.y = 0;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public String getChinese() {
        return word.chinese;
    }

    public String getMeaning() {
        return word.meaning;
    }
}
