import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegolithReservoir {
    private final int SRC_ROW = 0;
    private final int SRC_COL = 500;
    private int ABYSS_ROW;

    public int SolutionPartOne() {
        ABYSS_ROW = -1;
        String[] input = readInput();

        char[][] map = fillMap(input);
        ABYSS_ROW++;
        int count = 0;


        while(dropSandIntoAbyss(map)) {
            count++;
        }

        //printMap(map);

        return count;
    }

    public int SolutionPartTwo() {
        ABYSS_ROW = -1;
        String[] input = readInput();

        char[][] map = fillMap(input);
        ABYSS_ROW += 2;
        drawBottom(map);

        int count = 0;


        while(!dropSandOnFloor(map)) {
            count++;
        }
        count++;
        //printMap(map);
        return count;
    }

    private boolean dropSandOnFloor(char[][] map) {
        int row = SRC_ROW;
        int col = SRC_COL;

        while(map[row + 1][col] == '.' || map[row + 1][col - 1] == '.' || map[row + 1][col + 1] == '.') {
            if(map[row + 1][col] == '.') {
                row++;
            } else if(map[row + 1][col - 1] == '.') {
                row++;
                col--;
            } else if(map[row + 1][col + 1] == '.') {
                row++;
                col++;
            }
        }
        map[row][col] = 'o';
        return row == SRC_ROW && col == SRC_COL;
    }

    private void drawBottom(char[][] map) {
        for(int col = 0; col < map[0].length; col++) {
            map[ABYSS_ROW][col] = '#';
        }
    }

    private boolean dropSandIntoAbyss(char[][] map) {
        int row = SRC_ROW + 1;
        int col = SRC_COL;

        while(row != ABYSS_ROW && (map[row + 1][col] == '.' || map[row + 1][col - 1] == '.' || map[row + 1][col + 1] == '.') ) {
            if(map[row + 1][col] == '.') {
                row++;
            } else if(map[row + 1][col - 1] == '.') {
                row++;
                col--;
            } else if(map[row + 1][col + 1] == '.') {
                row++;
                col++;
            }
        }
        map[row][col] = 'o';
        return row != ABYSS_ROW;
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
    private void printMap(char[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    private char[][] fillMap(String[] lines) {
        char[][] grid = new char[200][800];

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                grid[i][j] = '.';
            }
        }

        for(var line : lines) {
            var pivots = line.split(" -> ");

            for(int i = 1; i < pivots.length; i++) {
                var coordinates1 = pivots[i - 1].split(",");
                var coordinates2 = pivots[i].split(",");

                int row1 = Integer.parseInt(coordinates1[1]);
                int col1 = Integer.parseInt(coordinates1[0]);

                int row2 = Integer.parseInt(coordinates2[1]);
                int col2 = Integer.parseInt(coordinates2[0]);

                ABYSS_ROW = Math.max(ABYSS_ROW, Math.max(row1, row2));
                if(row1 == row2) {
                    drawHorizontalLine(grid, col1, col2, row1);
                } else {
                    drawVerticalLine(grid, row1, row2, col1);
                }
            }
        }

        grid[0][500] = '+';

        return grid;
    }

    private void drawVerticalLine(char[][] grid, int row1, int row2, int col) {
        if(row1 > row2) {
            drawVerticalLine(grid, row2, row1, col);
        } else {
            for(int row = row1; row <= row2; row++) {
                grid[row][col] = '#';

            }
        }
    }

    private void drawHorizontalLine(char[][] grid, int col1, int col2, int row) {
        if(col1 > col2) {
            drawHorizontalLine(grid, col2, col1, row);
        } else {
            for (int col = col1; col <= col2; col++) {
                grid[row][col] = '#';
            }
        }
    }
}
