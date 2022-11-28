package search;

import java.util.AbstractList;

public class AVLTree extends BSTree{
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 10, 9, 8};
        for (int val:arr) {
            avlTree.add(val);
        }

        avlTree.infixList();

        avlTree.del(5);
        avlTree.infixList();


    }

    public void add(int data){
        if (root == null) {
            root = new AVLNode(data);
        } else {
            root.add(data);
        }
    }
}

class AVLNode extends Node {

    AVLNode(int data){
        value = data;
    }

    /**
     * 以this为根节点的树，进行左旋转操作
     * 根节点被赋予其右孩子的值，继承其右孩子的右孩子，并重写其左孩子
     * 如何重新左孩子：新左孩子的值即原根节点的值，新左孩子的左孩子就是原根节点的左孩子，新左孩子的右孩子是原根节点右孩子的左孩子（我知道你看不懂）
     */
    public void leftRotate(){
        // temp是作为旋转完后的跟节点的左孩子的
        Node temp = new Node(value);
        // 旋转下来节点的左孩子，当然自己的左孩子不变
        temp.left = this.left;
        // 旋转下来节点的右孩子，是原先右孩子的左孩子
        temp.right = this.right.left;
        // 旋转上来
        this.value = this.right.value;
        // 继承右孩子
        this.right = this.right.right;
        // 重写左孩子
        this.left = temp;
    }

    /**
     * 以this为根节点的树，进行右旋转操作
     */
    public void rightRotate(){
        AVLNode temp = new AVLNode(value);
        temp.right = this.right;
        temp.left = this.left.right;
        this.value = this.left.value;
        this.left = this.left.left;
        this.right = temp;
    }

    public void add(int data){
        Node node = new AVLNode(data);
        if(value == data){
            System.out.println("已经有这个节点了，添加失败");
        } else if(value > data){
            if(left != null){
                left.add(data);
            } else {
                left = node;
            }
        } else {
            if(right != null){
                right.add(data);
            } else {
                right = node;
            }
        }

        // bf > 1  进行右旋操作
        if(leftHeight() - rightHeight() > 1){
            // 判断this左子节点的bf，看是否异号(bf = -1)，是的话需要局部左旋
            if(this.left != null && left.leftHeight() < left.rightHeight()){
                left.leftRotate();
            }
            rightRotate();
            return;
        }

        // bf < -1  进行左旋操作
        if(leftHeight() - rightHeight() < -1){
            // 判断this右子节点的bf，看是否异号(bf = 1)，是的话需要局部右旋
            if(this.right != null && right.leftHeight() > right.rightHeight()){
                right.rightRotate();
            }
            leftRotate();
        }
    }

}