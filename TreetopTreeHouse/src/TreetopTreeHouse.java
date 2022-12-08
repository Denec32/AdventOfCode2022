import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreetopTreeHouse {
    int[][] grid;
    public int SolutionPartOne() {
        grid = readInput();

        int treeCount = grid.length * 2 + grid[0].length * 2 - 4;

        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                treeCount += checkVisibility(grid, i, j) ? 1 : 0;
            }
        }

        return treeCount;
    }

    public int SolutionPartTwo() {
        grid = readInput();

        int max = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                max = Math.max(max, scenicScore(grid, i, j));
            }
        }

        return max;
    }

    private int scenicScore(int[][] grid, int x, int y) {
        int[][] dirs = new int[][] {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
        int score = 1;
        for (int[] dir : dirs) {
            score *= countDirScore(grid, x, y, dir);
        }
        return score;
    }

    private int countDirScore(int[][] grid, int x, int y, int[] dir) {
        int score = 0;
        int initialTree = grid[x][y];
        int maxTree = 0;
        x += dir[0];
        y += dir[1];
        while(x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
            if(maxTree == 0) {
                if (grid[x][y] < initialTree) {
                    score++;
                } else {
                    maxTree = grid[x][y];
                }
            } else {
                if(grid[x][y] > maxTree) {
                    maxTree = grid[x][y];
                    score++;
                }
            }
            x += dir[0];
            y += dir[1];
        }
        return score;
    }

    private boolean checkVisibility(int[][] grid, int x, int y) {
        int[][] dirs = new int[][] {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dir : dirs) {
            if (isSeen(grid, x, y, dir)) return true;
        }
        return false;
    }

    private boolean isSeen(int[][] grid, int x, int y, int[] dir) {
        int tree = grid[x][y];
        x += dir[0];
        y += dir[1];
        while(x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
            if(grid[x][y] >= tree) {
                break;
            }
            if(x == 0 || y == 0 || x == grid.length - 1 || y == grid[0].length - 1) {
                return true;
            }
            x += dir[0];
            y += dir[1];
        }
        return false;
    }


    private int[][] readInput() {
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
/*        input = new ArrayList<>();
        input.add("30373");
        input.add("25512");
        input.add("65332");
        input.add("33549");
        input.add("35390");*/

        int m = input.size();
        int n = input.get(0).length();
        int[][] grid = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = Character.getNumericValue(input.get(i).charAt(j));
            }
        }

        return grid;
    }
}
