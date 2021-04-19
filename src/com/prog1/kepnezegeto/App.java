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
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

import com.prog1.kepnezegeto.lib.ClassHandler;
import com.prog1.kepnezegeto.lib.FormatHandler;
import com.prog1.kepnezegeto.lib.IFormat;

public class App extends JFrame{

    private JButton buttonOpenFile;
    private JPanel panel1;
    private JLabel label1;
    private JButton buttonSaveFile;
    private JLabel labelImage = null;
    private JPanel panelImage;
    private FormatHandler formatHandler;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;

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

        menuBar = new JMenuBar();
        menu = new JMenu("Operations");
        menuBar.add(menu);
        menuItem = new JMenuItem("Flip");
        menuItem1 = new JMenuItem("Rotate");
        menuItem2 = new JMenuItem("Invert");
        menu.add(menuItem);
        menu.add(menuItem1);
        menu.add(menuItem2);

        this.setJMenuBar(menuBar);

        buttonOpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser.resetChoosableFileFilters();
                formatHandler.reload();

                for (IFormat format: formatHandler.getClassList()) {
                    String extension = format.getExtensions()[0];
                    openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
                }

                int returnValue = openFileChooser.showOpenDialog(buttonOpenFile);

                if(returnValue == JFileChooser.APPROVE_OPTION){ //meg lett e nyiva file vagy nem
                    try{
                        File file = openFileChooser.getSelectedFile();
                        IFormat format = formatHandler.whichFormat(file.getName());

                        if (format == null) throw new IOException();

                        originalImage = format.loadFile(file); //beolvassuk a kepet

                        int xOriginal = originalImage.getWidth();
                        int yOriginal = originalImage.getHeight();
                        double ratio = (double)xOriginal / (double)yOriginal;

                        int xNew = 0;
                        int yNew = 0;

                        if(panel1.getWidth()>panel1.getHeight()){
                            xNew= panel1.getWidth()-100;
                            yNew=(int)(xNew/ratio);
                        }
                        if(panel1.getWidth()<=panel1.getHeight()){
                            yNew=panel1.getHeight()-100;
                            xNew=(int)(yNew*ratio);
                        }

                        label1.setText("Image file successfully loaded!");

                        //csinalunk egy uj labelt, es atmeretezzuk a kepet, majd rarakjuk arra

                        BufferedImage resizedImage = new BufferedImage(xNew, yNew, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = resizedImage.createGraphics();
                        g.drawImage(originalImage, 0, 0, xNew, yNew, null);
                        g.dispose();
                        labelImage.setIcon(new ImageIcon(resizedImage));



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

        buttonSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: fájlok elmentése

                // Effekt
                originalImage = rotate(originalImage, Math.PI/2);

                labelImage.setIcon(new ImageIcon(originalImage));
            }
        });

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(500,500);
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
    private static BufferedImage createInverted(BufferedImage image)
    {
        LookupTable lookup = new LookupTable(0, 4)
        {
            @Override
            public int[] lookupPixel(int[] src, int[] dest)
            {
                dest[0] = (int)(255-src[0]);
                dest[1] = (int)(255-src[1]);
                dest[2] = (int)(255-src[2]);
                return dest;
            }
        };
        LookupOp op = new LookupOp(lookup, new RenderingHints(null));
        return op.filter(image, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }

}
