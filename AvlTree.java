class Node {
    int value;
    int height;
    int balanceFactor;
    Node left;
    Node right;
    Node(int value) {
        this.value = value;
        this.height = 0;
        this.balanceFactor = 0;
        this.left = null;
        this.right = null;
    }
}

class AvlTree {
    private int nodesCount;
    private Node root;

    AvlTree() {
        this.nodesCount = 0;
        this.root = null;
    }

    
    public boolean find(int value) {
        return contains(root, value);
    }

    
    private boolean contains(Node node, int target) {
        if (node == null) {
            return false;
        }

        if (node.value == target) {
            return true;
        }

        if (target > node.value) {
            return contains(node.right, target);
        } else {
            return contains(node.left, target);
        }
    }

    
    public boolean insert(Integer value) {
        if (value == null) {
            return false;
        }

        if (find(value)) {
            return false;
        } else {
            root = insert(root, value);
            nodesCount++;
            return true;
        }
    }

    private Node insert(Node node, int target) {
        if (node == null) {
            return new Node(target);
        }

        if (target > node.value) {
            node.right = insert(node.right, target);
        } else {
            node.left = insert(node.left, target);
        }

        update(node);

        return balance(node);
    }

    private void update(Node node) {
        int leftHeight = (node.left == null) ? -1 : node.left.height;
        int rightHeight = (node.right == null) ? -1 : node.right.height;

        node.height = 1 + Math.max(leftHeight, rightHeight);
        node.balanceFactor = rightHeight - leftHeight;
    }

    private Node balance(Node node) {
        if (node.balanceFactor == 2) {
            if (node.right.balanceFactor >= 0) {
                return rightRightCase(node);
            } else {
                return rightLeftCase(node);
            }
        } else if (node.balanceFactor == -2) {
            if (node.left.balanceFactor <= 0) {
                return leftLeftCase(node);
            } else {
                return leftRightCase(node);
            }
        }
        return node;
    }

    private Node leftLeftCase(Node node) {
        return rotateRight(node);
    }

    private Node leftRightCase(Node node) {
        node.left = rotateLeft(node.left);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        return rotateLeft(node);
    }

    private Node rightLeftCase(Node node) {
        node.right = rotateRight(node.right);
        return rightRightCase(node);
    }

    private Node rotateRight(Node node) {
        Node B = node.left;
        node.left = B.right;
        B.right = node;

        update(node);
        update(B);
        return B;
    }

    private Node rotateLeft(Node node) {
        Node B = node.right;
        node.right = B.left;
        B.left = node;

        update(node);
        update(B);
        return B;
    }

    public boolean remove(Integer value) {
        if (value == null) {
            return false;
        }

        if (!find(value)) {
            return false;
        } else {
            root = remove(root, value);
            nodesCount--;
            return true;
        }
    }

    private Node remove(Node node, int target) {
        if (node == null) {
            return null;
        }

        if (target == node.value) {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node successor = node.left;
                while (successor.right != null) {
                    successor = successor.right;
                }

                node.value = successor.value;
                node.left = remove(node.left, successor.value);
            }
        } else if (target > node.value) {
            node.right = remove(node.right, target);
        } else {
            node.left = remove(node.left, target);
        }

        update(node);

        return balance(node);
    }

    public int size() {
        return nodesCount;
    }

    public Node getRoot() {
        return root;
    }
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.value + " ");
            inorder(node.right);
        }

    }
    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);
        tree.insert(12);
        tree.insert(17);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);
        tree.insert(11);
        tree.insert(13);
        tree.insert(16);
        tree.insert(18);

        System.out.println("Size: " + tree.size());
        System.out.println("Root: " + tree.getRoot().value);

        tree.inorder(tree.getRoot());
    }
}
