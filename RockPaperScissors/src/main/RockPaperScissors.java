import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RockPaperScissors {

    private enum Shape {
        Rock, Paper, Scissors
    }
    private enum RoundOutcome {
        Victory, Defeat, Draw

    }
    public int SolutionPartOne() {
        String[] rounds = readInput();
        int score = 0;
        for(String round : rounds) {
            score += countScore(getShape(round.charAt(0)), getShape(round.charAt(2)));
        }
        return score;
    }
    public int SolutionPartTwo() {
        String[] rounds = readInput();
        int score = 0;
        for(String round : rounds) {
            score += countScore(getShape(round.charAt(0)), getOutcome(round.charAt(2)));
        }
        return score;
    }

    /**
     * Find who will win
     * @param opp Your opponent shape
     * @param you Your shape
     * @return Round outcome
     */
    private RoundOutcome findRoundOutcome(Shape opp, Shape you) {
        if(opp == you) {
            return RoundOutcome.Draw;
        }
        switch (you) {
            case Rock -> {
                if(opp == Shape.Scissors) return RoundOutcome.Victory;
            }
            case Paper -> {
                if(opp == Shape.Rock) return RoundOutcome.Victory;
            }
            case Scissors -> {
                if(opp == Shape.Paper) return RoundOutcome.Victory;
            }
        }
        return RoundOutcome.Defeat;
    }

    /**
     * Count score that you will get by this outcome
     * @param outcome Selected outcome
     * @return Score
     */
    private int countRoundScore(RoundOutcome outcome) {
        return switch (outcome) {
            case Victory -> 6;
            case Defeat -> 0;
            case Draw -> 3;
        };
    }

    /**
     * Count score that you will get by selecting this shape
     * @param shape Chosen shape
     * @return Score
     */
    private int countShapeScore(Shape shape) {
        return switch (shape) {
            case Rock -> 1;
            case Paper -> 2;
            case Scissors -> 3;
        };
    }

    /**
     * Count score of a round by your opponent's shape and your shape
     * @param opp Opponent's shape
     * @param you Your shape
     * @return Round score
     */
    private int countScore(Shape opp, Shape you) {
        int roundScore = countRoundScore(findRoundOutcome(opp, you));
        int shapeScore = countShapeScore(you);

        return roundScore + shapeScore;
    }

    /**
     * Count score of a round by your opponent's shape and outcome of a round
     * @param opp Opponent's shape
     * @param outcome Round's outcome
     * @return Round score
     */
    private int countScore(Shape opp, RoundOutcome outcome) {
        return  countScore(opp, getShapeByOutcome(opp, outcome));
    }

    /**
     * Returns shape that will play out according to selected rules
     * @param shape shape that your opponent chose
     * @param outcome outcome that must happen
     * @return shape that will give stated result
     */
    private Shape getShapeByOutcome(Shape shape, RoundOutcome outcome) {
        return switch (outcome) {
            case Victory -> getWinningShape(shape);
            case Defeat -> getLosingShape(shape);
            default -> shape;
        };
    }

    /**
     * Find Shape that will win to a selected shape
     * @param shape shape you need to win
     * @return shape that will defeat selected shape
     */
    private Shape getWinningShape(Shape shape) {
        return switch (shape) {
            case Rock -> Shape.Paper;
            case Scissors -> Shape.Rock;
            case Paper -> Shape.Scissors;
        };
    }

    /**
     * Find Shape that will lose to a selected shape
     * @param shape shape you need to lose to
     * @return shape that will lose to selected shape
     */
    private Shape getLosingShape(Shape shape) {
        return switch (shape) {
            case Rock -> Shape.Scissors;
            case Scissors -> Shape.Paper;
            case Paper -> Shape.Rock;
        };
    }

    /**
     * Get shape by input symbol
     * @param symbol symbol you want to convert to Shape
     * @return Rock if A or X, Paper if B or Y, Scissors by default
     */
    private Shape getShape(char symbol) {
        return switch (symbol) {
            case 'A', 'X' -> Shape.Rock;
            case 'B', 'Y' -> Shape.Paper;
            default -> Shape.Scissors;
        };
    }

    /**
     * Get round symbol by input symbol
     * @param symbol symbol you want to convert to RoundOutcome
     * @return Defeat if X, Draw if Y, Victory by default
     */
    private RoundOutcome getOutcome(char symbol) {
        return switch (symbol) {
            case 'X' -> RoundOutcome.Defeat;
            case 'Y' -> RoundOutcome.Draw;
            default -> RoundOutcome.Victory;
        };
    }
    private String[] readInput() {
        List<String> input = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            String line = reader.readLine();

            while (line != null) {
                input.add(line);
                line = reader.readLine();
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return input.toArray(new String[0]);
    }
}
