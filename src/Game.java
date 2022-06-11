import java.io.File;
import java.nio.file.Files;
import java.util.List;


public class Game {

    public static List<String> WORDS;

    static {
        try {
            WORDS = Files.readAllLines(new File("./res/words.txt").toPath());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.getWord());
    }

    private String word;

    public Game() {
        word = WORDS.get((int) (WORDS.size() * Math.random()));
    }

    public String getWord() {
        return word;
    }

}
