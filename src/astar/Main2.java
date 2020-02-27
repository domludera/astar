package astar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static astar.DrawingBoard.createGui;

public class Main2 {

    static final int w = 5;
    static final int h = 5;

    public static DrawingBoard gui;

    public static void main(String[] args) throws IOException {

        int x1 = 50;
        int y1 = 45;
        int x2 = 195;
        int y2 = 48;


        GetPixelColor.scalePNG("media/h8.png", "media/100h8.png", 250);

        BufferedImage image = ImageIO.read(new File("media/100h8.png"));
        boolean[][] boolArr = GetPixelColor.createBinaryArr(GetPixelColor.getRGBarray(image));

        for(boolean[] b_arr : boolArr){
            for(boolean b : b_arr){
                System.out.print(b);
            }
            System.out.println();
        }



//        boolean[][] boolArr = {{false, true, false},{false,false,false},{false,true,false}};

        A_Star2 path = new A_Star2();

        System.out.println("starting a*...");

        Spot finalNode = path.aStar(boolArr, x1, y1, x2, y2);

        System.out.println("done a*...");


        gui = createGui(boolArr.length, boolArr[boolArr.length-1].length);

        for (int i = 0; i < boolArr.length; i++) {
            for (int j = 0; j < boolArr[i].length; j++) {
                if (boolArr[i][j]) {
                    MyDrawable gridSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), true);
                    gui.addMyDrawable(gridSquare);
                }else{

                    MyDrawable blackSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), false);
                    gui.addMyDrawable(blackSquare);
                }
            }
        }


        reconstruct_path(finalNode);

    }

    public static void reconstruct_path(Spot curr) {
        while (curr != null) {
            MyDrawable blue = new MyDrawable(new Ellipse2D.Double(curr.getX() * w, curr.getY() * h, w, h), new Color(0f, 0f, 1f, 1f), new BasicStroke(0), true);
            gui.addMyDrawable(blue);
            curr = curr.getPrevious();
        }
    }
}
