package com.li.honedu.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    private static final float DEFAULTQUALITY = 0.7F;

    public ImageUtil() {
    }

    public static void resize(File originalFile, File resizedFile) throws IOException {
        resize(originalFile, resizedFile, 0, 0, 0.0F);
    }

    public static void resize(File originalFile, File resizedFile, float quality) throws IOException {
        resize(originalFile, resizedFile, 0, 0, quality);
    }

    public static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {
        resize(originalFile, resizedFile, newWidth, 0, quality);
    }

    public static void resize(File originalFile, File resizedFile, int newWidth) throws IOException {
        resize(originalFile, resizedFile, newWidth, 0, 0.0F);
    }

    public static void resize(File originalFile, File resizedFile, int newWidth, int newHeight) throws IOException {
        resize(originalFile, resizedFile, newWidth, newHeight, 0.0F);
    }

    public static void resize(File originalFile, File resizedFile, int newWidth, int newHeight, float quality) throws IOException {
        resize(originalFile, resizedFile, newWidth, newHeight, 0.0F, false);
    }

    public static void resize(File originalFile, File resizedFile, int newWidth, int newHeight, float quality, boolean isPoint) throws IOException {
        Image img = ImageIO.read(originalFile);
        resizeImg(resizedFile, newWidth, newHeight, quality, isPoint, img);
    }

    private static void resizeImg(File resizedFile, int newWidth, int newHeight, float quality, boolean isPoint, Image img) throws IOException {
        if (quality <= 0.0F || quality >= 1.0F) {
            quality = 0.7F;
        }

        if (isPoint) {
            Point originalPoint = new Point(img.getWidth((ImageObserver)null), img.getHeight((ImageObserver)null));
            Point resizedPoint = new Point(newWidth, newHeight);
            Point newPoint = getNewSize(originalPoint, resizedPoint);
            newWidth = newPoint.x;
            newHeight = newPoint.y;
        } else {
            int iWidth = img.getWidth((ImageObserver)null);
            int iHeight = img.getHeight((ImageObserver)null);
            if (newWidth <= 0 && newHeight <= 0) {
                newWidth = iWidth;
                newHeight = iHeight;
            } else if (iWidth > iHeight) {
                newHeight = newWidth * iHeight / iWidth;
            } else {
                newHeight = newWidth;
                newWidth = newWidth * iWidth / iHeight;
            }
        }

        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, 1);
        Graphics g = bufferedImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(img.getScaledInstance(newWidth, newHeight, 4), 0, 0, (ImageObserver)null);
        g.dispose();
        ImageIO.write(bufferedImage, "JPEG", resizedFile);
    }

    public static void resize(byte[] imageByte, File resizedFile, int newWidth, int newHeight, float quality, boolean isPoint) throws IOException {
        ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
        Image img = ImageIO.read(byteInput);
        resizeImg(resizedFile, newWidth, newHeight, quality, isPoint, img);
    }

    public static Point getNewSize(Point imgSize, Point setSize) {
        Point result = new Point();
        float whImgRatio = (float)(imgSize.x / imgSize.y);
        float whSetRatio = (float)(setSize.x / setSize.y);
        if (whImgRatio == whSetRatio) {
            result.x = imgSize.x < setSize.x ? imgSize.x : setSize.x;
            result.y = imgSize.y < setSize.y ? imgSize.y : setSize.y;
        } else if (whImgRatio > whSetRatio) {
            result.x = setSize.x;
            result.y = setSize.x * imgSize.y / imgSize.x;
        } else {
            result.x = setSize.y * imgSize.x / imgSize.y;
            result.y = setSize.y;
        }

        return result;
    }

    public static void pressImage(File targetImg, int startX, int startY, int x, int y, float alpha, float averageGray) {
        try {
            if (averageGray > 255.0F || averageGray < 0.0F) {
                averageGray = 128.0F;
            }

            BufferedImage src = ImageIO.read(targetImg);
            int width = src.getWidth((ImageObserver)null);
            int height = src.getHeight((ImageObserver)null);
            BufferedImage image = new BufferedImage(width, height, 1);
            Graphics2D graphics = image.createGraphics();
            graphics.drawImage(src, 0, 0, width, height, (ImageObserver)null);
            graphics.setComposite(AlphaComposite.getInstance(10, alpha));
            int[] pixels = getPixArray(startX, startY, x, y);
            float average = getAverageGrap(x - startX, y - startY, pixels);
            File water = null;
            if (average >= averageGray) {
            }

            BufferedImage bufferwater = ImageIO.read((File)water);
            graphics.drawImage(bufferwater, startX, startY, x, y, (ImageObserver)null);
            graphics.dispose();
            ImageIO.write(bufferwater, "JPEG", targetImg);
        } catch (Exception var16) {
            var16.printStackTrace();
        }

    }

    public static void pressText(File targetImg, String pressText, int x, int y) {
        try {
            int averageGray = 128;
            int fontSize = 14;
            Image src = ImageIO.read(targetImg);
            int width = src.getWidth((ImageObserver)null);
            int height = src.getHeight((ImageObserver)null);
            int startX = x + fontSize;
            int startY = y + fontSize / 2;
            int[] pixels = getPixArray(startX, startY, width, height);
            float average = getAverageGrap(width - startX, height - startY, pixels);
            BufferedImage image = new BufferedImage(width, height, 1);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, (ImageObserver)null);
            if (average >= (float)averageGray) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }

            g.setFont(new Font("楷体", 1, 14));
            g.drawString(pressText, width - fontSize - x, height - fontSize / 2 - y);
            g.dispose();
            ImageIO.write(image, "JPEG", targetImg);
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

    private static int[] getPixArray(int startX, int startY, int w, int h) {
        int[] pix = new int[(w - startX) * (h - startY)];
        return pix;
    }

    private static float getAverageGrap(int width, int height, int[] pixels) {
        ColorModel colorModel = ColorModel.getRGBdefault();
        int j = 0;
        float average = 0.0F;

        int i;
        for(i = 0; i < height; ++i) {
            for(j = 0; j < width; ++j) {
                int k = i * width + j;
                int r = colorModel.getRed(pixels[k]);
                int g = colorModel.getGreen(pixels[k]);
                int b = colorModel.getBlue(pixels[k]);
                int gray = r * 38 + g * 75 + b * 15 >> 7;
                average += (float)gray;
            }
        }

        average /= (float)((i - 1) * (j - 1));
        return average;
    }

    public static void main(String[] args) throws IOException {
        File f1 = new File("D:\\test\\3.gif");
        File f2 = new File("D:\\test\\530-367-3.gif");
        resize((File)f1, f2, 530, 367, 0.0F, true);
    }
}
