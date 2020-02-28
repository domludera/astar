//package astar;

//import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.util.ArrayList;
//import java.util.List;

//public class A_Star {

//    public static Spot aStar(Spot start, Spot end) {

//        Spot final_Node = null;

//        List<Spot> openSet = new ArrayList<>();
//        List<Spot> closedSet = new ArrayList<>();

//        openSet.add(start);
//        Spot curr_Node;
//        List<Spot> neighbors;
//        double tent_score;

//        if (openSet.size() > 0) {
//            while (openSet.size() > 0) {

//                //*** PART OF DRAWING ***
//                //timer is only here for slowly down, to give the GUI time to render each step
//                /*
//                try {
//                    TimeUnit.MILLISECONDS.sleep(10);
//                } catch (Exception e) {
//                    continue;
//                }

//                 */

//                //check the node in the openSet with lowest F score
//                int current = 0;
//                for (int i = 0; i < openSet.size(); i++) {
//                    if (openSet.get(i).getF() < openSet.get(current).getF()) {
//                        current = i;
//                    }
//                }

//                curr_Node = openSet.get(current);

//                openSet.remove(curr_Node);
//                closedSet.add(curr_Node);

//                //*** PART OF DRAWING ***
//                /*
//                for (int i = 0; i < closedSet.size(); i++) {
//                    MyDrawable green = new MyDrawable(new Rectangle(closedSet.get(i).getX() * w, closedSet.get(i).getY() * h, w, h), new Color(0f, 1f, 0f, 0.1f), new BasicStroke(0), true);
//                    gui.addMyDrawable(green);
//                }

//                 */

//                neighbors = curr_Node.getNeighbors();

//                for (int i = 0; i < neighbors.size(); i++) {

//                    Spot neighbor = neighbors.get(i);

//                    if (!closedSet.contains(neighbor) && curr_Node.wall) {
//                        tent_score = curr_Node.getG() + 1;

//                        if (openSet.contains(neighbor)) {
//                            if (tent_score < neighbor.getG()) {
//                                neighbor.setG(curr_Node);
//                            }

//                        } else {
//                            neighbor.setG(curr_Node);
//                            openSet.add(neighbor);

//                            //*** PART OF DRAWING ***
//                            /*
//                            MyDrawable red = new MyDrawable(new Rectangle(neighbor.getX() * w, neighbor.getY() * h, w, h), new Color(1f, 0f, 0f, 0.4f), new BasicStroke(0), true);
//                            gui.addMyDrawable(red);

//                             */

//                        }

//                        neighbor.setPrevious(curr_Node);

//                        neighbor.setH(end);
//                        neighbor.updateF();
//                    }

//                }
//                //check if we have reached the end
//                int cX = curr_Node.getX();
//                int cY = curr_Node.getY();
//                int eX = end.getX();
//                int eY = end.getY();
//                if (cX == eX && cY == eY) {
//                    final_Node = curr_Node;
//                    System.out.println("Finished!");
//                }

//            }
//        } else {
//            System.out.println("No Solution!");
//        }

//        return final_Node;

//    }




//    //manhattan distance formula
//    public static int manh(Spot a, Spot b) {
//        int x = a.getX() - b.getX();
//        int y = a.getY() - b.getY();
//        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
//    }

//    //euclidean distance formula
//    public static double h(Spot a, Spot b) {
//        int deltaX = a.getX() - b.getX();
//        int deltaY = a.getY() - b.getY();
//        return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
//    }

//}
