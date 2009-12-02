/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author zx04741
 */
public class ImageHelper {

    public static void writeImage(Image image, String format) {
    }

    public static void readImage() {
    }

    public static byte[] getFormatData(Image img, String format) throws IOException {
        BufferedImage bi = image2Buffer(img);
        Iterator writers = ImageIO.getImageWritersByFormatName(format.toLowerCase());
        if (writers == null || !writers.hasNext()) {
            throw new IllegalArgumentException("Unsupported format (" + format + ")");
        }
        ImageWriter writer = (ImageWriter) writers.next();
        IIOImage iioImg = new IIOImage(bi, null, null);
        ImageWriteParam iwparam = writer.getDefaultWriteParam();
        // if JPEG, set image quality parameters
        if ("jpeg".equalsIgnoreCase(format)) {
            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwparam.setCompressionQuality(1.0f);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writer.setOutput(ImageIO.createImageOutputStream(out));
        writer.write(null, iioImg, iwparam);
        return out.toByteArray();

    }

    public static void paintImg(JLabel label, File f) throws IOException {
        
        int width = (int) label.getPreferredSize().getWidth();
        int height = (int) label.getPreferredSize().getHeight();
        Image image = ImageIO.read(f);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        label.setIcon(new ImageIcon(bi));
    }

    public static BufferedImage loadImage(byte[] data) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        BufferedImage bi = ImageIO.read(in);
        in.close();
        return bi;
    }

    public static BufferedImage image2Buffer(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bi;
    }
}
