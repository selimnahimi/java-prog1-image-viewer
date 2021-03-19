package com.prog1.kepnezegeto;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener{

    private JLabel labelFile; //Ez egy label a gomb mellett
    private JButton buttonFileChoose; //ez egy gomb
    private JLabel labelImage;

    private final JFileChooser openFileChooser; //ez az ablak lesz a file valaszto
    private BufferedImage originalImage; //ezt a kepet toltjuk be

    public Main(){
        super("BasicFileChooser"); //ez lesz a neve az ablaknak

        initComponents(); //meghivjuk az initComponenets fuggvenyt, ami inicializalja az ablak kinezetet

        openFileChooser = new JFileChooser(); //erteket adunk a file valasztonak
        openFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //beallitjuk az alapvetk konyvtarat

        openFileChooser.setFileFilter(new FileNameExtensionFilter("BMP images", "bmp"));
        openFileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png")); //ide irjuk milyen filetipusok lehetnek
        openFileChooser.setFileFilter(new FileNameExtensionFilter("JPG images", "jpg"));

        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents(){
        labelFile = new JLabel(""); //hozzaadjuk a cuccokat az ablakhoz
        buttonFileChoose = new JButton("Open File");

        setLayout(new FlowLayout());

        add(labelFile);
        add(buttonFileChoose);

        labelImage = new JLabel();
        labelImage.setSize(500, 500);
        add(labelImage);

        buttonFileChoose.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event){
        int returnValue = openFileChooser.showOpenDialog(this);

        if(returnValue == JFileChooser.APPROVE_OPTION){ //meg lett e nyiva file vagy nem
            try{
                originalImage = ImageIO.read(openFileChooser.getSelectedFile()); //beolvassuk a kepet
                labelFile.setText("Image file successfully loaded!");

                //csinalunk egy uj labelt, es atmeretezzuk a kepet, majd rarakjuk arra

                BufferedImage resizedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(originalImage, 0, 0, 500, 500, null);
                g.dispose();
                labelImage.setIcon(new ImageIcon(resizedImage));



            }catch (IOException ioe){
                labelFile.setText("No file choosen!");
            }
        }
        else{
            labelFile.setText("No file choosen");
        }
    }

    public static void main(String[] args) {
        //Ez csak megnyitja az ablakot, elinditja a GUI-t
        new Main().setVisible(true);
    }

}
