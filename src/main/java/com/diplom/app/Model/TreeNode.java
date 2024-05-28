package com.diplom.app.Model;

import com.diplom.app.Enum.Blocks;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TreeNode {
    public static void main(String[] args) {

        /*
        Данный main() нужен для тестирования алгоритма постфиксного обхода (Postorder) бинарного дерева,
        которая составляет исходная принципиальная схема!
        */

        Node root1 = new Node(Blocks.OR,0,new Node(Blocks.OR,0,new Node(Blocks.REFUSAL, 4, null, null), new Node(Blocks.REFUSAL, 5, null, null)),new Node(Blocks.AND, 0, null, new Node(Blocks.REFUSAL, 6, null, null)));
        System.out.println("TEST:   ");
        System.out.println(root1.sum());

    }
}


@Data
@NoArgsConstructor
@Builder
class Node {
    private Blocks name;
    private final int T = 1;
    private int data;
    private  Node left;
    private  Node right;

    public Node(int data) {
        this.data = data;
        left = right = null;
    }

    public Node(Blocks name, int data, Node left, Node right) {
        this.name = name;
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Node(Blocks name, int data) {
        this.name = name;
        this.data = data;
    }


    public Integer sum(){
        return GFG.sumV2(this);
    }


    static class GFG{

        static void printPostorder(Node node) {
            if(node == null) {
                return;
            }
            printPostorder(node.getLeft());
            printPostorder(node.getRight());
            System.out.println(node.getName()+" "+node.getData() + " ");

        }

        static Integer sum(Node node) {
            Integer sum = node.getData();
            if(node.getLeft() != null) {
                sum += sum(node.getLeft());
            }
            if(node.getRight() != null) {
                sum += sum(node.getRight());
            }
            return sum;
        }

        // c названием ноды
        static Integer sumV2(Node node) {
            Integer sum = node.getData();

            if(node.getLeft() != null || node.getRight() != null) {
                if(node.getName() == Blocks.OR) {
                    Integer sumLeft = 0;
                    Integer sumRight = 0;

                    if(node.getLeft() != null) {
                        sumLeft= sumV2(node.getLeft());
                    }
                    if(node.getRight() != null) {
                        sumRight = sumV2(node.getRight());
                    }

                    sum += 2*sumLeft*sumRight*node.getT();
                }else {

                    if(node.getLeft() != null) {
                        sum += sumV2(node.getLeft());
                    }
                    if(node.getRight() != null) {
                        sum += sumV2(node.getRight());
                    }
                }
            }
            return sum;
        }
    }
}


