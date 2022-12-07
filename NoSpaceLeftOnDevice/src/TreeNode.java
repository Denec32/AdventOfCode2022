import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private TreeNode parent;
    private Type type;
    private List<TreeNode> children = new ArrayList<>();
    private int size = 0;

    public TreeNode(TreeNode parent, Type type) {
        this.parent = parent;
        this.type = type;
    }

    public TreeNode(TreeNode parent, Type type, int size) {
        this(parent, type);
        this.size = size;
    }

    public TreeNode getParent() {
        return parent;
    }

    public Type getType() {
        return type;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
