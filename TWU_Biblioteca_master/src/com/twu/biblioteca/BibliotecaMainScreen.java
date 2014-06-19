package com.twu.biblioteca;

import javax.swing.*;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * Created by morrista on 6/11/2014.
 */
public class BibliotecaMainScreen extends JFrame {

    private final CategoryDetailScreen categoryDetailScreen = new CategoryDetailScreen(this);
    private final CategoryListScreen categoryListScreen = new CategoryListScreen(this);
    private final CheckOutScreen checkOutScreen = new CheckOutScreen(this);
    private final ReturnScreen returnScreen = new ReturnScreen(this);
    public String category = "movie";
    public String userLogIn = "";
    public boolean isLoggedIn = false;
    protected JButton loginInButton = new JButton("Login In");
    protected JButton customerInfoButton = new JButton("Customer Information");
    protected JButton itemListingButton= new JButton("Category Listing");
    protected JButton itemDetailButton = new JButton("Category Detail Button");
    protected JButton checkOutButton = new JButton("Check out");
    protected JButton returnBookButton = new JButton("Return");
    protected JButton logOutButton = new JButton("Login Out");
    protected final JButton itemOwnerButton = new JButton("Book/Movie Owner");
    protected String[] userTitles = new String[] {"customer", "librarian"};
    protected final JComboBox userList = new JComboBox(userTitles);
    protected final JTextField libNumText = new JTextField(5);
    protected final JTextField passwordText = new JTextField(5);
    protected JButton logInEnterButton = new JButton("Enter");
    protected JButton logInBackButton = new JButton("Back");

    public BibliotecaMainScreen() throws ParserConfigurationException, IOException, SAXException {

    }

    public void runMain() throws ParserConfigurationException, SAXException, IOException {

        this.setTitle("Main Menu");
        this.setBounds(800, 500, 700, 75);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new FlowLayout());

//        JButton categoryPickerButton = new JButton("Choose a category");

 //       mainPanel.add(categoryPickerButton);
        mainPanel.add(loginInButton);
        mainPanel.add(itemListingButton);
        mainPanel.add(itemDetailButton);
        mainPanel.add(customerInfoButton);
        mainPanel.add(checkOutButton);
        mainPanel.add(returnBookButton);
        mainPanel.add(itemOwnerButton);
        mainPanel.add(logOutButton);


        itemOwnerButton.setVisible(false);

        itemListingFunction(itemListingButton, 0);
        itemListingFunction(itemOwnerButton, 1);

        itemDetailFunction(itemDetailButton);

        logOutFunction(logOutButton, itemOwnerButton);

        logInFunction(loginInButton, itemOwnerButton);

        customerInfoFunction(customerInfoButton);

        returnBookFunction(returnBookButton);

        checkOutFunction(checkOutButton);

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

    }

    public void checkOutFunction(JButton checkOutButton) {
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLoggedIn) {
                    try {
                        checkOutScreen.checkOutPanel(userLogIn);
                    } catch (ParserConfigurationException e1) {
                        e1.printStackTrace();
                    } catch (SAXException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(BibliotecaMainScreen.this, "You need to login in order to check out",
                            "Login in Please", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void returnBookFunction(JButton returnBookButton) {
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLoggedIn) {
                    try {
                        returnScreen.returnPanel(userLogIn);
                    } catch (ParserConfigurationException e1) {
                        e1.printStackTrace();
                    } catch (SAXException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(BibliotecaMainScreen.this, "You need to login in order to return",
                            "Login in Please", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    public void customerInfoFunction(JButton customerInfoButton) {
        customerInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isLoggedIn){
                    JOptionPane.showMessageDialog(BibliotecaMainScreen.this,
                            "You need to login to see your account first",
                            "Not Logged In", JOptionPane.ERROR_MESSAGE);
                } else {
                    Document doc = null;
                    try {
                        doc = getPeopleDocument();
                    } catch (ParserConfigurationException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (SAXException e1) {
                        e1.printStackTrace();
                    }
                    doc.getDocumentElement().normalize();
                    NodeList customerList = doc.getElementsByTagName("customer");

                    for (int i = 0; i < customerList.getLength(); i++) {

                        Node customerNode = customerList.item(i);

                        if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element customerElement = (Element) customerNode;

                            String nameXml = customerElement.getElementsByTagName("name").item(0).getTextContent();
                            String emailXml = customerElement.getElementsByTagName("email").item(0).getTextContent();
                            String addressXml = customerElement.getElementsByTagName("address").item(0).getTextContent();
                            String phoneNumberXml = customerElement.getElementsByTagName("phoneNumber").item(0).getTextContent();

                            if (userLogIn.equals(nameXml)){
                                JOptionPane.showMessageDialog(BibliotecaMainScreen.this,
                                        "Name: " + nameXml + "\n" +
                                        "E-mail: " + emailXml + "\n" +
                                        "Address: " + addressXml + "\n" +
                                        "Phone Number: " + phoneNumberXml , "Customer Information", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }

                        }
                    }
                }
            }
        });
    }

    public void logOutFunction(JButton logOutButton, final JButton itemOwnerButton) {
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoggedIn){
                    JOptionPane.showMessageDialog(BibliotecaMainScreen.this, "No one is logged in",
                            "No one is logged in", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(BibliotecaMainScreen.this, userLogIn + " has logged out",
                            "Log Out Success", JOptionPane.PLAIN_MESSAGE);
                    isLoggedIn = false;
                    userLogIn = "";
                    itemOwnerButton.setVisible(false);
                }
            }
        });
    }

    public void logInFunction(JButton loginInButton, final JButton itemOwnerButton) {
        loginInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(BibliotecaMainScreen.this, "You are already logged in",
                    "Logged in Already", JOptionPane.QUESTION_MESSAGE);
                } else {
                    BibliotecaMainScreen.this.setVisible(false);
                    final JFrame loginScreen = new JFrame();
                    JPanel mainPanel = new JPanel();

                    JPanel customerLibNumberPanel = new JPanel(new BorderLayout());
                    JPanel customerPasswordPanel = new JPanel(new BorderLayout());


                    JLabel libNumberLabel = new JLabel("Library Number: ");
                    customerLibNumberPanel.add(libNumberLabel, BorderLayout.WEST);
                    customerLibNumberPanel.add(libNumText, BorderLayout.CENTER);

                    JLabel passwordLabel = new JLabel("Password: ");
                    customerPasswordPanel.add(passwordLabel, BorderLayout.WEST);
                    customerPasswordPanel.add(passwordText, BorderLayout.CENTER);

                    JPanel buttonsPanel = new JPanel(new BorderLayout());

                    buttonsPanel.add(logInEnterButton, BorderLayout.WEST);
                    buttonsPanel.add(logInBackButton, BorderLayout.EAST);

                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
                    mainPanel.add(userList);
                    mainPanel.add(customerLibNumberPanel);
                    mainPanel.add(customerPasswordPanel);
                    mainPanel.add(buttonsPanel);

                    loginScreen.getContentPane().add(mainPanel);
                    loginScreen.setSize(300, 200);
                    loginScreen.setLocation(400, 400);
                    loginScreen.setVisible(true);

                    logInBackButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            loginScreen.dispose();
                            BibliotecaMainScreen.this.setVisible(true);
                        }
                    });

                    logInEnterButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                boolean isUserLoggedIn = false;
                                Document doc = getPeopleDocument();
                                doc.getDocumentElement().normalize();
                                String selectedUser = (String) userList.getSelectedItem();
                                NodeList customerList;

                                if (selectedUser.equals("customer")) {
                                    customerList = doc.getElementsByTagName("customer");
                                } else {
                                    customerList = doc.getElementsByTagName("librarian");
                                    itemOwnerButton.setVisible(true);
                                }

                                String libraryNum = libNumText.getText();
                                String password = passwordText.getText();

                                for (int i = 0; i < customerList.getLength(); i++) {

                                    Node customerNode = customerList.item(i);

                                    if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element customerElement = (Element) customerNode;

                                        String nameXml = customerElement.getElementsByTagName("name").item(0).getTextContent();
                                        String libNumberXml = customerElement.getElementsByTagName("libNumber").item(0).getTextContent();
                                        String passwordXml = customerElement.getElementsByTagName("password").item(0).getTextContent();

                                        if (libraryNum.equals(libNumberXml) && password.equals(passwordXml)) {
                                            isLoggedIn = true;
                                            isUserLoggedIn = true;
                                            JOptionPane.showMessageDialog(loginScreen,
                                                    "You have successfully logged in: " + nameXml, "Welcome",
                                                    JOptionPane.PLAIN_MESSAGE);
                                            userLogIn = nameXml;
                                            loginScreen.dispose();
                                            BibliotecaMainScreen.this.setVisible(true);
                                            BibliotecaMainScreen.this.pack();
                                            break;
                                        }


                                    }
                                }
                                if (!isUserLoggedIn) {

                                    JOptionPane.showMessageDialog(loginScreen,
                                            "You have an invalid library number and/or password",
                                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                }

                            } catch (ParserConfigurationException e1) {
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (SAXException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }


    public void itemListingFunction(JButton bookListingButton, final int switcher) {
        bookListingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    categoryListScreen.bookListPanel(switcher);
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                } catch (SAXException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });
      }

    public void itemDetailFunction(JButton bookDetailButton) {
        bookDetailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    categoryDetailScreen.itemDetailPanel();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (SAXException e1) {
                    e1.printStackTrace();
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public Document getLibDocument() throws ParserConfigurationException, IOException, SAXException {
        File libraryListXml = new File("LibraryDatabase.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(libraryListXml);
        return doc;
    }

    public void updateLibXml(Document doc) throws IOException {
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        String filename = "LibraryDatabase.xml";
        XMLSerializer serializer = new XMLSerializer(
                new FileOutputStream(new File(filename)), format);
        serializer.serialize(doc);
    }


    public Document getPeopleDocument() throws ParserConfigurationException, IOException, SAXException {
        File peopleListXml = new File("PeopleDatabase.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(peopleListXml);
        return doc;
    }

    public void updatePeopleXml(Document doc) throws IOException {
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        String filename = "PeopleDatabase.xml";
        XMLSerializer serializer = new XMLSerializer(
                new FileOutputStream(new File(filename)), format);
        serializer.serialize(doc);
    }


}
