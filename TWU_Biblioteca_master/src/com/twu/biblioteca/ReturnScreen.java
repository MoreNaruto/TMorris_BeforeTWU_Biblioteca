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
import java.io.Serializable;

/**
 * Created by morrista on 6/12/2014.
 */
public class ReturnScreen implements Serializable {
    private final BibliotecaMainScreen bibliotecaMainScreen;
    protected JTextField userItemName = new JTextField(10);
    protected JButton enterButton = new JButton("Enter");
    protected JButton backButton = new JButton("Back");
    protected String[] categoryTitles = new String[] {"book", "movie"};
    protected final JComboBox categoryList = new JComboBox(categoryTitles);

    public ReturnScreen(BibliotecaMainScreen bibliotecaMainScreen) {
        this.bibliotecaMainScreen = bibliotecaMainScreen;
    }

    public void returnPanel(final String userLogIn) throws ParserConfigurationException, SAXException, IOException {
        final Document doc = bibliotecaMainScreen.getLibDocument();
        doc.getDocumentElement().normalize();
        final NodeList[] bookList = new NodeList[1];

        final JFrame returnBookFrame = new JFrame("Return Book");

        JPanel mainPanel = new JPanel();
        JPanel bookNamePanel = new JPanel(new BorderLayout());

        JLabel bookNameLabel = new JLabel("Name: ");

        bookNamePanel.add(bookNameLabel, BorderLayout.WEST);
        bookNamePanel.add(userItemName, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new BorderLayout());

        buttonsPanel.add(enterButton, BorderLayout.WEST);
        buttonsPanel.add(backButton, BorderLayout.EAST);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        mainPanel.add(categoryList);
        mainPanel.add(bookNamePanel);
        mainPanel.add(buttonsPanel);

        returnBookFrame.getContentPane().add(mainPanel);
        returnBookFrame.setSize(300, 200);
        returnBookFrame.setLocation(400, 400);
        returnBookFrame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBookFrame.dispose();
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryList.getSelectedItem();
                bookList[0] = doc.getElementsByTagName(selectedCategory);
                boolean canReturn = false;
                boolean foundName = false;
                String itemName = userItemName.getText();
                String customerName = userLogIn;
                if (itemName.isEmpty()) {
                    JOptionPane.showMessageDialog(returnBookFrame, "You didn't enter a book name, please enter a book name",
                            "Invalid input", JOptionPane.ERROR_MESSAGE);
                }  else {

                for (int i = 0; i < bookList[0].getLength(); i++) {

                    Node bookNode = bookList[0].item(i);

                    if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;

                        String bookXmlName = bookElement.getElementsByTagName("name").item(0).getTextContent();
                        String avaliableXml = bookElement.getElementsByTagName("avaliable").item(0).getTextContent();
                        String customerXml = bookElement.getElementsByTagName("customer").item(0).getTextContent();

                        if (itemName.toLowerCase().trim().equals(bookXmlName.toLowerCase().trim())) {
                            foundName = true;

                            if (avaliableXml.equals("yes")) {
                                canReturn = false;
                                JOptionPane.showMessageDialog(returnBookFrame, "The " + selectedCategory + ", " +
                                                itemName + ", is already in the library",
                                        "" + itemName + " already in Library", JOptionPane.ERROR_MESSAGE);
                            } else if (!customerName.toLowerCase().trim().equals(customerXml.toLowerCase().trim())) {
                                canReturn = false;
                                JOptionPane.showMessageDialog(returnBookFrame, "You don't have the " + selectedCategory +", " +
                                        customerXml + " has the book",
                                        "You are not the book owner", JOptionPane.ERROR_MESSAGE);
                            } else {
                                canReturn = true;
                                NodeList list = bookNode.getChildNodes();
                                for (int j = 0; j < list.getLength(); j++) {
                                    Node node = list.item(j);
                                    if ("avaliable".equals(node.getNodeName())) {
                                        node.setTextContent("yes");
                                    }
                                    if ("customer".equals(node.getNodeName())) {
                                        node.setTextContent("none");
                                    }
                                }
                                try {
                                    bibliotecaMainScreen.updateLibXml(doc);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                JOptionPane.showMessageDialog(returnBookFrame, "" + customerName + " has return the " + selectedCategory + ": "
                                        + itemName, "" + itemName + " returned", JOptionPane.PLAIN_MESSAGE);
                            }

                        }
                    }
                }

                if (!foundName) {
                    JOptionPane.showMessageDialog(returnBookFrame, "The " + selectedCategory + " you entered doesn't exist at this library!",
                            "Invalid input", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        });
    }
}
