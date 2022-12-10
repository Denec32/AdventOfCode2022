import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CathodeRayTube {

    int currentCycle = 0;
    int x = 1;
    int power = 0;

    StringBuilder image = new StringBuilder();

    public CathodeRayTube() {
        runCommands();
    }

    public int SolutionPartOne() {
        return power;
    }

    public void SolutionPartTwo() {
        printSprite();
    }

    private void runCommands() {
        String[] instructions = readInput();
        for (var instruction: instructions) {
            if(instruction.equals("noop")) {
                executeNoop();
            } else {
                int value = Integer.parseInt(instruction.substring(5));
                executeAddx(value);
            }
        }
    }

    private void printSprite() {
        for(int i = 0; i < image.length(); i++) {
            if(i % 40 == 0) {
                System.out.println();
            }
            if(i % 5 == 0) {
                System.out.print(" ");
            }
            System.out.print(image.charAt(i));
        }
    }

    private void executeAddx(int value) {
        nextCycle();
        nextCycle();
        x += value;
    }

    private void executeNoop() {
        nextCycle();
    }

    private void nextCycle() {
        if((currentCycle - 20) % 40 == 0) {
            power += currentCycle * x;
        }
        if(Math.abs(currentCycle % 40 - x) <= 1) {
            image.append('#');
        } else {
            image.append(' ');
        }
        currentCycle++;
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
