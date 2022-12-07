import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NoSpaceLeftOnDevice {
    private final int MAX_DIR_SIZE = 100000;

    private final int SPACE_AVAILABLE = 70000000;

    private final int SPACE_REQUIRED = 30000000;

    private int minSizeToDelete = 0;

    private TreeNode root = new TreeNode(null, Type.Directory);

    public NoSpaceLeftOnDevice() {
        this.root = initializeTree();
    }

    public int SolutionPartOne() {
        return calculateSizeOfSmallestFolders(root);
    }
    public int SolutionPartTwo() {

        int currentSize = root.getSize();
        int unusedSpace = SPACE_AVAILABLE - currentSize;
        minSizeToDelete = SPACE_REQUIRED - unusedSpace;

        List<Integer> folderSizes = new LinkedList<>();

        locateDirsOfFittingSize(folderSizes, root);

        int minSize = Integer.MAX_VALUE;
        for (var size : folderSizes) {
            if(size < minSize) {
                minSize = size;
            }
        }

        return minSize;
    }

    private TreeNode initializeTree() {
        String[] consoleOutput = readInput();
        TreeNode root = new TreeNode(null, Type.Directory);
        TreeNode current = root;

        Queue<TreeNode> q = new LinkedList<>();

        for(int i = 1; i < consoleOutput.length; i++) {
            String[] input = consoleOutput[i].split(" ");
            if(input[0].equals("$")) {
                if(input[1].equals("cd")) {
                    if(input[2].equals("..")) {
                        if(current.getParent() != null) {
                            current = current.getParent();
                        }
                    } else {
                        TreeNode child = new TreeNode(current, Type.Directory);
                        current.addChild(child);
                        current = child;
                    }
                } else if (input[1].equals("ls")) {
                    int index = i + 1;
                    while(index < consoleOutput.length && !consoleOutput[index].split(" ")[0].equals("$")) {
                        String[] line = consoleOutput[index].split(" ");
                        if(line[0].equals("dir")) {
                            TreeNode dir = new TreeNode(current, Type.Directory);
                            current.addChild(dir);
                        } else {
                            int size = Integer.parseInt(line[0]);
                            TreeNode file = new TreeNode(current, Type.File, size);
                            current.addChild(file);
                            q.add(file);
                        }
                        index++;
                    }
                    i = index - 1;
                }
            }
        }

        while (!q.isEmpty()) {
            var file = q.poll();
            int size = file.getSize();

            file = file.getParent();
            while(file != null) {
                file.setSize(file.getSize() + size);
                file = file.getParent();
            }
        }
        return root;
    }

    private void locateDirsOfFittingSize(List<Integer> folderSizes, TreeNode root) {
        if(root.getType() == Type.File) return;
        if(root.getSize() >= minSizeToDelete) {
            folderSizes.add(root.getSize());
        }
        for (var child : root.getChildren()) {
            locateDirsOfFittingSize(folderSizes, child);
        }
    }

    private int calculateSizeOfSmallestFolders(TreeNode root) {
        if(root.getType() == Type.File) return 0;
        int dirs = (root.getSize() <= MAX_DIR_SIZE) ? root.getSize() : 0;
        for (var child : root.getChildren()) {
            dirs += calculateSizeOfSmallestFolders(child);
        }
        return dirs;
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
