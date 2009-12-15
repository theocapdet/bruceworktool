/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author zx04741
 */
public class ImageSerializableTest {

    static void image1() {
        try {



            Image image = ImageIO.read(new File("C:\\Documents and Settings\\ZX04741\\My Documents\\My Pictures\\5f856760-3d7c-318a-b4a7-e6142c9e1d62.jpg"));

            int w = image.getWidth(null);
            int h = image.getHeight(null);
            int[] pixels = image != null ? new int[w * h] : null;
            System.out.println(pixels[pixels.length-1]);
            if (image != null) {
                try {
                    PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, pixels, 0, w);
                    pg.grabPixels();
                    if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                        throw new IOException("failed to load image contents");
                    }
                } catch (InterruptedException e) {
                    throw new IOException("image load interrupted");
                }
            }
            System.out.println(pixels[pixels.length-1]);
            
            if (pixels != null) {
                Toolkit tk = Toolkit.getDefaultToolkit();
                ColorModel cm = ColorModel.getRGBdefault();
                image = tk.createImage(new MemoryImageSource(w, h, cm, pixels, 0, w));
            }
            BufferedImage bi=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            ImageIO.write(bi, "jpg", new File("C:/a.jpg"));
        }  catch (IOException ex) {
            Logger.getLogger(ImageSerializableTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void image2() {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Documents and Settings\\ZX04741\\My Documents\\My Pictures\\5f856760-3d7c-318a-b4a7-e6142c9e1d62.jpg"));
            ImageIO.write(image, "jpg", new File("C:/a.jpg"));


        } catch (IOException ex) {
            Logger.getLogger(ImageSerializableTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) {

        image1();
    }
}


