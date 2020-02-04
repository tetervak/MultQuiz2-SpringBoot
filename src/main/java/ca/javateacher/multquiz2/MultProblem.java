package ca.javateacher.multquiz2;

import java.io.Serializable;

public class MultProblem implements Serializable {

    final private int a;
    final private int b;
    final private int answer;

    public MultProblem() {
        a = (int) (1 + 9 * Math.random());
        b = (int) (1 + 9 * Math.random());
        answer = a * b;
    }

    public MultProblem(int a, int b) {
        this.a = a;
        this.b = b;
        answer = a * b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getAnswer() {
        return answer;
    }

}

