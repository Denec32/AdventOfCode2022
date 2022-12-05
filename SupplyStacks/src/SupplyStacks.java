import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SupplyStacks {

    private static final int CARGO_WIDTH = 8;
    private static final int CARGO_HEIGHT = 7;
    private static final int SPACE_BETWEEN_CHARACTERS = 4;
    private static final int COMMAND_START_LINE = 10;

    public String SolutionPartOne() {
        String[] input = readInput();
        List<Stack<Character>> cargo = fillStack(input);

        for (int i = COMMAND_START_LINE; i < input.length; i++) {
            executeCommand(cargo, input[i]);
        }

        return getTopCrates(cargo);
    }

    private void executeCommand(List<Stack<Character>> cargo, String input) {
        String[] procedure = input.split(" ");

        int moves = Integer.parseInt(procedure[1]);
        int index1 = Integer.parseInt(procedure[3]) - 1;
        int index2 = Integer.parseInt(procedure[5]) - 1;

        useCrateMover9000(cargo, moves, index1, index2);
    }

    public String SolutionPartTwo() {
        String[] input = readInput();
        List<Stack<Character>> cargo = fillStack(input);

        for (int i = COMMAND_START_LINE; i < input.length; i++) {
            String[] procedure = input[i].split(" ");

            int moves = Integer.parseInt(procedure[1]);
            int index1 = Integer.parseInt(procedure[3]) - 1;
            int index2 = Integer.parseInt(procedure[5]) - 1;

            useCrateMover9001(cargo, moves, index1, index2);
        }

        return getTopCrates(cargo);
    }

    private void useCrateMover9000(List<Stack<Character>> cargo, int moves, int index1, int index2) {
        while(moves > 0) {
            Character buff = cargo.get(index1).pop();
            cargo.get(index2).push(buff);
            moves--;
        }
    }

    private void useCrateMover9001(List<Stack<Character>> cargo, int moves, int index1, int index2) {
        Stack<Character> buffer = new Stack<>();

        while(moves > 0) {
            buffer.push(cargo.get(index1).pop());
            moves--;
        }

        while (!buffer.isEmpty()) {
            cargo.get(index2).push(buffer.pop());
        }
    }

    private List<Stack<Character>> fillStack(String[] input) {
        List<Stack<Character>> cargo = new ArrayList<>();
        for(int i = 0; i <= CARGO_WIDTH; i++) {
            cargo.add(new Stack<>());
        }

        for(int i = CARGO_HEIGHT; i >= 0; i--) {
            for(int j = 1; j < input[i].length(); j += SPACE_BETWEEN_CHARACTERS) {
                if(input[i].charAt(j) != ' ') {
                    cargo.get((j - 1) / SPACE_BETWEEN_CHARACTERS).add(input[i].charAt(j));
                }
            }
        }
        return cargo;
    }

    private String getTopCrates(List<Stack<Character>> cargo) {
        StringBuilder result = new StringBuilder();
        for (var stack : cargo) {
            result.append(stack.peek());
        }
        return result.toString();
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
