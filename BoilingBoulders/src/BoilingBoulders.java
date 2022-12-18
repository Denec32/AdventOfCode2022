import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BoilingBoulders {
    private final int[][] dirs = new int[][]{
            {0, 0, 1},
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, -1},
            {-1, 0, 0},
            {0, -1, 0},
    };
    boolean[][][] grid;

    public int SolutionPartOne() {

        String[] cubes = readInput();
        initGrid(cubes);

        int count = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                for (int z = 0; z < grid[0][0].length; z++) {
                    if(grid[x][y][z]) {
                        int bareSides = 6;
                        for(var dir : dirs) {
                            int nX = x + dir[0];
                            int nY = y + dir[1];
                            int nZ = z + dir[2];
                            if(nX >= 0 && nY >= 0 && nZ >= 0 && grid[nX][nY][nZ]) {
                                bareSides--;
                            }
                        }
                        count += bareSides;
                    }
                }
            }
        }
        return count;
    }

    private void initGrid(String[] cubes) {
        grid = new boolean[23][23][23];
        for (String cube : cubes) {
            String[] coordinates = cube.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int z = Integer.parseInt(coordinates[2]);
            grid[x][y][z] = true;
        }
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
