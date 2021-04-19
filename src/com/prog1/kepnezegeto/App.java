package com.prog1.kepnezegeto;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.prog1.kepnezegeto.lib.ClassHandler;
import com.prog1.kepnezegeto.lib.FormatHandler;
import com.prog1.kepnezegeto.lib.IFormat;

public class App extends JFrame{
    public static App mainApp;

    private JButton buttonOpenFile;
    private JPanel panel1;
    private JLabel label1;
    private JButton buttonSaveFile;
    public JLabel labelImage = null;
    private JPanel panelImage;

    private FormatHandler formatHandler;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;

    private final JFileChooser openFileChooser; //ez az ablak lesz a file valaszto
    public BufferedImage originalImage; //ezt a kepet toltjuk be
    public BufferedImage resizedImage;
    public double rotationAngle = 0;

    public App() {
        App.mainApp = this;

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
        menuItem1 = new JMenuItem("Rotate");
        menuItem2 = new JMenuItem("Flip");
        menuItem3 = new JMenuItem("Invert");
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);

        //itt most egyesevel vannak a menuItemok, ha lesz operation class beolvaso, akkor az majd pakolhatja egy listaba ezeket

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
                        label1.setText("Image file successfully loaded!");

                        resize();

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

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                resize();
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });

        buttonSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: fájlok elmentése

            }
        });

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Slider sl = new Slider();
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                originalImage = flip(originalImage);
                resizedImage = flip(resizedImage);
                labelImage.setIcon(new ImageIcon(resizedImage));
            }
        });



        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(500,500);
    }

    public void resize(){

        int xOriginal = originalImage.getWidth();
        int yOriginal = originalImage.getHeight();
        double ratio = (double)xOriginal / (double)yOriginal;

        int xNew = 0;
        int yNew = 0;

        if(panel1.getWidth()>panel1.getHeight()){
            xNew= panel1.getWidth()-100;
            yNew=(int)(xNew/ratio);
            if(yNew > panel1.getHeight()-100){
                yNew=panel1.getHeight()-100;
                if(yNew<0){
                    yNew=0;
                }
                xNew=(int)(yNew*ratio);
            }
        }
        if(panel1.getWidth()<=panel1.getHeight()){
            yNew=panel1.getHeight()-100;
            xNew=(int)(yNew*ratio);
            if(xNew > panel1.getWidth()-100){
                xNew = panel1.getWidth()-100;
                if(xNew<0){
                    xNew=0;
                }
                yNew = (int) (xNew / ratio);
            }
        }

        //csinalunk egy uj labelt, es atmeretezzuk a kepet, majd rarakjuk arra

        resizedImage = new BufferedImage(xNew, yNew, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, xNew, yNew, null);
        g.dispose();
        labelImage.setIcon(new ImageIcon(resizedImage));

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }

}
