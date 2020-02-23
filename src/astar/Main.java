package astar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static astar.DrawingBoard.createGui;


public class Main  {

    static int width = 1000;
    static int height = 1000;
    static int rows = 50;
    static int cols = 50;
    static final int w = width / cols;
    static final int h = height / rows;

    public static final DrawingBoard gui = createGui(width, height);

    static Spot start;
    static Spot end;


    public static void main(String[] a) {

        Spot[][] grid = new Spot[rows][cols];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Spot(i, j);
                MyDrawable gridSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), false);
                gui.addMyDrawable(gridSquare);

                if (grid[i][j].wall) {
                    MyDrawable blackSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), true);
                    gui.addMyDrawable(blackSquare);
                }
            }
        }


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < (grid[i].length) - 1; j++) {
                if (i > 0) {
                    grid[i][j].addNeighbor(grid[i - 1][j]);
                }
                if (j > 0) {
                    grid[i][j].addNeighbor(grid[i][j - 1]);
                }
                if (i < grid.length - 1) {
                    grid[i][j].addNeighbor(grid[i + 1][j]);
                }
                if (j < grid.length - 1) {
                    grid[i][j].addNeighbor(grid[i][j + 1]);
                }
                if (i > 0 && j > 0) {
                    grid[i][j].addNeighbor(grid[i - 1][j - 1]);
                }
                if (i < grid.length - 1 && j < grid.length - 1) {
                    grid[i][j].addNeighbor(grid[i + 1][j + 1]);
                }
            }
        }

        start = grid[(rows-1)/2][0];
        end = grid[rows - 1][cols - 1];
        double total_h = h(start, end);
        start.setH(total_h);
        start.updateF();

        A_Star(start, end);

    }

    public static void reconstruct_path(Spot curr) {
        while (curr != null) {
            MyDrawable blue = new MyDrawable(new Rectangle(curr.getX() * w, curr.getY() * h, w, h), new Color(0f, 0f, 1f, 1f), new BasicStroke(0), true);
            gui.addMyDrawable(blue);
            curr = curr.getPrevious();
        }

    }

    public static void A_Star(Spot start, Spot end) {
        List<Spot> openSet = new ArrayList<>();
        List<Spot> closedSet = new ArrayList<>();

        openSet.add(start);

        if (openSet.size() > 0) {
            while (openSet.size() > 0) {


                
                //*** PART OF DRAWING ***
                //timer is only here for slowly down, to give the GUI time to render each step
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    continue;
                }




                //check the node in the openSet with lowest F score
                int current = 0;
                for (int i = 0; i < openSet.size(); i++) {
                    if (openSet.get(i).getF() < openSet.get(current).getF()) {
                        current = i;
                    }
                }

                Spot curr_Node = openSet.get(current);

                openSet.remove(curr_Node);
                closedSet.add(curr_Node);




                //*** PART OF DRAWING ***
                for (int i = 0; i < closedSet.size(); i++) {
                    MyDrawable green = new MyDrawable(new Rectangle(closedSet.get(i).getX() * w, closedSet.get(i).getY() * h, w, h), new Color(0f, 1f, 0f, 0.1f), new BasicStroke(0), true);
                    gui.addMyDrawable(green);
                }



                List<Spot> neighbors = curr_Node.getNeighbors();

                for (int i = 0; i < neighbors.size(); i++) {
                    Spot neighbor = neighbors.get(i);
                    if (!closedSet.contains(neighbor) && !curr_Node.wall) {
                        double tent_score = curr_Node.getG() + 1;

                        if (openSet.contains(neighbor)) {
                            if (tent_score < neighbor.getG()) {
                                neighbor.setG(tent_score);
                            }

                        } else {
                            neighbor.setG(tent_score);
                            openSet.add(neighbor);

                            //*** PART OF DRAWING ***
                            MyDrawable red = new MyDrawable(new Rectangle(neighbor.getX() * w, neighbor.getY() * h, w, h), new Color(1f, 0f, 0f, 0.4f), new BasicStroke(0), true);
                            gui.addMyDrawable(red);

                        }

                        neighbor.setPrevious(curr_Node);

                        neighbor.setH(h(neighbor, end));
                        neighbor.updateF();
                    }

                }
                //check if we have reached the end
                int cX = curr_Node.getX();
                int cY = curr_Node.getY();
                int eX = end.getX();
                int eY = end.getY();
                if (cX == eX && cY == eY) {
                    System.out.println("Finished!");

                    //*** PART OF DRAWING ***
                    reconstruct_path(curr_Node);

                    break;
                }

            }
        } else {
            System.out.println("No Solution!");
        }

    }

    //manhattan distance formula
    public int manh(Spot a, Spot b) {
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
    }

    //euclidean distance formula
    static public double h(Spot a, Spot b) {
        int deltaX = a.getX() - b.getX();
        int deltaY = a.getY() - b.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }


}

