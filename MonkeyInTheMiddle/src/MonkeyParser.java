import java.util.ArrayList;
import java.util.List;

public class MonkeyParser {
    static Monkey parseMonkey(List<String> monkeyStr) {
        List<Integer> items = new ArrayList<>();

        getItems(monkeyStr, items);
        String[] operation = monkeyStr.get(2).strip().split(" ");

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
        String[] test = monkeyStr.get(3).split(" ");
        int divisibleBy = Integer.parseInt(test[test.length - 1]);

        Monkey monkey = new Monkey(items, operationType, operand, divisibleBy);
        String[] ifTrue = monkeyStr.get(4).split(" ");
        int trueMonkey = Integer.parseInt(ifTrue[ifTrue.length - 1]);

        String[] ifFalse = monkeyStr.get(5).split(" ");
        int falseMonkey = Integer.parseInt(ifFalse[ifFalse.length - 1]);
        monkey.trueMonkeyId = trueMonkey;
        monkey.falseMonkeyId = falseMonkey;

        return monkey;
    }

    private static void getItems(List<String> monkeyStr, List<Integer> items) {
        String[] startingItems = monkeyStr.get(1).split(", |: ");
        for (int i = 1; i < startingItems.length; i++) {
            items.add(Integer.parseInt(startingItems[i]));
        }
    }
}
