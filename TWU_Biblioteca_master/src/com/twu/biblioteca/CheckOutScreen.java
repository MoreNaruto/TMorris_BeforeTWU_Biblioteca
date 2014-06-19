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

public class CheckOutScreen implements Serializable {

    private final BibliotecaMainScreen bibliotecaMainScreen;
    protected JTextField userItemName = new JTextField(10);
    protected JButton enterButton = new JButton("Enter");
    protected JButton backButton = new JButton("Back");
    protected String[] categoryTitles = new String[] {"book", "movie"};
    protected final JComboBox<String> categoryList = new JComboBox<String>(categoryTitles);

    public CheckOutScreen(BibliotecaMainScreen bibliotecaMainScreen) {
        this.bibliotecaMainScreen = bibliotecaMainScreen;
    }

    public void checkOutPanel(final String userLogIn) throws ParserConfigurationException, SAXException, IOException {
        final Document doc = bibliotecaMainScreen.getLibDocument();
        doc.getDocumentElement().normalize();
        final NodeList[] bookList = new NodeList[1];

        final JFrame bookCheckOutFrame = new JFrame("Check out Book");

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


        bookCheckOutFrame.getContentPane().add(mainPanel);
        bookCheckOutFrame.setSize(300, 200);
        bookCheckOutFrame.setLocation(400, 400);
        bookCheckOutFrame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookCheckOutFrame.dispose();
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryList.getSelectedItem();
                bookList[0] = doc.getElementsByTagName(selectedCategory);
                boolean canCheckOut = false;
                boolean foundName = false;
                String itemName = userItemName.getText();
                String customerName = userLogIn;
                if (itemName.isEmpty()) {
                    JOptionPane.showMessageDialog(bookCheckOutFrame, "You didn't enter a " + selectedCategory + " name, please enter a name",
                            "Invalid input", JOptionPane.ERROR_MESSAGE);
                } else {

                    for (int i = 0; i < bookList[0].getLength(); i++) {

                        Node bookNode = bookList[0].item(i);

                        if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element bookElement = (Element) bookNode;

                            String itemXmlName = bookElement.getElementsByTagName("name").item(0).getTextContent();
                            String avaliableXml = bookElement.getElementsByTagName("avaliable").item(0).getTextContent();

                            if (itemName.toLowerCase().trim().equals(itemXmlName.toLowerCase().trim())) {
                                foundName = true;

                                if (avaliableXml.equals("yes")) {
                                    canCheckOut = true;
                                    NodeList list = bookNode.getChildNodes();
                                    for (int j = 0; j < list.getLength(); j++) {
                                        Node node = list.item(j);
                                        if ("avaliable".equals(node.getNodeName())) {
                                            node.setTextContent("no");
                                        }
                                        if ("customer".equals(node.getNodeName())) {
                                            node.setTextContent(customerName);
                                        }
                                    }
                                    try {
                                        bibliotecaMainScreen.updateLibXml(doc);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                    JOptionPane.showMessageDialog(bookCheckOutFrame, "" + customerName + " has checked out:"
                                            + itemXmlName, "" + itemXmlName + " checked out", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    canCheckOut = false;
                                    JOptionPane.showMessageDialog(bookCheckOutFrame,
                                            "You can not check out this " + selectedCategory + ", it's unavaliable",
                                            "Already Checked out", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }

                    if (!foundName) {
                        JOptionPane.showMessageDialog(bookCheckOutFrame,
                                "The " + selectedCategory + " you entered doesn't exist at this library!",
                                "Invalid input", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
    }
}