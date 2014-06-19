package com.twu.biblioteca;


import org.junit.Test;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class BibliotecaTest {
    private BibliotecaMainScreen mainMenu;
    private String user = "Bif";
    private String falseUser = "Bob";
    private String legitBook = "The Great Gatsby";
    private String unlegitBook = "Meet Big Brother";
    private String legitMovie = "Pulp Fiction";
    private String unlegitMovie = "The Godfather";
    private String unlegitLibNumber = "555-5555";
    private String legitCustomerLibNumber = "000-0001";
    private String legitCustomerPassword = "highfive21";
    private String legitLibrarianLibNumber = "100-0000";
    private String legitLibrarianPassword = "cuttymcfutty001";
    private int category = 0;

    public BibliotecaTest() throws IOException, SAXException, ParserConfigurationException {
        mainMenu = new BibliotecaMainScreen();
    }

    @Test
    public void welcomeGUITest() throws InterruptedException {
        BibliotecaMain main = new BibliotecaMain();
        System.out.println("Welcome Menu");
        main.welcomeMenuGUI();
        main.closeMe.doClick();
        sleep(2000);

    }

    @Test
    public void mainMenuGUITest() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        mainMenu.runMain();
        sleep(2000);
        mainMenu.loginInButton.doClick();
        System.out.println("Login Screen");
        sleep(2000);
        mainMenu.customerInfoButton.doClick();
        System.out.println("Customer Information Screen");
        sleep(2000);
        mainMenu.itemListingButton.doClick();
        System.out.println("Item Listing Screen");
        sleep(2000);
        mainMenu.itemDetailButton.doClick();
        System.out.println("Item Detail Screen");
        sleep(2000);
        mainMenu.checkOutButton.doClick();
        System.out.println("Check Out Screen");
        sleep(2000);
        mainMenu.returnBookButton.doClick();
        System.out.println("Return Screen");
        sleep(2000);
        mainMenu.logOutButton.doClick();
        System.out.println("Log Out Screen");
        sleep(2000);
        mainMenu.itemOwnerButton.doClick();
        System.out.println("Item Owner Screen");
        sleep(2000);
    }

    @Test
    public void categoryListingGUITesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        CategoryListScreen categoryListTest = new CategoryListScreen(mainMenu);
        categoryListTest.bookListPanel(0);
        categoryListTest.bookButton.doClick();
        System.out.println("List of Books");
        sleep(2000);
        categoryListTest.backButton.doClick();
        sleep(2000);
        categoryListTest.movieButton.doClick();
        System.out.println("List of Movies");
        sleep(2000);
        categoryListTest.backButton.doClick();
        sleep(2000);
    }

    @Test
    public void categoryDetailBookGUITesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        CategoryDetailScreen categoryDetailTest = new CategoryDetailScreen(mainMenu);
        categoryDetailTest.itemDetailPanel();
        categoryDetailTest.bookButton.doClick();
        System.out.println("This should send an error message saying the book is not in the library.");
        categoryDetailTest.categoryName.setText("This is very wrong");
        sleep(2000);
        categoryDetailTest.enterButton.doClick();
        sleep(2000);

        System.out.println("This should make a GUI with information about 'The Great Gatsby' ");
        categoryDetailTest.categoryName.setText("The Great Gatsby");
        sleep(2000);
        categoryDetailTest.enterButton.doClick();
        sleep(2000);
        categoryDetailTest.backButton.doClick();
    }
    @Test
    public void categoryDetailMovieGUITesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        CategoryDetailScreen categoryDetailTest = new CategoryDetailScreen(mainMenu);
        System.out.println("This should make a GUI with information about 'Pulp Fiction' ");
        categoryDetailTest.itemDetailPanel();
        categoryDetailTest.movieButton.doClick();
        categoryDetailTest.categoryName.setText("Pulp Fiction");
        sleep(2000);
        categoryDetailTest.enterButton.doClick();;
        sleep(2000);

        categoryDetailTest.backButton.doClick();
    }

    @Test
    public void checkingBookTesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        CheckOutScreen checkOutTest = new CheckOutScreen(mainMenu);
        category= 0;
        checkOutTest.categoryList.setSelectedIndex(category);


        System.out.println("This should send an error saying this is not a book in the library");
        checkOutTest.checkOutPanel(user);
        checkOutTest.userItemName.setText("This is not a book");
        checkOutTest.enterButton.doClick();
        sleep(2000);

        System.out.println("This checks out 'The Great Gatsby' from the xml");
        checkOutTest.userItemName.setText(legitBook);
        checkOutTest.enterButton.doClick();
        sleep(2000);

        System.out.println("This should give an error saying that the book is already checked out");
        checkOutTest.enterButton.doClick();
        sleep(2000);
    }

    @Test
    public void checkingMovieTesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        CheckOutScreen checkOutTest = new CheckOutScreen(mainMenu);
        category = 1;
        checkOutTest.categoryList.setSelectedIndex(category);

        System.out.println("This should send an error saying this is not a movie in the library");
        checkOutTest.checkOutPanel(user);
        checkOutTest.userItemName.setText("This is not a Movie");
        checkOutTest.enterButton.doClick();
        sleep(2000);

        System.out.println("This checks out" + legitBook + " from the xml");
        checkOutTest.userItemName.setText(legitMovie);
        checkOutTest.enterButton.doClick();
        sleep(2000);

        System.out.println("This should give an error saying that the movie is already checked out");
        checkOutTest.enterButton.doClick();
        sleep(2000);
    }
     @Test
     public void returnBookTesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
         ReturnScreen returnTest = new ReturnScreen(mainMenu);
         category = 0;
         returnTest.categoryList.setSelectedIndex(category);

         System.out.println("This should send an error, becuase this user doesn't have the book");
         returnTest.returnPanel(falseUser);
         returnTest.userItemName.setText(legitBook);
         returnTest.enterButton.doClick();
         sleep(2000);

         System.out.println("This should send an error, because the book isn't checked out by anyone");
         returnTest.returnPanel(user);
         returnTest.userItemName.setText(unlegitBook);
         returnTest.enterButton.doClick();
         sleep(2000);

         System.out.println("The user can return the book that he/she currently owns");
         returnTest.userItemName.setText(legitBook);
         returnTest.enterButton.doClick();
         sleep(2000);
     }

    @Test
    public void returnMovieTesting() throws IOException, SAXException, ParserConfigurationException, InterruptedException {
        ReturnScreen returnTest = new ReturnScreen(mainMenu);
        category = 1;
        returnTest.categoryList.setSelectedIndex(category);

        System.out.println("This should send an error, becuase this user doesn't have the movie");
        returnTest.returnPanel(falseUser);
        returnTest.userItemName.setText(legitMovie);
        returnTest.enterButton.doClick();
        sleep(2000);

        System.out.println("This should send an error, because the book isn't checked out by anyone");
        returnTest.returnPanel(user);
        returnTest.userItemName.setText(unlegitMovie);
        returnTest.enterButton.doClick();
        sleep(2000);

        System.out.println("The user can return the book that he/she currently owns");
        returnTest.userItemName.setText(legitMovie);
        returnTest.enterButton.doClick();
        sleep(2000);
    }

    @Test
    public void logInAndOutTesting() throws InterruptedException {
        JButton loginBut = mainMenu.loginInButton;
        JButton itemOwnBut = mainMenu.itemOwnerButton;
        JButton logOutBut = mainMenu.logOutButton;

        System.out.println("This should give an error that the library number doesn't exist");
        mainMenu.logInFunction(loginBut, itemOwnBut);
        loginBut.doClick();
        mainMenu.libNumText.setText(unlegitLibNumber);
        mainMenu.logInEnterButton.doClick();
        sleep(2000);

        System.out.println("This will make you login as customer: 'Timothy Power' ");
        mainMenu.libNumText.setText(legitCustomerLibNumber);
        mainMenu.passwordText.setText(legitCustomerPassword);
        mainMenu.logInEnterButton.doClick();
        sleep(2000);

        System.out.println("This will log out 'Timothy Power' ");
        mainMenu.logOutFunction(logOutBut, itemOwnBut);
        logOutBut.doClick();
        sleep(2000);
    }

    @Test
    public void customerInfoTest() throws InterruptedException {
        JButton customerInfoBut = mainMenu.customerInfoButton;
        JButton loginBut = mainMenu.loginInButton;
        JButton itemOwnBut = mainMenu.itemOwnerButton;

        System.out.println("This will give an error, because no one is logged in yet.");
        mainMenu.customerInfoFunction(customerInfoBut);
        customerInfoBut.doClick();
        sleep(2000);

        System.out.println("This will make you login as customer: 'Timothy Power' ");
        mainMenu.logInFunction(loginBut, itemOwnBut);
        loginBut.doClick();
        mainMenu.libNumText.setText(legitCustomerLibNumber);
        mainMenu.passwordText.setText(legitCustomerPassword);
        mainMenu.logInEnterButton.doClick();
        sleep(2000);

        System.out.println("This will display information about 'Timothy Power' ");
        mainMenu.customerInfoFunction(customerInfoBut);
        customerInfoBut.doClick();
        sleep(2000);
    }

    @Test
    public void bookOwnerInfoTest() throws InterruptedException, IOException, SAXException, ParserConfigurationException {


       CategoryListScreen itemOwnerList = new CategoryListScreen(mainMenu);
       itemOwnerList.bookListPanel(1);
       itemOwnerList.bookButton.doClick();
       sleep(2000);
       itemOwnerList.backButton.doClick();

       itemOwnerList.bookListPanel(1);
       itemOwnerList.movieButton.doClick();
       sleep(2000);
    }

}


