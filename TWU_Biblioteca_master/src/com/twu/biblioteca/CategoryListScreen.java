package com.twu.biblioteca;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CategoryListScreen {
    private final BibliotecaMainScreen bibliotecaMainScreen;
    private String categoryType = "Movie";
    private int switcher = 0;
    protected JButton movieButton = new JButton("Movie");
    protected JButton bookButton = new JButton("Book");
    protected JButton backButton = new JButton("Back");

    public CategoryListScreen(BibliotecaMainScreen bibliotecaMainScreen) {
        this.bibliotecaMainScreen = bibliotecaMainScreen;
    }

    public void bookListPanel(int switcher) throws ParserConfigurationException, SAXException, IOException {
        bibliotecaMainScreen.setVisible(false);
        final Document doc = bibliotecaMainScreen.getLibDocument();
        doc.getDocumentElement().normalize();
        final NodeList[] ItemList = new NodeList[1];
        this.switcher = switcher;
        createCategoryGUI(doc, ItemList);


    }

    public void createCategoryGUI(final Document doc, final NodeList[] itemList) {

        final JFrame choiceCategoryFrame = new JFrame("Choose a category");
        choiceCategoryFrame.setSize(300,70);
        choiceCategoryFrame.setLocation(400, 400);
        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(movieButton);
        mainPanel.add(bookButton);
        choiceCategoryFrame.getContentPane().add(mainPanel);
        choiceCategoryFrame.setVisible(true);

        movieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceCategoryFrame.dispose();
                itemList[0] = doc.getElementsByTagName("movie");
                categoryType = "Movie";
                createListOfSpecificCategory(itemList[0]);
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceCategoryFrame.dispose();
                itemList[0] = doc.getElementsByTagName("book");
                categoryType = "Book";
                createListOfSpecificCategory(itemList[0]);
            }
        });
    }

    public void createListOfSpecificCategory(NodeList itemList) {

        final JFrame bookFrame = new JFrame(categoryType + " in Library");
        bookFrame.setSize(300, 300);
        bookFrame.setLocation(400, 400);
        BoxLayout bookListOrder = new BoxLayout(bookFrame.getContentPane(), BoxLayout.Y_AXIS);
        bookFrame.setLayout(bookListOrder);

        if (this.switcher == 0) {
            for (int n = 0; n < itemList.getLength(); n++) {

                Node bookNode = itemList.item(n);

                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNode;
                    JLabel bookLabel = new JLabel();
                    bookLabel.setText(bookElement.getElementsByTagName("name").item(0).getTextContent());
                    bookFrame.add(bookLabel);


                }
            }
        } else {
            for (int n = 0; n < itemList.getLength(); n++) {

                Node bookNode = itemList.item(n);

                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNode;
                    JLabel bookLabel = new JLabel();
                    bookLabel.setText(bookElement.getElementsByTagName("name").item(0).getTextContent()
                            + " is owned by: " + bookElement.getElementsByTagName("customer").item(0).getTextContent());
                    bookFrame.add(bookLabel);
                }
            }
        }
                bookFrame.add(backButton);
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bookFrame.dispose();
                        bibliotecaMainScreen.setVisible(true);
                    }
                });
                bookFrame.setVisible(true);
                bookFrame.pack();
            }
        }
