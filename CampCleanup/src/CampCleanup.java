import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CampCleanup {
    public int SolutionPartOne() {
        String[] input = readInput();

        int result = 0;
        for (var pair : input) {
            String[] sections = pair.split(",");
            String[] firstPair = sections[0].split("-");
            String[] secondPair = sections[1].split("-");

            int first = Integer.parseInt(firstPair[0]);
            int second = Integer.parseInt(firstPair[1]);
            int third = Integer.parseInt(secondPair[0]);
            int fourth = Integer.parseInt(secondPair[1]);

            if(first <= third && second >= fourth || first >= third && second <= fourth) {
                result++;
            }

        }
        return result;
    }
    public int SolutionPartTwo() {
        String[] input = readInput();


        int result = 0;
        for (var pair : input) {
            String[] sections = pair.split(",");
            String[] firstPair = sections[0].split("-");
            String[] secondPair = sections[1].split("-");

            int first = Integer.parseInt(firstPair[0]);
            int second = Integer.parseInt(firstPair[1]);
            int third = Integer.parseInt(secondPair[0]);
            int fourth = Integer.parseInt(secondPair[1]);

            if(first >= third && first <= fourth ||  second >= third && second <= fourth ||
                    third >= first && third <= second ||  fourth >= first && fourth <= second) {

                result++;
            }

        }
        return result;
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
