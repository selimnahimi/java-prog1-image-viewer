package com.prog1.kepnezegeto;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.prog1.kepnezegeto.lib.interfaces.IFormat;
import com.prog1.kepnezegeto.lib.interfaces.IOperation;
import com.prog1.kepnezegeto.lib.reflection.FormatHandler;
import com.prog1.kepnezegeto.lib.reflection.OperationHandler;

/**
 * Fő App osztály, a program magja
 */
public class App extends JFrame {
    public static App mainApp;

    private JPanel panel1;
    private JLabel label1;
    public JLabel labelImage;
    private JPanel panelImage;

    private FormatHandler formatHandler;

    private JMenuBar menuBar;
    private JMenu operationMenu;
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem closeMenuItem;
    private JMenuItem helpMenuItem;


    private final JFileChooser openFileChooser; //ez az ablak lesz a file valaszto
    public BufferedImage originalImage; //ezt a kepet toltjuk be
    public BufferedImage resizedImage;

    /**
     * A betöltött kép lecserélése
     *
     * @param image Kép, amire cserélünk
     */
    public void setImage(BufferedImage image) {
        this.originalImage = image;
        resize();
    }

    public App() {
        App.mainApp = this;

        formatHandler = new FormatHandler();

        openFileChooser = new JFileChooser(); //erteket adunk a file valasztonak
        openFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //beallitjuk az alapvetk konyvtarat

        for (IFormat format : formatHandler.getClassList()) {
            String extension = format.getExtensions()[0];
            openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
        }

        menuBar = new JMenuBar();
        operationMenu = new JMenu("Operations");
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        closeMenuItem = new JMenuItem("Close");
        helpMenuItem = new JMenuItem("Help");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(helpMenuItem);
        fileMenu.add(closeMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(operationMenu);
        this.setJMenuBar(menuBar);

        openMenuItem.addActionListener((ActionEvent e) -> {
            openFileChooser.resetChoosableFileFilters();
            formatHandler.reload();

            for (IFormat format : formatHandler.getClassList()) {
                String extension = format.getExtensions()[0];
                openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
            }

            int returnValue = openFileChooser.showOpenDialog(openMenuItem);

            if (returnValue == JFileChooser.APPROVE_OPTION) { //meg lett e nyiva file vagy nem
                try {
                    File file = openFileChooser.getSelectedFile();
                    IFormat format = formatHandler.whichFormat(file.getName());
                    if (format == null) throw new IOException("Unsupported file format!");
                    setImage(format.loadFile(file));

                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                    if (ioe.getMessage() != null) {
                        showOptions(ioe.getMessage());
                    }
                }
            }
        });

        operationMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                loadOperations();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                if (originalImage != null) {
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

        saveMenuItem.addActionListener((ActionEvent e) -> {

            int returnValue = openFileChooser.showSaveDialog(saveMenuItem);

            if (returnValue == JFileChooser.APPROVE_OPTION) { //meg lett e nyiva file vagy nem
                try {
                    File file = openFileChooser.getSelectedFile();
                    IFormat format = formatHandler.whichFormat(file.getName());
                    if (format == null) throw new IOException();
                    format.exportFile(originalImage, file.getAbsolutePath());

                } catch (IOException ioe) {
                    showOptions("Failed saving the image!");
                }
            }
        });

        helpMenuItem.addActionListener((ActionEvent e) -> {
            showOptions("Program használata:\n" +
                    "- A program indítása után a File menüponton belül az Helpre kattintva elolvashatjuk az utmutatót a használatrol. \n" +
                    "- Open-re kattintva egy File chooser jelenik meg ahol a megfelelő file kiterjesztést kiválasztva megtudjuk nyitni az adott képet.\n" +
                    "- Operations menüponton belül kitudjuk választani milyen módosításokat szeretnénk elvégezni\n" +
                    "  - Flip horizontal vizszintes tengelyen tükrözi a képet \n" +
                    "  - Flip vertical függőleges tengelyen tükrözi a képet\n" +
                    "  - Invert invertálja a képet\n" +
                    "  - RGB Edit színskála szerint tudjuk változtatni a kép színét\n" +
                    "  - Rotate kép elforgatása 90 fokkal\n" +
                    "- File menüponton belül a Save-ra kattintva ismét egy File chooser jelenik meg ahol le tudjuk mententeni a képünket");
        });

        closeMenuItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(500, 500);
    }

    /**
     * Operációk betöltése reflekció használatával
     */
    private void loadOperations() {
        OperationHandler operationHandler = new OperationHandler();

        // Menük hozzáadása OperationHandler-ből
        operationMenu.removeAll();

        for (IOperation operation : operationHandler.getClassList()) {
            String name = operation.getName();
            JMenuItem menuItem = new JMenuItem(name);

            operationMenu.add(menuItem);

            // Actionlistener, mikor rákattintunk
            menuItem.addActionListener((ActionEvent e) -> {
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BufferedImage image = (BufferedImage) this.getValue("image");
                        setImage(image);
                    }
                };

                operation.execute(originalImage, action);
            });
        }
    }

    /**
     * Felugró ablak mutatása
     *
     * @param text Szöveg, ami megjelenik
     */
    private void showOptions(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    /**
     * Megjelenített (nem az eredeti) kép átméretezése az ablak méretétől függően
     */
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

        resizedImage = new BufferedImage(xNew, yNew, BufferedImage.OPAQUE);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, xNew, yNew, null);
        g.dispose();
        labelImage.setIcon(new ImageIcon(resizedImage));
    }

    /**
     * Java Swing GraphicsConfiguration lekérdezése a GUI kontextusban
     * @return GraphicsConfiguration
     */
    public static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    /**
     * Main függvény
     * @param args CLI argumentumok
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }

}
