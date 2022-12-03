import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RucksackReorganization {

    public int SolutionPartOne() {
        String[] rucksacks = readInput();

        int sum = 0;
        for(var rucksack : rucksacks) {
            int[] compartment1 = new int[256];
            int[] compartment2 = new int[256];
            for(int i = 0; i < rucksack.length(); i++) {
                if(i < rucksack.length() / 2) {
                    compartment1[rucksack.charAt(i)]++;
                } else {
                    compartment2[rucksack.charAt(i)]++;
                }
            }
            for(int i = 0; i < compartment1.length; i++) {
                if (compartment1[i] > 0 && compartment2[i] > 0)  {
                    sum += countPriority((char)i);
                }
            }
        }
        return sum;
    }

    public int SolutionPartTwo() {
        String[] rucksacks = readInput();

        int sum = 0;

        for(int i = 0; i < rucksacks.length; i += 3) {
            HashMap<Character, Integer> map = new HashMap<>();
            countCharacters(map, rucksacks[i]);
            countCharacters(map, rucksacks[i + 1]);
            countCharacters(map, rucksacks[i + 2]);
            for(var entry : map.entrySet()) {
                if(entry.getValue() == 3) {
                    sum += countPriority(entry.getKey());
                }

            }
        }
        return sum;
    }

    /**
     * Adds to map a letter if it appeared once
     * @param map List of letters
     * @param rucksack String that contains letters
     */
    private void countCharacters(HashMap<Character, Integer> map, String rucksack) {
        int[] letters = new int[256];
        for (int i = 0; i < rucksack.length(); i++) {
            letters[rucksack.charAt(i)]++;
        }
        for (int i = 0; i < letters.length; i++) {
            if(letters[i] > 0) {
                if (map.containsKey((char)i)) {
                    map.replace((char)i, map.get((char)i) + 1);
                } else {
                    map.put((char)i, 1);
                }
            }
        }
    }

    /**
     * Count priority of a letter
     * @param symbol
     * @return Priority of a letter
     */
    private int countPriority(char symbol) {
        if(symbol > 'Z') {
            return symbol - 'a' + 1;
        } else {
            return symbol - 'A' + 27;
        }
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
