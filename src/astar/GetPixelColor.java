package astar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

//-2476490 == dark red (classroom)
//-534826 == light red (corridor)

public class GetPixelColor {

    protected static void scalePNG(String path, String newPath, int targetSize){
        try {
            BufferedImage scaledImage = Scalr.resize(ImageIO.read(new File(path)), targetSize);
            ImageIO.write(scaledImage, "png", new File(newPath));
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    protected static boolean[][] createBinaryArr(int[][] arrRGB){
        boolean[][] boolArr = new boolean[arrRGB.length][arrRGB[arrRGB.length-1].length];
        for(int i = 0; i<arrRGB.length; i++){
            for(int j = 0; j<arrRGB[i].length; j++) {
                //System.out.println(result[i][j]);
                if (arrRGB[i][j] != 0) {
                    if (arrRGB[i][j] != -534826) {
                        boolArr[i][j] = true;
                    } else {
                        boolArr[i][j] = false;
                    }
                }
            }
        }
        return boolArr;
    }





    protected static int[][] getRGBarray(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }
}
