import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistressSignal {

    public int SolutionPartOne() {
        String[] input = readInput();
        int count = 0;
        for(int i = 0; i < input.length; i+=3) {
            String left = input[i];
            String right = input[i + 1];
            if(checkOrder(left, right) != -1) {
                count += i / 3 + 1;
            }
        }

        return count;
    }

    public int SolutionPartTwo() {
        String[] input = readInput();
        int overall = 0;
        int beforeTwo = 0;
        int afterSix = 0;
        for(int i = 0; i < input.length; i++) {
            if(input[i].equals("")) {
                i++;
            }
            String packet = input[i];
            String p = (packet.split(","))[0];
            StringBuilder num = new StringBuilder();
            for(var ch : p.toCharArray()) {
                if(ch >= '0' && ch <= '9') {
                    num.append(ch);
                }
            }
            if(num.isEmpty()) {
                beforeTwo++;
            } else {
                int n = Integer.parseInt(num.toString());
                if (n < 2) {
                    beforeTwo++;
                } else if (n >= 6) {
                    afterSix++;
                }
            }
            overall++;
        }
        overall += 2;
        return (overall - afterSix) * (beforeTwo + 1);
    }

    private int checkOrder(String left, String right) {
        List<String> elementsLeft = parseToStringArray(left);
        List<String> elementsRight = parseToStringArray(right);
        int length = Math.min(elementsLeft.size(), elementsRight.size());
        for (int i = 0; i < length; i++) {
            String l = elementsLeft.get(i);
            String r = elementsRight.get(i);
            if(l.charAt(0) != '[' && r.charAt(0) != '[') {
                int lNum = Integer.parseInt(l);
                int rNum = Integer.parseInt(r);
                if(lNum > rNum) {
                    return -1;
                }
                if(lNum < rNum) {
                    return 1;
                }
            } else {
                if(l.charAt(0) != '[') {
                    l = '[' + l + ']';
                }
                if(r.charAt(0) != '[') {
                    r = '[' + r + ']';
                }
                int res = checkOrder(l, r);
                if(res != 0) {
                    return res;
                }
            }
        }
        return Integer.compare(elementsRight.size(), elementsLeft.size());
    }

    private List<String> parseToStringArray(String packet) {
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        if(hasBrackets(packet)) {
            packet = packet.substring(1, packet.length() - 1);
        }
        if(packet.equals("")) {
            //result.add("");
            return result;
        }
        for(int i = 0; i < packet.length(); i++) {
            if(packet.charAt(i) >= '0' && packet.charAt(i) <= '9') {
                sb.append(packet.charAt(i));
                if(packet.charAt(i) == '1' && i < packet.length() - 1 && packet.charAt(i + 1) == '0') {
                    sb.append("0");
                    i++;
                }
            } else if(packet.charAt(i) == '[') {
                int listStart = i;
                int brackets = 0;
                do {
                    if(packet.charAt(i) == '[') {
                        brackets++;
                    } else if(packet.charAt(i) == ']') {
                        brackets--;
                    }
                    i++;
                } while(brackets > 0);
                sb = new StringBuilder(packet.substring(listStart, i));
            }
            if(sb.length() != 0) {
                result.add(sb.toString());
                sb = new StringBuilder();
            }
        }

        return result;
    }

    private boolean hasBrackets(String packet) {
        return packet.charAt(0) == '[' &&
                packet.charAt(packet.length() - 1) == ']';
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
