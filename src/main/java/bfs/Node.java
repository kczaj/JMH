package bfs;

public class Node implements Comparable {
    private int index;
    private Node up;
    private Node right;
    private Node down;
    private Node left;
    private boolean entrance;
    private boolean exit;
    private Node creator;

    public Node(int index) {
        this.index = index;
        entrance = false;
        exit = false;
    }

    public Node(int index, Node up, Node right, Node down, Node left) {
        this.index = index;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        entrance = false;
        exit = false;
    }

    public void setCreator(Node creator) {
        this.creator = creator;
    }

    public void setEntrance(boolean entrance) {
        this.entrance = entrance;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public boolean isExit() {
        return exit;
    }

    public int getIndex() {
        return index;
    }

    public Node getUp() {
        return up;
    }

    public Node getRight() {
        return right;
    }

    public Node getDown() {
        return down;
    }

    public Node getLeft() {
        return left;
    }

    public Node getCreator() {
        return creator;
    }

    public boolean isEntrance() {
        return entrance;
    }

    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        if (o instanceof Node) {
            Node node = (Node) o;
            if (index > node.getIndex()) {
                return 1;
            } else if (index < node.getIndex()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }
}
