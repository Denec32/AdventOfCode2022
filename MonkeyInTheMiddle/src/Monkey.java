import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Monkey {
    public static int modulo;
    public static boolean isWorryManageable;
    private final Deque<Integer> items = new ArrayDeque<>();
    private final OperationType operationType;
    private final int operand;

    private int itemsInspected = 0;

    private Monkey trueMonkey;
    public int trueMonkeyId;
    private Monkey falseMonkey;
    public int falseMonkeyId;

    private final int divisibleBy;

    public Monkey(List<Integer> items, OperationType operationType, int operand, int divisibleBy) {
        this.items.addAll(items);

        this.operationType = operationType;
        this.operand = operand;

        this.divisibleBy = divisibleBy;
    }

    public void setMonkeys(Monkey trueMonkey, Monkey falseMonkey) {
        this.trueMonkey = trueMonkey;
        this.falseMonkey = falseMonkey;
    }

    public void makeTurn() {
        while(!items.isEmpty()) {
            int item = items.pollFirst();
            item = inspectItem(item);
            item = isWorryManageable ? lowerWorry(item) : item;
            decideWhomToThrow(item);
        }
    }

    private int inspectItem(int item) {
        itemsInspected++;
        BigInteger num = new BigInteger(String.valueOf(item));
        BigInteger bigOperand = new BigInteger(String.valueOf(operand));

        num = switch (operationType) {
            case Add -> num.add(bigOperand);
            case Multiply -> num.multiply(bigOperand);
            case Power -> num.multiply(num);
        };

        return num.mod(new BigInteger(String.valueOf(modulo))).intValue();
    }

    private int lowerWorry(int item) {
        return item / 3;
    }

    private void decideWhomToThrow(int item) {
        if(item % divisibleBy == 0) {
            trueMonkey.receiveItem(item);
        } else {
            falseMonkey.receiveItem(item);
        }
    }

    public void receiveItem(int item) {
        this.items.addLast(item);
    }

    public int getItemsInspected() {
        return itemsInspected;
    }
}
