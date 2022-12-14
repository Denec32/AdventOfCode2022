import java.util.ArrayList;
import java.util.List;

public class KeepAway {
    private final int numberOfRounds;
    private final List<Monkey> monkeys;

    KeepAway(int numberOfRounds) {
        monkeys = new ArrayList<>();
        this.numberOfRounds = numberOfRounds;
    }

    public void addMonkey(Monkey monkey) {
        monkeys.add(monkey);
    }

    public void startGame() {
        for(int i = 0; i < numberOfRounds; i++) {
            for (var monkey : monkeys) {
                monkey.makeTurn();
            }
        }
    }

    public List<Integer> getItemsInspected() {
        List<Integer> inspectedItems = new ArrayList<>();
        for (var monkey : monkeys) {
            inspectedItems.add(monkey.getItemsInspected());
        }
        return inspectedItems;
    }
}
