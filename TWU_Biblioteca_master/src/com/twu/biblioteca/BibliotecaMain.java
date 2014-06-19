package com.twu.biblioteca;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BibliotecaMain {

    public JButton closeMe = new JButton("Close");
    public BibliotecaMain(){

        welcomeMenuGUI();
    }
    public static void main(String [] args) {

        new BibliotecaMain();
    }
    protected void welcomeMenuGUI() {
        final JFrame welcomeFrame = new JFrame("Welcome to TW Biblioteca");
        welcomeFrame.setBounds(800, 500, 400, 75);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        closeMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.setVisible(false);
                try {
                    mainMenu();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (SAXException e1) {
                    e1.printStackTrace();
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        welcomeFrame.getContentPane().add(closeMe);
        welcomeFrame.setVisible(true);
    }

    private void mainMenu() throws IOException, SAXException, ParserConfigurationException {

        BibliotecaMainScreen mainMenuScreen = new BibliotecaMainScreen();
        mainMenuScreen.runMain();
    }
}
