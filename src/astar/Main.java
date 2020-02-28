//package astar;
//
//import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
//import org.w3c.dom.ls.LSOutput;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static astar.A_Star.*;
//import static astar.DrawingBoard.createGui;
//
//
//public class Main  {
//
//    /*
//    static int width = 1000;
//    static int height = 1000;
//    static int rows = 50;
//    static int cols = 50;
//
//     */
//    static final int w = 5;
//    static final int h = 5;
//
//
//    public static DrawingBoard gui;
//
//    static Spot start;
//    static Spot end;
//
//
//    public static void main(String[] a) throws IOException {
//
//        GetPixelColor.scalePNG("media/h8.png", "media/100h8.png", 250);
//
//        BufferedImage image = ImageIO.read(new File("media/100h8.png"));
//        boolean[][] boolArr = GetPixelColor.createBinaryArr(GetPixelColor.getRGBarray(image));
//
//
//        Spot[][] grid = new Spot[boolArr.length][boolArr[boolArr.length-1].length];
//
//
//        System.out.println("Starting grid creation "+grid.length+" x "+grid[0].length);
//
//
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                grid[i][j] = new Spot(i, j, boolArr[i][j]);
//            }
//        }
//
//
//        System.out.println("Grid created!");
//        System.out.println("Adding Neighbours..");
//
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < (grid[i].length) - 1; j++) {
//                if (i > 0) {
//                    grid[i][j].addNeighbor(grid[i - 1][j]);
//                }
//                if (j > 0) {
//                    grid[i][j].addNeighbor(grid[i][j - 1]);
//                }
//                if (i < grid.length - 1) {
//                    grid[i][j].addNeighbor(grid[i + 1][j]);
//                }
//                if (j < grid.length - 1) {
//                    grid[i][j].addNeighbor(grid[i][j + 1]);
//                }
//                if (i > 0 && j > 0) {
//                    grid[i][j].addNeighbor(grid[i - 1][j - 1]);
//                }
//                if (i < grid.length - 1 && j < grid.length - 1) {
//                    grid[i][j].addNeighbor(grid[i + 1][j + 1]);
//                }
//            }
//        }
//
//        System.out.println("Neighbours done!");
//
//        start = grid[50][50];
//        end = grid[50][195];
//
//
//        double total_h = manh(start, end);
//        start.setH(end);
//        start.updateF();
//
//        System.out.println("Starting Algo..");
//
//        Spot finalNode = aStar(start, end);
//
//        gui = createGui(grid.length, grid[grid.length-1].length);
//
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                if (!grid[i][j].wall) {
//                    MyDrawable gridSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.WHITE, new BasicStroke(1), true);
//                    gui.addMyDrawable(gridSquare);
//                }else{
//
//                    MyDrawable blackSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), true);
//                    gui.addMyDrawable(blackSquare);
//                }
//            }
//        }
//
//        //*** PART OF DRAWING ***
//        reconstruct_path(finalNode);
//
//    }
//
//    public static void doubleArrToStringFile(boolean boolArr[][]){
//        StringBuilder arr = new StringBuilder();
//
//        for(int i =0; i<boolArr.length; i++){
//            for(int j =0; j<boolArr[i].length; j++){
//                if (boolArr[j][i]) {
//                    arr.append(0);
//                } else {
//                    arr.append(1);
//                }
//            }
//            arr.append("\n");
//        }
//
//        PrintWriter out = null;
//        try {
//            out = new PrintWriter("string_arr.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        out.print(arr);
//        out.close();
//
//    }
//
//    public static void reconstruct_path(Spot curr) {
//        while (curr != null) {
//            MyDrawable blue = new MyDrawable(new Ellipse2D.Double(curr.getX() * w, curr.getY() * h, w, h), new Color(0f, 0f, 1f, 1f), new BasicStroke(0), true);
//            gui.addMyDrawable(blue);
//            curr = curr.getPrevious();
//        }
//    }
//
//}
//
