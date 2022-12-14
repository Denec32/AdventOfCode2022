import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HillClimbingAlgorithm {
    private final int[][] dirs = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int SolutionPartOne() {
        var input = readInput();

        char[][] grid = parseInput(input);

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int start = getStartingPoint(grid);
        grid[start][0] = 'a';

        Queue<int[]> q = new LinkedList<>();

        q.offer(new int[] {start, 0, 0, grid[start][0]});

        return findShortestPath(grid, visited, q);
    }

    public int SolutionPartTwo() {
        var input = readInput();

        char[][] grid = parseInput(input);

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int start = getStartingPoint(grid);
        grid[start][0] = 'a';

        Queue<int[]> q = new LinkedList<>();

        findAllLowPoints(grid, q);

        return findShortestPath(grid, visited, q);
    }

    private int findShortestPath(char[][] grid, boolean[][] visited, Queue<int[]> q) {
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            int row = curr[0];
            int col = curr[1];
            int length = curr[2];
            char height = (char) curr[3];

            if(row >= 0 && col >= 0 && row < grid.length && col < grid[0].length &&
                    !visited[row][col] && (grid[row][col] - height <= 1 || grid[row][col] == 'E')) {
                visited[row][col] = true;

                if(grid[row][col] == 'E') {
                    if(height == 'z' || height == 'y') {
                        return length;
                    } else {
                        visited[row][col] = false;
                    }
                }
                for (var dir : dirs) {
                    q.offer(new int[] {row + dir[0], col + dir[1], length + 1, grid[row][col]});
                }
            }
        }

        return -1;
    }

    private void findAllLowPoints(char[][] grid, Queue<int[]> q) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 'a') {
                    q.offer(new int[] {i, j, 0, grid[i][j]});
                }
            }
        }
    }

    private int getStartingPoint(char[][] grid) {
        int start = 0;
        for(int i = 0; i < grid.length; i++) {
            if(grid[i][0] == 'S') {
                start = i;
            }
        }
        return start;
    }

    private char[][] parseInput(String[] input) {
        int m = input.length;
        int n = input[0].length();
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = input[i].charAt(j);
            }
        }
        return grid;
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
