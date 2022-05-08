package cz.cvut.fel.pjv;

//The Coordinates class equivalent to Pair<Integer, Integer>
//Created to improve readability, clarity
public class Coordinates {
    private Integer x;
    private Integer y;
    public Coordinates(int a, int b) {
        x = a;
        y = b;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
