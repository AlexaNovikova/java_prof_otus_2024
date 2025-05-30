package ru.otus.model;

public class GameResult {
    private static final String RESULT_PATTERN = "Respected: %s. There were examples: %d, correct answers: %d";

    private final Player player;
    private int total;
    private int rightAnswers;

    public GameResult(Player player) {
        this.player = player;
    }

    public void incrementRightAnswers(boolean mustIncremented) {
        total++;
        if (mustIncremented) {
            rightAnswers++;
        }
    }

    @Override
    public String toString() {
        return String.format(RESULT_PATTERN, player.getName(), total, rightAnswers);
    }
}
