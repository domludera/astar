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


    public Spot(int x, int y, boolean b) {
        this.x = x;
        this.y = y;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.neighbors = new ArrayList<>();
        this.previous = null;
        this.wall = b;
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

    public void setG(Spot current) {
        this.g = current.getG() + euclidean(this,current);
    }

    public double getH() {
        return h;
    }

    public void setH(Spot end) {
        this.h = euclidean(this,end);
    }

    public double euclidean(Spot a, Spot b){
        double x = a.getX()-b.getX();
        double y = a.getY()-b.getY();
        return (Math.pow(x,2)+Math.pow(y,2));
    }

    public double manh(Spot a, Spot b){
        double x = Math.abs(a.getX()-b.getX());
        double y = Math.abs(a.getY()-b.getY());
        return x + y;

    }

    public void toPrint(){
        System.out.println("H "+this.getH());
        System.out.println("G "+this.getG());
        System.out.println("F "+this.getF());
    }

    public boolean equals(Spot toCompare){
        return this.x == toCompare.x && this.y == toCompare.y;
    }

}
