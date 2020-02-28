package astar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static astar.A_Star.*;
import static astar.DrawingBoard.createGui;
import static astar.GetPixelColor.getBoolArr;
import static astar.GetPixelColor.getRGBarray;


public class Main  {

    static final int w = 2;
    static final int h = 2;

    public static DrawingBoard gui;


    public static void main(String[] a) throws IOException {

        BufferedImage image = ImageIO.read(new File("media/h8scaled.png"));
        gui = createGui(image.getWidth(), image.getHeight());

        int[][] result = getRGBarray(image);
        boolean[][] bool = getBoolArr(result, -534826);

        Spot[][] grid = new Spot[bool.length][bool[bool.length-1].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Spot(i, j, bool[i][j]);
                MyDrawable gridSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), false);
                gui.addMyDrawable(gridSquare);

                if (grid[i][j].wall) {
                    MyDrawable blackSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), true);
                    gui.addMyDrawable(blackSquare);
                }
            }
        }

        linkNeighbors(grid);

        int x1 = 80;
        int y1 = 80;
        int x2 = 70;
        int y2 = 200;


        Spot path = A_Star(grid,x1,y1,x2,y2);
        reconstruct_path(path);

    }


    public static void linkNeighbors(Spot[][] grid){
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
    }

    public static void reconstruct_path(Spot curr) {
        while (curr != null) {
            MyDrawable blue = new MyDrawable(new Rectangle(curr.getX() * w, curr.getY() * h, w, h), new Color(0f, 0f, 1f, 1f), new BasicStroke(0), true);
            gui.addMyDrawable(blue);
            curr = curr.getPrevious();
        }

    }

    public static Spot A_Star(Spot[][] grid, int x1, int y1, int x2, int y2) {
        java.util.List<Spot> openSet = new ArrayList<>();
        List<Spot> closedSet = new ArrayList<>();

        Spot start = grid[y1][x1];
        Spot end = grid[y2][x2];

        start.setH(euclidean(start, end));
        start.updateF();

        openSet.add(start);

        Spot current;
        Spot path = null;

        while (openSet.size() > 0) {


            //*** PART OF DRAWING ***
            //timer is only here for slowly down, to give the GUI time to render each step
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (Exception e) {
                continue;
            }




            //check the node in the openSet with lowest F score
            int currentIndex = 0;
            current = openSet.get(currentIndex);
//                for (int i = 0; i < openSet.size(); i++) {
//                    if (openSet.get(i).getF() < openSet.get(currentIndex).getF()) {
//                        currentIndex = i;
//                    }
//                }


            for(Spot candidate : openSet){
                if(candidate.getF()<current.getF()){
                    currentIndex = openSet.indexOf(candidate);
                }
            }

            current = openSet.get(currentIndex);



            openSet.remove(current);
            closedSet.add(current);




            //*** PART OF DRAWING ***
            for (int i = 0; i < closedSet.size(); i++) {
                MyDrawable green = new MyDrawable(new Rectangle(closedSet.get(i).getX() * w, closedSet.get(i).getY() * h, w, h), new Color(0f, 1f, 0f, 0.1f), new BasicStroke(0), true);
                gui.addMyDrawable(green);
            }

            for(Spot neighbor : current.getNeighbors()){
                if(!closedSet.contains(neighbor) && !neighbor.wall){
                    double tent_score = current.getG() + euclidean(current, neighbor);

                    if(openSet.contains(neighbor)){
                        if(tent_score < neighbor.getG()){
                            neighbor.setG(tent_score);
                        }
                    }else{
                        neighbor.setG(tent_score);
                        openSet.add(neighbor);

                        //*** PART OF DRAWING ***
                        MyDrawable red = new MyDrawable(new Rectangle(neighbor.getX() * w, neighbor.getY() * h, w, h), new Color(1f, 0f, 0f, 0.4f), new BasicStroke(0), true);
                        gui.addMyDrawable(red);
                    }

                    neighbor.setPrevious(current);

                    neighbor.setH(euclidean(neighbor,end));
                    neighbor.updateF();
                }

            }

            if(current.equals(end)){
                path = current;
                break;
            }

        }
        return path;

    }

    //manhattan distance formula
    public int manh(Spot a, Spot b) {
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
    }

    //euclidean distance formula
    static public double euclidean(Spot a, Spot b) {
        int deltaX = a.getX() - b.getX();
        int deltaY = a.getY() - b.getY();
        return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
    }





}

