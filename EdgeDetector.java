package com.bham.pij.assignments.edgedetector;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.UIManager.getColor;

public class EdgeDetector {


    final float[] filter = {-1.0f, -1.0f, -1.0f, -1.0f, 8.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    Color[][] bordered;
    int h;
    int w;

    public Image filterImage(Image image) {

        h = (int) image.getHeight();
        w = (int) image.getWidth();

        bordered = getPixelDataExtended(image);
        Color[][] gray = applyGreyscale(bordered);
        Color[][] fin = applyFilter(gray, createFilter());


        WritableImage wimg = new WritableImage(fin.length, fin.length);
        PixelWriter pw = wimg.getPixelWriter();
        for (int i = 1; i < fin.length - 1; i++) {
            for (int j = 1; j < fin.length - 1; j++) {
                pw.setColor(i, j, fin[i][j]);
            }
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(wimg, null);
        try {
            ImageIO.write(bImage, "png", new File("newImg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wimg;

    }

    public float[][] createFilter() {

        float[][] mmt = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};

        return mmt;
    }

    public Color[][] getPixelDataExtended(Image image) {

        PixelReader pl = image.getPixelReader();
        Color[][] img = new Color[(int) (image.getWidth() + 2)][(int) (image.getHeight() + 2)];
        Color black = new Color(0, 0, 0, 1.0);

        for (int x = 0; x < image.getHeight() + 2; x++) {
            for (int y = 0; y < image.getWidth() + 2; y++) {

                if (x == 0 || y == 0 || x == image.getHeight() + 1 || y == image.getWidth() + 1) {

                    img[x][y] = black;
                } else {

                    img[x][y] = pl.getColor(x - 1, y - 1);

                }

            }
        }

        return img;
    }

    public Color[][] getPixelData(Image image) {


        PixelReader br = image.getPixelReader();
        Color[][] pic = new Color[(int) (image.getWidth() - 1)][(int) (image.getWidth() - 1)];

        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {

                pic[x][y] = br.getColor(x - 1, y - 1);
            }
        }
        return pic;

    }

    public Color[][] applyGreyscale(Color[][] pixels) {

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {

                double r = (pixels[i][j].getRed() + pixels[i][j].getBlue() + pixels[i][j].getGreen()) / 3;
                double b = (pixels[i][j].getRed() + pixels[i][j].getBlue() + pixels[i][j].getGreen()) / 3;
                double g = (pixels[i][j].getRed() + pixels[i][j].getBlue() + pixels[i][j].getGreen()) / 3;

                double avg = (r + b + g) / 3;
                Color grey = new Color(avg, avg, avg, 1);

                pixels[i][j] = grey;
            }
        }
        return pixels;
    }

    public Color[][] applyFilter(Color[][] pixels, float[][] filter) {
        Color[][] applied = new Color[pixels.length][pixels.length];
        Color[][] finalized = new Color[applied.length - 2][applied.length - 2];

        for (int i = 1; i < pixels.length - 1; i++) {
            for (int j = 1; j < pixels.length - 1; j++) {


                double edited = ((filter[0][0] * pixels[i - 1][j - 1].getBlue()) +
                        (filter[0][1] * pixels[i - 1][j].getBlue()) +
                        (filter[0][2] * pixels[i - 1][j + 1].getBlue()) +
                        (filter[1][0] * pixels[i][j - 1].getBlue()) +
                        (filter[1][1] * pixels[i][j].getBlue()) +
                        (filter[1][2] * pixels[i][j + 1].getBlue()) +
                        (filter[2][0] * pixels[i + 1][j - 1].getBlue()) +
                        (filter[2][1] * pixels[i + 1][j].getBlue()) +
                        (filter[2][2] * pixels[i + 1][j + 1].getBlue()));

                if (edited < 0.0) {
                    edited = 0.0;
                }
                if (edited > 1.0) {
                    edited = 1.0;
                }
                Color singlePixel = new Color(edited, edited, edited, 1.0);
                applied[i][j] = singlePixel;
            }
        }
                for (int x = 1; x < pixels.length - 1; x++) {
                    for (int y = 1; y < pixels.length - 1; y++) {
                        finalized[x - 1][y - 1] = applied[x][y];
                    }


                }



        return finalized;

    }

}



