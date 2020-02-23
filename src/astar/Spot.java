package astar;

import java.util.ArrayList;
import java.util.List;

public class Spot {

    private double f;
    private double g;
    private double h;
    private int x;
    private int y;
    private List<Spot> neighbors;
    private Spot previous;
    public boolean wall;


    public Spot(int x, int y) {
        this.x = x;
        this.y = y;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.neighbors = new ArrayList<>();
        this.previous = null;

        this.wall = Math.random() < 0.3;
        if (this.x < 3 && this.y < 3) {
            this.wall = false;
        }
    }

    public void setPrevious(Spot previous) {
        this.previous = previous;
    }

    public Spot getPrevious() {
        return this.previous;
    }

    public void addNeighbor(Spot neighbor) {
        this.neighbors.add(neighbor);
    }

    public List<Spot> getNeighbors() {
        return this.neighbors;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getF() {
        return f;
    }

    public void updateF() {
        this.f = this.g + this.h;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
