import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeaconExclusionZone {

    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    private int resultingRow = 2_000_000;

    List<int[]> sections = new ArrayList<>();

    public void SolutionPartOne() {
        String[] input = new String[] {
                "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
                "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
                "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
                "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
                "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
                "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
                "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
                "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
                "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
                "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
                "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
                "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
                "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
                "Sensor at x=20, y=1: closest beacon is at x=15, y=3"
        };

        input = readInput();
        List<Point[]> pairs = new ArrayList<>();
        for(var entry : input) {
            String[] entrySplit = entry.split(" ");
            // Get coordinates
            int sensorX = getNum(entrySplit[2]);
            int sensorY = getNum(entrySplit[3]);
            int beaconX = getNum(entrySplit[8]);
            int beaconY = getNum(entrySplit[9]);

            Point sensor = new Point(sensorX, sensorY);
            Point beacon = new Point(beaconX, beaconY);

            pairs.add(new Point[] {sensor, beacon});
        }

        for(var pair : pairs) {
            operate(pair);
        }
        System.out.println();

        for (int[] ints : sections) {
            ints[0] -= min;
            ints[1] -= min;
            System.out.println(ints[0] + ":" + ints[1]);
        }
        boolean[] line = new boolean[max - min + 1];

        for(var section : sections) {
            for(int i = section[0]; i <= section[1]; i++) {
                line[i] = true;
            }
        }

        for(var pair : pairs) {
            if(pair[0].y == resultingRow) {
                line[pair[0].x - min] = false;
            }
            if(pair[1].y == resultingRow) {
                line[pair[1].x - min] = false;

            }
        }
        int count = 0;
        for(var el : line) {
            if(el) {
                count++;
            }
        }
        System.out.println(count);

    }

    public void SolutionPartTwo() {
        String[] input = new String[] {
                "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
                "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
                "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
                "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
                "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
                "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
                "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
                "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
                "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
                "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
                "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
                "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
                "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
                "Sensor at x=20, y=1: closest beacon is at x=15, y=3"
        };

        //input = readInput();
        int ceiling = 20;//4_000_000;

        for(resultingRow = 0; resultingRow <= ceiling; resultingRow++) {

            List<Point[]> pairs = new ArrayList<>();
            sections = new ArrayList<>();
            for(var entry : input) {
                String[] entrySplit = entry.split(" ");
                // Get coordinates
                int sensorX = getNum(entrySplit[2]);
                int sensorY = getNum(entrySplit[3]);
                int beaconX = getNum(entrySplit[8]);
                int beaconY = getNum(entrySplit[9]);

                Point sensor = new Point(sensorX, sensorY);
                Point beacon = new Point(beaconX, beaconY);

                pairs.add(new Point[] {sensor, beacon});
            }

            for(var pair : pairs) {
                operate(pair);
            }

            for (int[] section : sections) {

                section[0] = Math.max(0, section[0]);
                section[1] = Math.min(ceiling, section[1]);
            }
        }
    }

    private void operate(Point[] pair) {
        Point sensor = pair[0];
        Point beacon = pair[1];

        int manhattanDistance = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);

        int low = sensor.y - manhattanDistance;
        int high = sensor.y + manhattanDistance;

        if(resultingRow < low || resultingRow > high) {
            return;
        }

        int distance = Math.abs(sensor.y - resultingRow);

        int lx = sensor.x - (manhattanDistance - distance);
        int rx = sensor.x + (manhattanDistance - distance);
        sections.add(new int[] {lx, rx});
        min = Math.min(min, lx);
        max = Math.max(max, rx);
    }

    private int getNum(String entrySplit) {
        StringBuilder sb = new StringBuilder();
        boolean minus = false;
        for(var ch : entrySplit.toCharArray()) {
            if(ch >= '0' && ch <= '9') {
                sb.append(ch);
            }
            if(ch == '-') {
                minus = true;
            }
        }
        int num = Integer.parseInt(sb.toString());
        return minus ? -num : num;
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
