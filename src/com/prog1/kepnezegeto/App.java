package com.prog1.kepnezegeto;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

import com.prog1.kepnezegeto.lib.*;

public class App extends JFrame {
    public static App mainApp;

    private JButton buttonOpenFile;
    private JPanel panel1;
    private JLabel label1;
    private JButton buttonSaveFile;
    public JLabel labelImage = null;
    private JPanel panelImage;

    private FormatHandler formatHandler;
    private OperationHandler operationHandler;

    private JMenuBar menuBar;
    private JMenu menu;
    private ArrayList<JMenuItem> menuItems;

    public static int RED;
    public static int GREEN;
    public static int BLUE;

    private final JFileChooser openFileChooser; //ez az ablak lesz a file valaszto
    public BufferedImage originalImage; //ezt a kepet toltjuk be
    public BufferedImage resizedImage;
    public double rotationAngle = 0;

    public void setImage(BufferedImage image) {
        this.originalImage = image;
        resize();
    }

    public App() {
        App.mainApp = this;

        formatHandler = new FormatHandler();
        operationHandler = new OperationHandler();

        openFileChooser = new JFileChooser(); //erteket adunk a file valasztonak
        openFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //beallitjuk az alapvetk konyvtarat

        for (IFormat format : formatHandler.getClassList()) {
            String extension = format.getExtensions()[0];
            openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
        }

        menuBar = new JMenuBar();
        menu = new JMenu("Operations");
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        // Menük hozzáadása OperationHandler-ből
        menuItems = new ArrayList<JMenuItem>();
        for (IOperation operation : operationHandler.getClassList()) {
            String name = operation.getName();
            JMenuItem menuItem = new JMenuItem(name);

            menuItems.add(menuItem);
            menu.add(menuItem);

            // Actionlistener, mikor rákattintunk
            menuItem.addActionListener((ActionEvent e) -> {
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BufferedImage image = (BufferedImage) this.getValue("image");
                        setImage(image);
                    }
                };

                operation.execute(this.originalImage, action);
            });
        }

        buttonOpenFile.addActionListener((ActionEvent e) -> {
            openFileChooser.resetChoosableFileFilters();
            formatHandler.reload();

            for (IFormat format : formatHandler.getClassList()) {
                String extension = format.getExtensions()[0];
                openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
            }

            int returnValue = openFileChooser.showOpenDialog(buttonOpenFile);

            if (returnValue == JFileChooser.APPROVE_OPTION) { //meg lett e nyiva file vagy nem
                try {
                    File file = openFileChooser.getSelectedFile();
                    IFormat format = formatHandler.whichFormat(file.getName());
                    if (format == null) throw new IOException();
                    //originalImage = format.loadFile(file); //beolvassuk a kepet
                    setImage(format.loadFile(file));
                    label1.setText("Image file successfully loaded!");

                } catch (IOException ioe) {
                    label1.setText("No file choosen!");
                }
                // TODO: Format error lekezelés
            } else {
                label1.setText("No file choosen");
            }
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if(originalImage!=null){
                    resize();
                }

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

        buttonSaveFile.addActionListener((ActionEvent e) -> {
            // TODO: fájlok elmentése
        });

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(500, 500);
    }

    public void resize() {

        int xOriginal = originalImage.getWidth();
        int yOriginal = originalImage.getHeight();
        double ratio = (double) xOriginal / (double) yOriginal;

        int xNew = 0;
        int yNew = 0;

        if (panel1.getWidth() > panel1.getHeight()) {
            xNew = panel1.getWidth() - 100;
            yNew = (int) (xNew / ratio);
            if (yNew > panel1.getHeight() - 100) {
                yNew = panel1.getHeight() - 100;
                if (yNew < 0) {
                    yNew = 0;
                }
                xNew = (int) (yNew * ratio);
            }
        }
        if (panel1.getWidth() <= panel1.getHeight()) {
            yNew = panel1.getHeight() - 100;
            xNew = (int) (yNew * ratio);
            if (xNew > panel1.getWidth() - 100) {
                xNew = panel1.getWidth() - 100;
                if (xNew < 0) {
                    xNew = 0;
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

    /*public BufferedImage convert(BufferedImage image) {
        BufferedImage newImage = new BufferedImage();

        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, xNew, yNew, null);
        g.dispose();
        labelImage.setIcon(new ImageIcon(resizedImage));
    }*/

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

    public static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    public static BufferedImage ColorEdit(BufferedImage image,int r,int g, int b) {
        LookupTable lookup = new LookupTable(0, 3) {
            @Override
            public int[] lookupPixel(int[] src, int[] dest) {
                if(src[0]+r<255 && src[0]+r>0){
                    dest[0] = (int) (src[0]+r);
                }if(src[0]+r> 255){
                    dest[0] = (int) (255);
                }
                if(src[0]+r< 0){
                    dest[0] = (int) (0);
                }
                if(src[1]+g<255 && src[1]+g>0){
                    dest[1] = (int) (src[1]+g);
                }if(src[1]+g> 255){
                    dest[1] = (int) (255);
                }
                if(src[1]+g< 0){
                    dest[1] = (int) (0);
                }
                if(src[2]+b<255 && src[2]+b>0){
                    dest[2] = (int) (src[2]+b);
                }if(src[2]+b> 255){
                    dest[2] = (int) (255);
                }
                if(src[2]+b< 0){
                    dest[2] = (int) (0);
                }
                return dest;
            }
        };
        LookupOp op = new LookupOp(lookup, new RenderingHints(null));
        return op.filter(image, null);
    }

    public static BufferedImage createInverted(BufferedImage image) {
        LookupTable lookup = new LookupTable(0, 3) {
            @Override
            public int[] lookupPixel(int[] src, int[] dest) {
                dest[0] = (int) (255 - src[0]);
                dest[1] = (int) (255 - src[1]);
                dest[2] = (int) (255 - src[2]);
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
