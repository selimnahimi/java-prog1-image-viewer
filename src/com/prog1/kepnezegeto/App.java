package com.prog1.kepnezegeto;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;

import com.prog1.kepnezegeto.lib.FormatHandler;
import com.prog1.kepnezegeto.lib.IFormat;

public class App{

    private JButton button1;
    private JPanel panel1;
    private JLabel label1;
    private JLabel image;

    private final JFileChooser openFileChooser; //ez az ablak lesz a file valaszto
    private BufferedImage originalImage; //ezt a kepet toltjuk be

    public App() {
        FormatHandler formatHandler = new FormatHandler();
        java.util.List<IFormat> list = formatHandler.getFormats();

        for (IFormat format: list) {
            System.out.println(format.getExtensions()[0]);
        }

        openFileChooser = new JFileChooser(); //erteket adunk a file valasztonak
        openFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //beallitjuk az alapvetk konyvtarat

        //openFileChooser.setFileFilter(new FileNameExtensionFilter("BMP images", "bmp"));
        //openFileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png")); //ide irjuk milyen filetipusok lehetnek
        //openFileChooser.setFileFilter(new FileNameExtensionFilter("JPG images", "jpg"));

        for (IFormat format: list) {
            String extension = format.getExtensions()[0];
            openFileChooser.setFileFilter(new FileNameExtensionFilter(extension, format.getExtensions()));
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = openFileChooser.showOpenDialog(button1);

                if(returnValue == JFileChooser.APPROVE_OPTION){ //meg lett e nyiva file vagy nem
                    try{
                        originalImage = ImageIO.read(openFileChooser.getSelectedFile()); //beolvassuk a kepet
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
                }
                else{
                    label1.setText("No file choosen");
                }
            }
        });
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
