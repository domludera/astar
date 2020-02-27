package astar;

import java.util.ArrayList;
import java.util.List;

public class A_Star2 {

    public Spot aStar(boolean[][] boolArr, int x1, int y1, int x2, int y2){

        Spot[][] grid = linkNeighbors(boolArr);

        Spot start = grid[x1][y1];
        Spot end = grid[x2][y2];


        Spot final_node = null;

        List<Spot> openSet = new ArrayList<>();
        List<Spot> closedSet = new ArrayList<>();

        Spot curr_node = start;
        curr_node.setH(end);
        curr_node.setG(curr_node);
        curr_node.updateF();

        openSet.add(curr_node);


        while(openSet.size()>0){

            for(Spot current : openSet){
                if(current.getF()<= curr_node.getF()){
                    curr_node = current;
                }
            }

            if(curr_node.equals(end)){
                final_node = curr_node;
                break;
            }

            closedSet.add(curr_node);
            openSet.remove(curr_node);

            for(Spot neighbor : curr_node.getNeighbors()){
                if(!closedSet.contains(neighbor)) {
                    if (!openSet.contains(neighbor) && !neighbor.wall) {
                        neighbor.setG(curr_node);
                        neighbor.setH(end);
                        neighbor.updateF();
                        neighbor.setPrevious(curr_node);
                        openSet.add(neighbor);
                    } else {
                        double tent_score = curr_node.getG() + euclidean(curr_node, neighbor);
                        if (tent_score <= neighbor.getG()) {
                            neighbor.setG(curr_node);
                            neighbor.updateF();
                            neighbor.setPrevious(curr_node);
                        }
                    }
                }
            }
        }

        return final_node;
    }

    public Spot[][] linkNeighbors(boolean boolArr[][]){

        Spot[][] grid = new Spot[boolArr.length][boolArr[boolArr.length-1].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Spot(i, j, boolArr[i][j]);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
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

        return grid;

    }


    public double euclidean(Spot a, Spot b){
        double x = a.getX()-b.getX();
        double y = a.getY()-b.getY();
        return (Math.pow(x,2)+Math.pow(y,2));
    }

}
