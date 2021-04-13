package com.prog1.kepnezegeto;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.prog1.kepnezegeto.lib.ClassHandler;
import com.prog1.kepnezegeto.lib.FormatHandler;
import com.prog1.kepnezegeto.lib.IFormat;

public class App{

    private JButton button1;
    private JPanel panel1;
    private JLabel label1;
    private JLabel image;
    private FormatHandler formatHandler;

    private final JFileChooser openFileChooser; //ez az ablak lesz a file valaszto
    private BufferedImage originalImage; //ezt a kepet toltjuk be

    public App() {
        formatHandler = new FormatHandler();

        openFileChooser = new JFileChooser(); //erteket adunk a file valasztonak
        openFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //beallitjuk az alapvetk konyvtarat

        //openFileChooser.setFileFilter(new FileNameExtensionFilter("BMP images", "bmp"));
        //openFileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png")); //ide irjuk milyen filetipusok lehetnek
        //openFileChooser.setFileFilter(new FileNameExtensionFilter("JPG images", "jpg"));

        for (IFormat format: formatHandler.getClassList()) {
            String extension = format.getExtensions()[0];
            openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser.resetChoosableFileFilters();
                formatHandler.reload();

                for (IFormat format: formatHandler.getClassList()) {
                    String extension = format.getExtensions()[0];
                    openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
                }

                int returnValue = openFileChooser.showOpenDialog(button1);

                if(returnValue == JFileChooser.APPROVE_OPTION){ //meg lett e nyiva file vagy nem
                    try{
                        File file = openFileChooser.getSelectedFile();
                        IFormat format = formatHandler.whichFormat(file.getName());

                        if (format == null) throw new IOException();

                        originalImage = format.loadFile(file); //beolvassuk a kepet
                        label1.setText("Image file successfully loaded!");

                        //csinalunk egy uj labelt, es atmeretezzuk a kepet, majd rarakjuk arra

                        BufferedImage resizedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g = resizedImage.createGraphics();
                        g.drawImage(originalImage, 0, 0, 500, 500, null);
                        g.dispose();
                        image.setIcon(new ImageIcon(resizedImage));



                    }catch (IOException ioe){
                        label1.setText("No file choosen!");
                    }
                    // TODO: Format error lekezelés
                }
                else{
                    label1.setText("No file choosen");
                }
            }
        });
    }
    public static BufferedImage flip(BufferedImage image) {
        BufferedImage flipped = new BufferedImage(image.getWidth(), image.getHeight(),
                image.getType());
        AffineTransform tran = AffineTransform.getTranslateInstance(0,
                image.getHeight());
        AffineTransform flip = AffineTransform.getScaleInstance(1d, -1d);
        tran.concatenate(flip);

        Graphics2D g = flipped.createGraphics();
        g.setTransform(tran);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return flipped;
    }


    public static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,500);
    }

}
