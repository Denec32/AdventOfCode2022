import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TuningTrouble {

    public int SolutionPartOne() {
        return findMarker(4);
    }

    public int SolutionPartTwo() {
        return findMarker(14);
    }

    private int findMarker(int markerLength) {

        String sequence = readInput();
        StringBuilder currentSequence = new StringBuilder(sequence.substring(0, markerLength - 1));

        for(int i = markerLength - 1; i < sequence.length(); i++) {
            currentSequence.append(sequence.charAt(i));

            if(isMarker(currentSequence.toString())) {
                return i + 1;
            }
            currentSequence.delete(0, 1);
        }
        return -1;
    }

    private boolean isMarker(String sequence) {
        int[] charCounter = new int['z' - 'a' + 1];
        for(var c : sequence.toCharArray()) {
            charCounter[c - 'a']++;
            if(charCounter[c - 'a'] >= 2) {
                return false;
            }
        }

        return true;
    }

    private String readInput() {
        String input = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            input = reader.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
