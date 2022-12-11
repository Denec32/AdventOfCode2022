import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonkeyInTheMiddle {

    private final int NUM_OF_ROUNDS1 = 20;
    private final int NUM_OF_ROUNDS2 = 10000;

    private List<Monkey> monkeys = new ArrayList<>();
    List<Integer> monkeyItems = new ArrayList<>();

    public MonkeyInTheMiddle() {
        Monkey.modulo = getMod();
    }

    private  int getMod() {
        var mod = 1;
        for (var item : monkeyItems) {
            mod *= item;
        }

        return mod;
    }

    public String SolutionPartOne() {
        monkeyItems = new ArrayList<>();
        monkeys = new ArrayList<>();
        initializeMonkeys();
        Monkey.isWorryManageable = true;

        for (int i = 1; i <= NUM_OF_ROUNDS1; i++) {
            for (var monkey : monkeys) {
                monkey.makeTurn();
            }
        }
        
        List<Integer> inspectedCount = new ArrayList<>();
        for (var monkey : monkeys) {
            inspectedCount.add(monkey.getItemsInspected());
        }

        Collections.sort(inspectedCount);

        int num1 = inspectedCount.get(inspectedCount.size() - 1);
        int num2 = inspectedCount.get(inspectedCount.size() - 2);
        BigInteger result = new BigInteger(String.valueOf(num1)).multiply(new BigInteger(String.valueOf(num2)));
        return result.toString();
    }

    public String SolutionPartTwo() {
        monkeyItems = new ArrayList<>();
        monkeys = new ArrayList<>();
        initializeMonkeys();
        Monkey.isWorryManageable = false;

        for (int i = 1; i <= NUM_OF_ROUNDS2; i++) {
            for (var monkey : monkeys) {
                monkey.makeTurn();
            }
        }

        List<Integer> inspectedCount = new ArrayList<>();
        for (var monkey : monkeys) {
            inspectedCount.add(monkey.getItemsInspected());
        }

        Collections.sort(inspectedCount);
        int num1 = inspectedCount.get(inspectedCount.size() - 1);
        int num2 = inspectedCount.get(inspectedCount.size() - 2);
        BigInteger result = new BigInteger(String.valueOf(num1)).multiply(new BigInteger(String.valueOf(num2)));
        return result.toString();
    }

    private void initializeMonkeys() {
        var input = readInput();
        List<String> monkey = new ArrayList<>();
        for(int i = 0; i < input.length; i++) {
            if(input[i].equals("")) {
                initializeMonkey(monkey);
                monkey = new ArrayList<>();
            } else {
                monkey.add(input[i]);
            }
        }
        initializeMonkey(monkey);
        addPairs();
    }

    private void addPairs() {
        for (var monkey : monkeys) {
            monkey.setMonkeys(monkeys.get(monkey.trueMonkeyId), monkeys.get(monkey.falseMonkeyId));
        }
    }

    private void initializeMonkey(List<String> monkey) {
        List<Integer> items = new ArrayList<>();

        String[] startingItems = monkey.get(1).split(", |: ");
        for (int i = 1; i < startingItems.length; i++) {
            items.add(Integer.parseInt(startingItems[i]));
        }
        String[] operation = monkey.get(2).strip().split(" ");

        OperationType operationType;
        int operand;

        if(operation[5].equals("old")) {
            operationType = OperationType.Power;
            operand = 0;
        } else {
            operationType = switch (operation[4]) {
                case "*" -> OperationType.Multiply;
                default -> OperationType.Add;
            };
            operand = Integer.parseInt(operation[5]);
        }
        String[] test = monkey.get(3).split(" ");
        int divisibleBy = Integer.parseInt(test[test.length - 1]);
        monkeyItems.add(divisibleBy);

        Monkey mnk = new Monkey(items, operationType, operand, divisibleBy);
        String[] ifTrue = monkey.get(4).split(" ");
        int trueMonkey = Integer.parseInt(ifTrue[ifTrue.length - 1]);

        String[] ifFalse = monkey.get(5).split(" ");
        int falseMonkey = Integer.parseInt(ifFalse[ifFalse.length - 1]);
        mnk.trueMonkeyId = trueMonkey;
        mnk.falseMonkeyId = falseMonkey;
        monkeys.add(mnk);
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
