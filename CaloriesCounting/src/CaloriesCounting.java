import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CaloriesCounting {

    public Integer SolutionPartOne() {
        var elvesCalories = getSortedElvesCalories();
        return elvesCalories[elvesCalories.length - 1];
    }

    public Integer SolutionPartTwo() {
        var elvesCalories = getSortedElvesCalories();
        int n = elvesCalories.length;
        return elvesCalories[n - 1] + elvesCalories[n - 2] + elvesCalories[n - 3];

    }

    private Integer[] getSortedElvesCalories() {
        var elvesCalories = getElvesCalories();
        Arrays.sort(elvesCalories);
        return elvesCalories;
    }

    private Integer[] getElvesCalories() {
        String[] args = readInput();
        List<Integer> elves = new ArrayList<>();
        int current = 0;
        for(var arg : args) {
            if(!arg.equals("")) {
                current += Integer.parseInt(arg);
            } else {
                elves.add(current);
                current = 0;
            }
        }
        return elves.toArray(new Integer[0]);
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
