package search;

class BSTree {
    public static void main(String[] args) {
        int[] arr = {62, 88, 58, 47, 35, 73, 51, 99, 37, 93};
        BSTree tree = new BSTree();
        for (int val : arr) {
            tree.add(val);
        }

        System.out.println(tree.searchParent(37));
        tree.infixList();
        tree.del(62);
        tree.del(93);
        tree.del(37);
        tree.add(111);

        System.out.println("--------------------------");
        tree.infixList();


    }

    public Node root; // 先声明一个根节点用于判空

    // 以下方法均已root为根节点

    // 调用节点的add方法，并加上判空
    public void add(int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            root.add(data);
        }
    }

    // 查找
    public Node search(int key){
        if(root == null){
            System.out.println("这是一个空树");
            return null;
        } else {
            return root.search(key);
        }
    }

    // 查找父节点
    public Node searchParent(int key){
        if(root == null){
            System.out.println("这是一个空树");
            return null;
        } else {
           return root.searchParent(key);
        }
    }

    public void del(int key){
        if(root == null){
            System.out.println("这是一个空树");
        } else {
            root.del(key);
        }
    }

    // 中序遍历
    public void infixList() {
        if (root == null) {
            return;
        } else {
            root.infixList();
        }
    }

}

//二叉排序树的节点
class Node {
    public int value;
    public Node left;
    public Node right;

    Node(){}

    Node(int data) {
        value = data;
    }

    @Override
    public String toString() {
        String l_value = null, r_value = null;
        if (left != null) {
            l_value = String.valueOf(left.value);
        }
        if (right != null) {
            r_value = String.valueOf(right.value);
        }
        return "Node{" +
                "value=" + value +
                ", left=" + l_value +
                ", right=" + r_value +
                '}';
    }


    // 在Node里的方法仅用于不是空树的情况，对树的判空交给BinarySortTree中的同名方法去处理

    /**
     * 以this为根节点的树，进行高度计算
     * @return 树的高度
     */
    public int height(){
        return Math.max(this.left == null ? 0 : this.left.height(), this.right == null ? 0 : this.right.height()) + 1;
    }

    /**
     * 求以this节点的左子树高度
     * @return 左子树高度
     */
    public int leftHeight() {
        if (this.left == null) {
            return 0;
        }
        return this.left.height();
    }

    /**
     * 求以this节点的右子树高度
     * @return 右子树高度
     */
    public int rightHeight() {
        if (this.right == null) {
            return 0;
        }
        return this.right.height();
    }

    /**
     * 以this为根节点的树，进行中序遍历
     */
    public void infixList() {
        if (left != null) {
            left.infixList();
        }
        System.out.println(this);
        if (right != null) {
            right.infixList();
        }
    }

    /**
     * 搜索以this为根节点的树中value=key的节点，返回该结点
     *
     * @param key
     * @return Node
     */
    public Node search(int key) {
        if (value == key) {
            return this;
        } else if (value > key) {
            if (left != null) {
                return left.search(key);
            } else {
                System.out.println("没有该节点");
                return null;
            }
        } else {
            if (right != null) {
                return right.search(key);
            } else {
                System.out.println("没有该节点");
                return null;
            }
        }
    }

    /**
     * 查找以this为根节点的树中value = key的节点的父亲节点，返回该节点
     *
     * @param key
     * @return Node
     */
    public Node searchParent(int key) {
        if ((this.left != null && this.left.value == key) || (this.right != null && this.right.value == key)) {
            return this;
        } else {
            if (this.left != null && this.value > key) {
                return this.left.searchParent(key);
            } else if (this.right != null && this.value < key) {
                return this.right.searchParent(key);
            } else if (this.value == key) {
                System.out.println("该结点为根节点，无父亲节点");
                return null;
            } else {
                System.out.println("树中没有该节点");
                return null;
            }
        }
    }

    /**
     * 以this为根节点的树中添加value = data的节点
     * 思路同search，递归，只不过在找到空时插入
     *
     * @param data
     */
    public void add(int data) {
        Node node = new Node(data);
        if (value == data) {
            System.out.println("已经有这个节点了，添加失败");
        } else if (value > data) {
            if (left != null) {
                left.add(data);
            } else {
                left = node;
            }
        } else {
            if (right != null) {
                right.add(data);
            } else {
                right = node;
            }
        }
    }


    /**
     * 删除以this为根节点的树中value = key的节点，并且使新树依然为一个排序二叉树
     *
     * @param key
     */
    public void del(int key) {
        // 找到这个节点
        Node target = search(key);
        if (target == null) {
            System.out.println("没有这个节点");
            return;
        } else {
            // 找它的父亲节点
            Node parent = searchParent(key);

            //1. 如果他是叶子节点
            if (target.left == null && target.right == null) {
                // 如果他是根节点
                if (parent == null) {
                    target = null;
                } else {
                    if (parent.left != null && parent.left.value == target.value) {
                        parent.left = null;
                    } else if (parent.right != null && parent.right.value == target.value) {
                        parent.right = null;
                    } else {
                        System.out.println("啷个可能");
                    }
                }
            }

            //2. 如果他是同时有左右两颗子树的节点
            else if (target.left != null && target.right != null) {
                // 如果他是根节点
                if (parent == null) {
                    target.value = deleteLeftMax(target);
                } else {
                    if (parent.left != null && parent.left.value == target.value) {
                        parent.left.value = deleteLeftMax(target);
                    } else if (parent.right != null && parent.right.value == target.value) {
                        parent.right.value = deleteLeftMax(target);
                    } else {
                        System.out.println("啷个可能");
                    }
                }
            }

            //3. 如果他有一个子树
            else {
                // 如果他是根节点
                if (parent == null) {
                    if (target.left != null) {
                        target = target.left;
                    } else {
                        target = target.right;
                    }
                } else {
                    if (parent.left != null && parent.left.value == target.value) {
                        if (target.left != null) {
                            parent.left = target.left;
                        } else {
                            parent.left = target.right;
                        }
                    } else if (parent.right != null && parent.right.value == target.value) {
                        if (target.left != null) {
                            parent.right = target.left;
                        } else {
                            parent.right = target.right;
                        }
                    } else {
                        System.out.println("啷个可能");
                    }
                }
            }
        }
    }

    /**
     * 在以node为根节点的树中，删除其左子树的最大节点
     * 由于是用于删除中节点同时有左右子树的情况，此处就不做相关判空了
     *
     * @param node
     * @return
     */
    private int deleteLeftMax(Node node) {
        Node target = node.left;
        while (target.right != null) {
            target = target.right;
        }
        del(target.value);
        return target.value;

    }

    /**
     * 左旋方法，具体在AVLTree的Node中实现
     */
    public void leftRotate(){}

    /**
     * 右旋方法，具体在AVLTree的Node中实现
     */
    public void rightRotate(){}
}







