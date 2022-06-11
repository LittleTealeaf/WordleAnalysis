import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    public static List<String> WORDS;

    static {
        try {
            WORDS = Files.readAllLines(new File("./res/words.txt").toPath());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Game game = new Game(); System.out.println(game.getAnswer()); System.out.println(game.isValidWord("adieu")); System.out.println(game.isValidWord("rodeo"));
    }

    private final String answer;

    private final List<Guess> guesses = new LinkedList<>();

    public Game() {
        answer = WORDS.get((int) (WORDS.size() * Math.random()));
    }

    public String getAnswer() {
        return answer;
    }

    public void guess(String word) {

        Result[] results = new Result[word.length()];

        List<Character> stack = answer.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                results[i] = Result.CORRECT;
            } else if (stack.contains(word.charAt(i))) {
                results[i] = Result.PARTIAL; stack.remove((Character) word.charAt(i));
            }
        }

        guesses.add(new Guess(word, results));
    }

    public boolean isValidWord(String word) {

        return guesses.parallelStream().noneMatch(guess -> {

            List<Character> stack = word.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

            for (int i = 0; i < guess.word().length(); i++) {

                switch (guess.results()[i]) {
                    case WRONG -> {
                        if (stack.contains(guess.word().charAt(i))) {
                            return true;
                        }
                    } case PARTIAL -> {
                        if (guess.word().charAt(i) == word.charAt(i)) {
                            return true;
                        } if (!stack.contains(guess.word().charAt(i))) {
                            return true;
                        } stack.remove((Character) guess.word().charAt(i));
                    } case CORRECT -> {
                        if (word.charAt(i) != guess.word().charAt(i)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        });
    }
}
