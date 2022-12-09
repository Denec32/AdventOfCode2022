import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RopeBridge {
    private final int GRID_SIZE = 550;
    public int SolutionPartOne() {
        String[] steps = readInput();

        boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
        Point h = new Point(GRID_SIZE / 2, GRID_SIZE / 2);
        Point t = new Point(GRID_SIZE / 2, GRID_SIZE / 2);
        grid[GRID_SIZE / 2][GRID_SIZE / 2] = true;

        for (var step : steps) {
            var command = step.split(" ");
            Point dir = chooseDirection(command[0].charAt(0));
            int moves = Integer.parseInt(command[1]);
            while(moves > 0) {
                h.x += dir.x;
                h.y += dir.y;
                if(!isNear(t, h)) {
                    followHead(t, h);
                    grid[t.x][t.y] = true;
                }
                moves--;
            }
        }
        return countVisited(grid);
    }
    public int SolutionPartTwo() {
        String[] steps = readInput();

        boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
        Point h = new Point(GRID_SIZE / 2, GRID_SIZE / 2);
        Point[] t = new Point[9];

        for (int i = 0; i < 9; i++) {
            t[i] = new Point(GRID_SIZE / 2, GRID_SIZE / 2);
        }

        grid[GRID_SIZE / 2][GRID_SIZE / 2] = true;

        for (var step : steps) {
            var command = step.split(" ");
            Point dir = chooseDirection(command[0].charAt(0));
            int moves = Integer.parseInt(command[1]);
            while(moves > 0) {
                h.x += dir.x;
                h.y += dir.y;

                if(!isNear(t[0], h)) {
                    followHead(t[0], h);
                }

                for (int i = 1; i < 9; i++) {
                    if(!isNear(t[i], t[i - 1])) {
                        followHead(t[i], t[i - 1]);
                    }
                }

                grid[t[8].x][t[8].y] = true;
                moves--;
            }
        }
        return countVisited(grid);
    }

    private void followHead(Point t, Point h) {
        Point dir = new Point(0, 0);
        if(h.x > t.x) {
            dir.x = 1;
        } else if(h.x < t.x) {
            dir.x = -1;
        }
        if(h.y > t.y) {
            dir.y = 1;
        } else if(h.y < t.y) {
            dir.y = -1;
        }

        t.x += dir.x;
        t.y += dir.y;
    }

    private boolean isNear(Point t, Point h) {
        return Math.abs(t.x - h.x) <= 1 && Math.abs(h.y - t.y) <= 1;
    }

    private Point chooseDirection(char s) {
        return switch (s) {
            case 'U' -> new Point(-1, 0);
            case 'L' -> new Point(0, -1);
            case 'R' -> new Point(0, 1);
            default -> new Point(1, 0);
        };
    }

    private int countVisited(boolean[][] grid) {
        int visited = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j]) {
                    visited++;
                }
            }
        }
        return visited;
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
