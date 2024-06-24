package com.diplom.app.Model;

import com.diplom.app.Enum.Blocks;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NodeTree {
    private Blocks name;
    private int t;
    private double data;
    private NodeTree left;
    private NodeTree right;
    private int in1;
    private int in2;
    private int out;

    public NodeTree(Blocks name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name: " + name + "\n" +
                "data: " + data + "\n" +
                "in1: " + in1 + "\n" +
                "in2: " + in2 + "\n" +
                "out: " + out + "\n";
    }

    public String sum() {
        return roundingDouble(GFG.sumV2(this));
    }

    private String roundingDouble(Double t) {
        String str = t.toString();
        int indexE = str.indexOf('E');
        if(indexE != -1) {
            double toBeTruncated = Double.parseDouble(str.substring(0, indexE));
            Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue();
            String degree = str.substring(indexE, str.length());
            str = truncatedDouble.toString() + degree;
            return str;
        } else {
            Double truncatedDouble = BigDecimal.valueOf(Double.parseDouble(str))
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue();
            str = truncatedDouble.toString();
            return str;
        }
    }

    static class GFG {

        // c названием ноды
        static Double sumV2(NodeTree node) {
            Double sum = node.getData();

            if (node.getLeft() != null || node.getRight() != null) {
                if (node.getName() == Blocks.AND) {
                    Double sumLeft = 0.;
                    Double sumRight = 0.;

                    if (node.getLeft() != null) {
                        sumLeft = sumV2(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        sumRight = sumV2(node.getRight());
                    }

                    sum += 2 * sumLeft * sumRight * node.getT();
                } else {

                    if (node.getLeft() != null) {
                        sum += sumV2(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        sum += sumV2(node.getRight());
                    }
                }
            }
            return sum;
        }
    }

    public Blocks getName() {
        return name;
    }

    public void setName(Blocks name) {
        this.name = name;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public NodeTree getLeft() {
        return left;
    }

    public void setLeft(NodeTree left) {
        this.left = left;
    }

    public NodeTree getRight() {
        return right;
    }

    public void setRight(NodeTree right) {
        this.right = right;
    }

    public void setT(int t){
        this.t = t;
    }

    public int getT() {
        return t;
    }

    public int getIn1() {
        return in1;
    }

    public void setIn1(int in1) {
        this.in1 = in1;
    }

    public int getIn2() {
        return in2;
    }

    public void setIn2(int in2) {
        this.in2 = in2;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

}
