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

public class CategoryDetailScreen {
    private final BibliotecaMainScreen bibliotecaMainScreen;
    private String categoryType = "Movie";
    protected JButton movieButton = new JButton("Movie");
    protected JButton bookButton = new JButton("Book");
    protected JTextField categoryName = new JTextField();
    protected JButton enterButton = new JButton("Enter");
    protected JButton backButton = new JButton("Back");

    public CategoryDetailScreen(BibliotecaMainScreen bibliotecaMainScreen) {
        this.bibliotecaMainScreen = bibliotecaMainScreen;


    }

    public void createCategoryGUI(final Document doc, final NodeList[] itemList) {

        final JFrame choiceCategoryFrame = new JFrame("Choose a category");
        choiceCategoryFrame.setSize(300, 70);
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
                createDetailOfSpecificCategory(itemList[0]);
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceCategoryFrame.dispose();
                itemList[0] = doc.getElementsByTagName("book");
                categoryType = "Book";
                createDetailOfSpecificCategory(itemList[0]);
            }
        });
    }

    void itemDetailPanel() throws IOException, SAXException, ParserConfigurationException {

        final Document doc = bibliotecaMainScreen.getLibDocument();
        doc.getDocumentElement().normalize();
        final NodeList[] ItemList = new NodeList[1];

        createCategoryGUI(doc, ItemList);


    }

    private void createDetailOfSpecificCategory(final NodeList categoryList) {
        JPanel bookDetailPane = new JPanel(new GridBagLayout());
        GridBagConstraints comp = new GridBagConstraints();
        final JFrame categoryDetailFrame = new JFrame("Enter the book name for more details");


        comp.fill = GridBagConstraints.HORIZONTAL;
        comp.gridx = 0;
        comp.gridy = 0;
        bookDetailPane.add(categoryName, comp);


        comp.fill = GridBagConstraints.HORIZONTAL;
        comp.gridx = 0;
        comp.gridy = 2;
        bookDetailPane.add(enterButton, comp);

        comp.fill = GridBagConstraints.HORIZONTAL;
        comp.gridx = 2;
        comp.gridy = 2;
        bookDetailPane.add(backButton, comp);

        if (categoryType.equals("Book")) {
            enterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean foundName = false;
                    String bookName = categoryName.getText();
                    if (bookName.isEmpty()) {
                        JOptionPane.showMessageDialog(categoryDetailFrame, "You didn't enter a book name, please enter a book name",
                                "Invalid input", JOptionPane.ERROR_MESSAGE);
                    }
                    for (int i = 0; i < categoryList.getLength(); i++) {

                        Node bookNode = categoryList.item(i);

                        if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element bookElement = (Element) bookNode;

                            String bookXmlName = bookElement.getElementsByTagName("name").item(0).getTextContent();
                            String bookXmlAuthor = bookElement.getElementsByTagName("author").item(0).getTextContent();
                            String bookXmlYear = bookElement.getElementsByTagName("year").item(0).getTextContent();
                            if (bookName.toLowerCase().trim().equals(bookXmlName.toLowerCase().trim())) {
                                JOptionPane.showMessageDialog(categoryDetailFrame,
                                        "Name : " + bookXmlName + "\n" +
                                                "Author : " + bookXmlAuthor + "\n" +
                                                "Year Published : " + bookXmlYear + "\n", "Book Information", JOptionPane.INFORMATION_MESSAGE);
                                foundName = true;
                                break;
                            }
                        }
                    }

                    if (!foundName) {
                        JOptionPane.showMessageDialog(categoryDetailFrame, "The " + categoryType + " you entered doesn't exist at this library!",
                                "Invalid input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    categoryDetailFrame.dispose();
                }
            });
        } else if (categoryType.equals("Movie")) {
            enterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean foundName = false;
                    String movieName = categoryName.getText();
                    if (movieName.isEmpty()) {
                        JOptionPane.showMessageDialog(categoryDetailFrame, "You didn't enter a movie name, please enter a book name",
                                "Invalid input", JOptionPane.ERROR_MESSAGE);
                    }
                    for (int i = 0; i < categoryList.getLength(); i++) {

                        Node movieNode = categoryList.item(i);

                        if (movieNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element movieElement = (Element) movieNode;

                            String movieXmlName = movieElement.getElementsByTagName("name").item(0).getTextContent();
                            String movieXmlDirector = movieElement.getElementsByTagName("director").item(0).getTextContent();
                            String movieXmlYear = movieElement.getElementsByTagName("year").item(0).getTextContent();
                            String movieXmlRating = movieElement.getElementsByTagName("rating").item(0).getTextContent();
                            if (movieName.toLowerCase().trim().equals(movieXmlName.toLowerCase().trim())) {
                                JOptionPane.showMessageDialog(categoryDetailFrame,
                                        "Name : " + movieXmlName + "\n" +
                                                "Director : " + movieXmlDirector + "\n" +
                                                "Year Released : " + movieXmlYear + "\n" +
                                                "Rating : " + movieXmlRating, "Movie Information", JOptionPane.INFORMATION_MESSAGE);
                                foundName = true;
                                break;
                            }
                        }
                    }

                    if (!foundName) {
                        JOptionPane.showMessageDialog(categoryDetailFrame, "The " + categoryType + " you entered doesn't exist at this library!",
                                "Invalid input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    categoryDetailFrame.dispose();
                }
            });
        }
        categoryDetailFrame.getContentPane().add(bookDetailPane);
        categoryDetailFrame.setSize(300, 200);
        categoryDetailFrame.setLocation(400, 400);
        categoryDetailFrame.setVisible(true);
    }
}