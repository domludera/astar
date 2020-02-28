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

        openSet.add(start);

        Spot current;

        while(openSet.size()>0){
            current = lowestF(openSet);
            closedSet.add(current);
            openSet.remove(current);

            if(current.equals(end)){
                final_node = current;
                break;
            }

            for(Spot neighbor : current.getNeighbors()){
                if(!openSet.contains(neighbor)){
                    neighbor.setPrevious(current);
                    neighbor.setH(end);
                    neighbor.setG(current);
                    openSet.add(neighbor);
                }else{
                    if( neighbor.getG() > (current.getG()+euclidean(neighbor,current)) ){
                        neighbor.setPrevious(current);
                        neighbor.setG(current);
                    }
                }
            }
        }
        return final_node;
    }

    public Spot lowestF(List<Spot> list){
        Spot lowestF = null;
        if(list.size()>0){
            lowestF = list.get(0);
            for(Spot lowest : list){
                if(lowest.getF()<lowestF.getF()){
                    lowestF = lowest;
                }
            }
        }
        return lowestF;
    }

//    public final List<T> findPath(int oldX, int oldY, int newX, int newY) {
//        openList = new LinkedList<T>();
//        closedList = new LinkedList<T>();
//        openList.add(nodes[oldX][oldY]); // add starting node to open list
//
//        done = false;
//        T current;
//        while (!done) {
//            current = lowestFInOpen(); // get node with lowest fCosts from openList
//            closedList.add(current); // add current node to closed list
//            openList.remove(current); // delete current node from open list
//
//            if ((current.getxPosition() == newX)
//                    && (current.getyPosition() == newY)) { // found goal
//                return calcPath(nodes[oldX][oldY], current);
//            }
//
//            // for all adjacent nodes:
//            List<T> adjacentNodes = getAdjacent(current);
//            for (int i = 0; i < adjacentNodes.size(); i++) {
//                T currentAdj = adjacentNodes.get(i);
//                if (!openList.contains(currentAdj)) { // node is not in openList
//                    currentAdj.setPrevious(current); // set current node as previous for this node
//                    currentAdj.sethCosts(nodes[newX][newY]); // set h costs of this node (estimated costs to goal)
//                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
//                    openList.add(currentAdj); // add node to openList
//                } else { // node is in openList
//                    if (currentAdj.getgCosts() > currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
//                        currentAdj.setPrevious(current); // set current node as previous for this node
//                        currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
//                    }
//                }
//            }
//
//            if (openList.isEmpty()) { // no path exists
//                return new LinkedList<T>(); // return empty list
//            }
//        }
//        return null; // unreachable
//    }


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
